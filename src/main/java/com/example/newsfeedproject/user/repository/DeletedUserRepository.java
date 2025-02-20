package com.example.newsfeedproject.user.repository;

import com.example.newsfeedproject.user.entity.DeletedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {
    Optional<DeletedUser> findByEmail(String email);
}
