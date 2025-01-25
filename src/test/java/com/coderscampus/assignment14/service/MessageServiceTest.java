package com.coderscampus.assignment14.service;

import com.coderscampus.assignment14.domain.Channel;
import com.coderscampus.assignment14.domain.User;
import com.coderscampus.assignment14.dto.MessageDTO;
import com.coderscampus.assignment14.repository.MessageRepository;
import com.coderscampus.assignment14.repository.ChannelRepository;
import com.coderscampus.assignment14.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageServiceTest {
    private MessageRepository messageRepository;
    private ChannelRepository channelRepository;
    private UserRepository userRepository;
    private ChannelService channelService;
    private UserService userService;
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageRepository = new MessageRepository();
        channelRepository = new ChannelRepository();
        userRepository = new UserRepository();
        channelService = new ChannelService(channelRepository);
        userService = new UserService(userRepository, channelService);
        messageService = new MessageService(channelService, userService, messageRepository);
    }

    @Test
    void getMessagesForChannel_WithInitialFetch_ReturnsAllMessages() {
        // Arrange
        Channel channel = channelService.addChannel("Test Channel");
        User user = userService.addUser("testUser");
        MessageDTO messageDTO = new MessageDTO(null, "Test content", user.getUsername(), channel.getId().longValue(), null);
        messageService.createMessage(channel.getId(), messageDTO);

        // Act
        List<MessageDTO> result = messageService.getMessagesForChannel(channel.getId(), -1);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Test content", result.get(0).getContent());
    }  

    @Test
    void createMessage_WithValidData_CreatesMessage() {
        // Arrange
        Channel channel = channelService.addChannel("Test Channel");
        User user = userService.addUser("testUser");
        MessageDTO messageDTO = new MessageDTO(null, "Test content", user.getUsername(), channel.getId().longValue(), null);

        // Act
        MessageDTO result = messageService.createMessage(channel.getId(), messageDTO);

        // Assert
        assertNotNull(result);
        assertEquals(messageDTO.getContent(), result.getContent());
        assertEquals(messageDTO.getUsername(), result.getUsername());
        assertEquals(1, result.getId());
    }

    @Test
    void createMessage_WithInvalidChannel_ThrowsException() {
        // Arrange
        User user = userService.addUser("testUser");
        MessageDTO messageDTO = new MessageDTO(null, "Test content", user.getUsername(), 999L, null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            messageService.createMessage(999, messageDTO)
        );
    }

    @Test
    void createMessage_WithInvalidUser_ThrowsException() {
        // Arrange
        Channel channel = channelService.addChannel("Test Channel");
        MessageDTO messageDTO = new MessageDTO(null, "Test content", "nonexistentUser", channel.getId().longValue(), null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            messageService.createMessage(channel.getId(), messageDTO)
        );
    }
} 