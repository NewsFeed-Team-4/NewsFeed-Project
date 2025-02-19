package com.example.newsfeedproject.friend.controller;

import com.example.newsfeedproject.friend.dto.FriendResponseDto;
import com.example.newsfeedproject.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/{userId}/friend-requests")
    public ResponseEntity<List<FriendResponseDto>> getFriends(@PathVariable Long userId) {
        return ResponseEntity.ok(friendService.requestFriends(userId));
    }

    @PostMapping("/{usersId}/friend-requests/{email}")
    public ResponseEntity<FriendResponseDto> requestFriend(@PathVariable Long usersId, @PathVariable String email) {
        return ResponseEntity.ok(friendService.save(usersId, email));
    }

    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<FriendResponseDto> acceptFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        return ResponseEntity.ok(friendService.acceptFriend(userId, friendId));
    }

    @PatchMapping("/{userId}/friend-requests/{friendId}")
    public ResponseEntity<FriendResponseDto> declineFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        return ResponseEntity.ok(friendService.declinedFriend(userId, friendId));
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.deleteFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }
}
