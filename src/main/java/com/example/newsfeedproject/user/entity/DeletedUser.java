package com.example.newsfeedproject.user.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class DeletedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private DeletedUser(String email) {
        this.email = email;
    }

    public static DeletedUser of(String email) {
        return new DeletedUser(email);
    }
}
