package com.example.newsfeedproject.util;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class Payload {
    private final Long id;
    private final String email;
    private final String username;
    private final Date issuedAt;
    private Date exp;

    public Payload(Claims payload) {
        this.id = payload.get("id", Long.class);
        this.email = payload.get("email", String.class);
        this.username = payload.get("username", String.class);
        this.issuedAt = payload.get("issuedAt", Date.class);
        this.exp = payload.get("exp", Date.class);
    }

    public static Payload of(Long id, String email, String username, Date issuedAt) {
        return new Payload(id, email, username, issuedAt);
    }
}
