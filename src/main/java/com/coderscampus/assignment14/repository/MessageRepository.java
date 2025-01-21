package com.coderscampus.assignment14.repository;

import com.coderscampus.assignment14.domain.Message;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MessageRepository {
    private final List<Message> messages;

    public MessageRepository() {
        this.messages = new ArrayList<>();
    }

    public List<Message> findByChannelId(Integer channelId) {
        return messages.stream()
                .filter(message -> message.getChannel().getId().equals(channelId))
                .collect(Collectors.toList());
    }

    public List<Message> findByChannelIdAfterMessageId(Integer channelId, Integer afterMessageId) {
        return messages.stream()
                .filter(message -> message.getChannel().getId().equals(channelId))
                .filter(message -> message.getId() > afterMessageId)
                .collect(Collectors.toList());
    }

    public Message save(Message message) {
        messages.add(message);
        return message;
    }
}
