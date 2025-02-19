package com.example.newsfeedproject.friend.dto;

import com.example.newsfeedproject.common.enums.FriendRequestType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FriendResponseDto {

    private final Long id;
    private final Long userId;
    private final Long friendId;
    private final FriendRequestType friendRequestType;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
