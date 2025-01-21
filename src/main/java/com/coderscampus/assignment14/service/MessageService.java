package com.coderscampus.assignment14.service;

import com.coderscampus.assignment14.domain.Channel;
import com.coderscampus.assignment14.domain.Message;
import com.coderscampus.assignment14.domain.User;
import com.coderscampus.assignment14.dto.MessageDTO;
import com.coderscampus.assignment14.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MessageService {
    private final AtomicInteger messageIdGenerator = new AtomicInteger(0);
    private final ChannelService channelService;
    private final UserService userService;
    private final MessageRepository messageRepository;

    public MessageService(ChannelService channelService, UserService userService, MessageRepository messageRepository) {
        this.channelService = channelService;
        this.userService = userService;
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessagesForChannel(Integer channelId, Integer afterMessageId) {
        if (afterMessageId == -1) {
            return messageRepository.findByChannelId(channelId);
        }
        return messageRepository.findByChannelIdAfterMessageId(channelId, afterMessageId);
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

        return messageRepository.save(message);
    }

    public MessageDTO convertToDTO(Message message) {
        return new MessageDTO(
            message.getId().longValue(),
            message.getContent(),
            message.getSender().getUsername(),
            message.getChannel().getId().longValue(),
            message.getCreatedAt().toEpochMilli()
        );
    }
}
