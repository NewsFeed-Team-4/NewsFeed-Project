package com.example.newsfeedproject.user.repository;

import com.example.newsfeedproject.common.exception.ApplicationException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    Optional<User> findByEmail(String email);

    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    }
}
