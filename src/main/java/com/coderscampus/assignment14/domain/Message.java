package com.coderscampus.assignment14.domain;

import lombok.Data;
import java.time.Instant;

@Data
public class Message {
    private Integer id;
    private String content;
    private User sender;
    private Channel channel;
    private Instant createdAt;

    public Message() {
        this.createdAt = Instant.now();
    }
}
