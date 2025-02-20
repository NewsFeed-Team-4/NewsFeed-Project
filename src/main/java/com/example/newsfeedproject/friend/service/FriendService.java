package com.example.newsfeedproject.friend.service;

import com.example.newsfeedproject.common.enums.FriendRequestType;
import com.example.newsfeedproject.friend.dto.FriendResponseDto;
import com.example.newsfeedproject.friend.entity.FriendRequest;
import com.example.newsfeedproject.friend.entity.UserFriend;
import com.example.newsfeedproject.friend.repository.FriendRequestRepository;
import com.example.newsfeedproject.friend.repository.UserFriendRepository;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final UserFriendRepository userFriendRepository;

    public FriendResponseDto save(Long userId, Long friendId) {

        User fromUser = userRepository.findByIdOrElseThrow(userId);

        User toUser = userRepository.findByIdOrElseThrow(friendId);

        // 자기 자신에게 친구 요청을 보내는 경우 방지
        if (fromUser.getId().equals(toUser.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot send a friend request to yourself.");
        }

        // 이미 대기 중 또는 수락된 친구 요청이 있는 경우 예외 처리
        if (friendRequestExists(fromUser, toUser, FriendRequestType.WAITING) ||
                friendRequestExists(fromUser, toUser, FriendRequestType.ACCEPTED)) {
            log.error("Friend request already sent or accepted.");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Friend request already sent or accepted.");
        }

        FriendRequest friendRequest = FriendRequest.builder()
                .user(fromUser)
                .friend(toUser)
                .status(FriendRequestType.WAITING)
                .build();

        FriendRequest request = friendRequestRepository.save(friendRequest);

        return new FriendResponseDto(request.getId(), request.getUser().getId(),
                request.getFriend().getId(), request.getStatus(), request.getCreatedAt(), request.getModifiedAt());
    }

    // 특정 상태의 친구 요청이 존재하는지 확인하는 메서드
    private boolean friendRequestExists(User user1, User user2, FriendRequestType status) {
        return friendRequestRepository.existsByUserAndFriendAndStatus(user1.getId(), user2.getId(), status);
    }

    public List<FriendResponseDto> requestFriends(long userId) {

        User user = userRepository.findByIdOrElseThrow(userId);

        // 1. 내가 보낸 요청 가져오기
        List<FriendRequest> sentRequests = friendRequestRepository.findAllByStatusAndUser_Id(FriendRequestType.WAITING, user.getId());

        // 2. 내가 받은 요청 가져오기
        List<FriendRequest> receivedRequests = friendRequestRepository.findAllByStatusAndFriend_Id(FriendRequestType.WAITING, user.getId());

        List<FriendResponseDto> dtos = new ArrayList<>();

        // 내가 보낸 요청 목록 추가
        for (FriendRequest request : sentRequests) {
            dtos.add(new FriendResponseDto(request.getId(), request.getUser().getId(),
                    request.getFriend().getId(), request.getStatus(), request.getCreatedAt(), request.getModifiedAt()));
        }

        // 내가 받은 요청 목록 추가
        for (FriendRequest request : receivedRequests) {
            dtos.add(new FriendResponseDto(request.getId(), request.getUser().getId(),
                    request.getFriend().getId(), request.getStatus(), request.getCreatedAt(), request.getModifiedAt()));
        }

        return dtos;
    }

    @Transactional // 트랜잭션에 의해 양방향 저장이 않되는 문제가 있어서 flush() 를 사용
    public FriendResponseDto acceptFriend(long userId, long friendId) {
        FriendRequest friendRequest = friendRequestRepository.findByUserAndFriendBothDirections(userId, friendId, FriendRequestType.WAITING)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend request not found"));

        if (friendRequest.getStatus() == FriendRequestType.ACCEPTED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Friend request already accepted.");
        }

        friendRequest.setStatus(FriendRequestType.ACCEPTED);

        // 중복 체크를 위한 boolean 선언
        boolean existsUserToFriend = userFriendRepository.existsByUserIdAndFriendId(userId, friendId);
        boolean existsFriendToUser = userFriendRepository.existsByUserIdAndFriendId(friendId, userId);

        // user_friend 테이블에 양방향 저장
        if (!existsUserToFriend) {
            userFriendRepository.save(new UserFriend(friendRequest.getUser(), friendRequest.getFriend()));
            // 사용자의 친구 수 증가
            friendRequest.getUser().incrementFriendCount();
        }

        if (!existsFriendToUser) {
            userFriendRepository.save(new UserFriend(friendRequest.getFriend(), friendRequest.getUser()));
            // 친구의 친구 수 증가
            friendRequest.getFriend().incrementFriendCount();
        }

        return new FriendResponseDto(
                friendRequest.getId(),
                friendRequest.getUser().getId(),
                friendRequest.getFriend().getId(),
                friendRequest.getStatus(),
                friendRequest.getCreatedAt(),
                friendRequest.getModifiedAt()
        );
    }

    @Transactional
    public FriendResponseDto declinedFriend(long userId, long friendId) {
        FriendRequest friendRequest = friendRequestRepository.findByUserAndFriendBothDirections(userId, friendId, FriendRequestType.WAITING)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend request not found"));

        if (friendRequest.getStatus() == FriendRequestType.ACCEPTED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Friend request already accepted.");
        }

        friendRequest.setStatus(FriendRequestType.DECLINED);

        friendRequestRepository.save(friendRequest);

        return new FriendResponseDto(
                friendRequest.getId(),
                friendRequest.getUser().getId(),
                friendRequest.getFriend().getId(),
                friendRequest.getStatus(),
                friendRequest.getCreatedAt(),
                friendRequest.getModifiedAt()
        );
    }

    @Transactional
    public void deleteFriend(long userId, long friendId) {

        FriendRequest friendRequest = friendRequestRepository.findByUserAndFriendBothDirections(userId, friendId, FriendRequestType.ACCEPTED)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend request not found"));

        // DELETE 로 변경
        friendRequest.setStatus(FriendRequestType.DELETED);
        friendRequestRepository.save(friendRequest);

        // 친구 관계가 존재하는지 확인 (양방향 체크)
        boolean exists = userFriendRepository.existsByUserIdAndFriendId(userId, friendId);
        boolean existsReverse = userFriendRepository.existsByUserIdAndFriendId(friendId, userId);

        if (!exists || !existsReverse) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend relationship not found.");
        }

        // 친구 관계 삭제 (양방향)
        userFriendRepository.deleteByUserIdAndFriendId(userId, friendId);
        userFriendRepository.deleteByUserIdAndFriendId(friendId, userId);

        // 사용자와 친구의 친구 수 감소
        friendRequest.getUser().decrementFriendCount();
        friendRequest.getFriend().decrementFriendCount();
    }
}
