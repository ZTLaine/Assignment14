package com.coderscampus.assignment14.service;

import com.coderscampus.assignment14.domain.Channel;
import com.coderscampus.assignment14.domain.Message;
import com.coderscampus.assignment14.domain.User;
import com.coderscampus.assignment14.dto.MessageDTO;
import com.coderscampus.assignment14.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private ChannelService channelService;
    @Mock
    private UserService userService;
    @Mock
    private MessageRepository messageRepository;

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageService = new MessageService(channelService, userService, messageRepository);
    }

    @Test
    void getMessagesForChannel_WithInitialFetch_ReturnsAllMessages() {
        // Arrange
        Integer channelId = 1;
        Message message = createTestMessage(1, channelId);
        when(messageRepository.findByChannelId(channelId))
                .thenReturn(Collections.singletonList(message));

        // Act
        List<MessageDTO> result = messageService.getMessagesForChannel(channelId, -1);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(message.getContent(), result.get(0).getContent());
        verify(messageRepository).findByChannelId(channelId);
    }

    @Test
    void getMessagesForChannel_WithAfterMessageId_ReturnsNewMessages() {
        // Arrange
        Integer channelId = 1;
        Integer afterMessageId = 5;
        Message message = createTestMessage(6, channelId);
        when(messageRepository.findByChannelIdAfterMessageId(channelId, afterMessageId))
                .thenReturn(Collections.singletonList(message));

        // Act
        List<MessageDTO> result = messageService.getMessagesForChannel(channelId, afterMessageId);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(message.getContent(), result.get(0).getContent());
        verify(messageRepository).findByChannelIdAfterMessageId(channelId, afterMessageId);
    }

    @Test
    void createMessage_WithValidData_CreatesMessage() {
        // Arrange
        Integer channelId = 1;
        MessageDTO messageDTO = new MessageDTO(null, "Test content", "testUser", 1L, null);
        Channel channel = new Channel();
        channel.setId(channelId);
        User user = new User("testUser");
        Message savedMessage = createTestMessage(1, channelId);

        when(channelService.findById(channelId.longValue())).thenReturn(channel);
        when(userService.findByUsername("testUser")).thenReturn(user);
        when(messageRepository.save(any(Message.class))).thenReturn(savedMessage);

        // Act
        MessageDTO result = messageService.createMessage(channelId, messageDTO);

        // Assert
        assertNotNull(result);
        assertEquals(messageDTO.getContent(), result.getContent());
        assertEquals(messageDTO.getUsername(), result.getUsername());
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void createMessage_WithInvalidChannel_ThrowsException() {
        // Arrange
        Integer channelId = 1;
        MessageDTO messageDTO = new MessageDTO(null, "Test content", "testUser", 1L, null);
        when(channelService.findById(channelId.longValue())).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            messageService.createMessage(channelId, messageDTO)
        );
        verify(messageRepository, never()).save(any(Message.class));
    }

    @Test
    void createMessage_WithInvalidUser_ThrowsException() {
        // Arrange
        Integer channelId = 1;
        MessageDTO messageDTO = new MessageDTO(null, "Test content", "testUser", 1L, null);
        Channel channel = new Channel();
        channel.setId(channelId);

        when(channelService.findById(channelId.longValue())).thenReturn(channel);
        when(userService.findByUsername("testUser")).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            messageService.createMessage(channelId, messageDTO)
        );
        verify(messageRepository, never()).save(any(Message.class));
    }

    private Message createTestMessage(Integer id, Integer channelId) {
        Message message = new Message();
        message.setId(id);
        message.setContent("Test content");
        Channel channel = new Channel();
        channel.setId(channelId);
        message.setChannel(channel);
        User user = new User("testUser");
        message.setSender(user);
        return message;
    }
} 