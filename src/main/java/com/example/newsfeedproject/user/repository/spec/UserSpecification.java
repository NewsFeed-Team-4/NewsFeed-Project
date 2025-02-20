package com.example.newsfeedproject.user.repository.spec;

import com.example.newsfeedproject.user.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasUsername(String username) {
        return (root, query, builder) -> username == null ? null : builder.like(root.get("username"), "%"+username+"%");
    }
}
