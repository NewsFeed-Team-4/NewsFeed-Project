package com.example.newsfeedproject.friend.repository;

import com.example.newsfeedproject.common.enums.FriendRequestType;
import com.example.newsfeedproject.friend.entity.FriendRequest;
import com.example.newsfeedproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findAllByStatusAndUser_Id(FriendRequestType status, Long userId);
    List<FriendRequest> findAllByStatusAndFriend_Id(FriendRequestType friendRequestType, Long id);
    boolean existsByUserAndFriendAndStatus(User fromUser, User toUser, FriendRequestType friendRequestType);

    // user id 와 friend id 가 반대일 경우도 있어 함께 조회 할 수 있게 만들었습니다.
    @Query("SELECT fr FROM FriendRequest fr WHERE (fr.user.id = :userId AND fr.friend.id = :friendId AND fr.status = :status) " +
            "OR (fr.user.id = :friendId AND fr.friend.id = :userId AND fr.status = :status)")
    Optional<FriendRequest> findByUserAndFriendBothDirections(@Param("userId") Long userId,
                                                              @Param("friendId") Long friendId,
                                                              @Param("status") FriendRequestType status);
}
