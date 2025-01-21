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

    public List<MessageDTO> getMessagesForChannel(Integer channelId, Integer afterMessageId) {
        List<Message> messages;
        if (afterMessageId == -1) {
            messages = messageRepository.findByChannelId(channelId);
        } else {
            messages = messageRepository.findByChannelIdAfterMessageId(channelId, afterMessageId);
        }
        return messages.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public MessageDTO createMessage(Integer channelId, MessageDTO messageDTO) {
        Channel channel = channelService.findById(channelId.longValue());
        User user = userService.findByUsername(messageDTO.getUsername());
        
        if (channel == null || user == null) {
            throw new IllegalArgumentException("Invalid channel or user");
        }

        Message message = new Message();
        message.setId(messageIdGenerator.incrementAndGet());
        message.setContent(messageDTO.getContent());
        message.setSender(user);
        message.setChannel(channel);

        return convertToDTO(messageRepository.save(message));
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
