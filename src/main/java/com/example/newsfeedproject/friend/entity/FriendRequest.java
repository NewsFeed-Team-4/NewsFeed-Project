package com.example.newsfeedproject.friend.entity;

import com.example.newsfeedproject.common.entity.BaseEntity;
import com.example.newsfeedproject.common.enums.FriendRequestType;
import com.example.newsfeedproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User friend;

    @Enumerated(EnumType.STRING)
    private FriendRequestType status;

    @Builder
    public FriendRequest(User user, User friend, FriendRequestType status) {
        this.user = user;
        this.friend = friend;
        this.status = status;
    }
}
