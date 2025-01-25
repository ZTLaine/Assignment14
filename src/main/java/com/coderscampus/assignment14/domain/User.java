package com.coderscampus.assignment14.domain;

import lombok.Data;
import java.time.Instant;

@Data
public class User {
    private Integer userId;
    private String username;
    private Instant createdAt;

    public User() {
        this.createdAt = Instant.now();
    }

    public User(String username) {
        this();
        this.username = username;
    }

    public User(Integer userId, String username) {
        this();
        this.userId = userId;
        this.username = username;
    }
}
