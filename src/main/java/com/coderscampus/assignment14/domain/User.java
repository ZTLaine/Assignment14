package com.coderscampus.assignment14.domain;

import lombok.Data;

import java.time.Instant;

@Data
public class User {
    private Integer userId;
    private String username;
    private Instant createdAt;

    public User() {
        this.userId = null;
        this.createdAt = Instant.now();
    }

    public User(Integer userId, String username) {
        this.userId = userId;
        this.username = username;
        this.createdAt = Instant.now();
    }

    public User(String username) {
        this.userId = null;
        this.username = username;
        this.createdAt = Instant.now();
    }
}
