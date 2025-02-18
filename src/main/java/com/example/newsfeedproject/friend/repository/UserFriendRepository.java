package com.example.newsfeedproject.friend.repository;

import com.example.newsfeedproject.friend.entity.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {
    boolean existsByUserIdAndFriendId(long userId, long friendId);
}
