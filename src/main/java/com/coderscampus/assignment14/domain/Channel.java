package com.coderscampus.assignment14.domain;

import java.util.List;

public class Channel {
    private Integer id;
    private String name;
    private List<User> activeUsers;
    private List<Message> messages;

    public Channel() {

    }
}
