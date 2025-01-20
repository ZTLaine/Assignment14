package com.coderscampus.assignment14.service;

import com.coderscampus.assignment14.domain.Channel;
import com.coderscampus.assignment14.domain.Message;
import com.coderscampus.assignment14.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MessageService {
    private final ConcurrentHashMap<Integer, List<Message>> channelMessages = new ConcurrentHashMap<>();
    private final AtomicInteger messageIdGenerator = new AtomicInteger(0);
    private final ChannelService channelService;
    private final UserService userService;

    public MessageService(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;
    }

    public List<Message> getMessagesForChannel(Integer channelId, Integer afterMessageId) {
        List<Message> messages = channelMessages.getOrDefault(channelId, new ArrayList<>());
        if (afterMessageId == -1) {
            return messages;
        }
        return messages.stream()
                .filter(message -> message.getId() > afterMessageId)
                .toList();
    }

    public Message createMessage(Integer channelId, String username, String content) {
        Channel channel = channelService.findById(channelId.longValue());
        User user = userService.findByUsername(username);
        
        if (channel == null || user == null) {
            throw new IllegalArgumentException("Invalid channel or user");
        }

        Message message = new Message();
        message.setId(messageIdGenerator.incrementAndGet());
        message.setContent(content);
        message.setSender(user);
        message.setChannel(channel);

        channelMessages.computeIfAbsent(channelId, ArrayList::new).add(message);
        return message;
    }
}
