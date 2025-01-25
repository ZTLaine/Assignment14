package com.coderscampus.assignment14.service;

import com.coderscampus.assignment14.domain.Channel;
import com.coderscampus.assignment14.repository.ChannelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChannelServiceTest {
    private ChannelRepository channelRepository;
    private ChannelService channelService;

    @BeforeEach
    void setUp() {
        channelRepository = new ChannelRepository();
        channelService = new ChannelService(channelRepository);
    }

    @Test
    void findById_WithValidId_ReturnsChannel() {
        // Arrange
        Channel channel = new Channel();
        channel.setName("Test Channel");
        Channel savedChannel = channelService.addChannel("Test Channel");

        // Act
        Channel result = channelService.findById(savedChannel.getId().longValue());

        // Assert
        assertNotNull(result);
        assertEquals(savedChannel.getId(), result.getId());
        assertEquals("Test Channel", result.getName());
    }

    @Test
    void findAllChannels_WhenEmpty_CreatesGeneralChannel() {
        // Act
        List<Channel> result = channelService.findAllChannels();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("General", result.get(0).getName());
    }

    @Test
    void findAllChannels_WithExistingChannels_ReturnsChannels() {
        // Arrange
        channelService.addChannel("Test Channel");

        // Act
        List<Channel> result = channelService.findAllChannels();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Test Channel", result.get(0).getName());
    }

    @Test
    void addChannel_WithNewName_CreatesChannel() {
        // Arrange
        String channelName = "New Channel";

        // Act
        Channel result = channelService.addChannel(channelName);

        // Assert
        assertNotNull(result);
        assertEquals(channelName, result.getName());
        assertEquals(0, result.getId());
    }

    @Test
    void addChannel_WithExistingName_ThrowsException() {
        // Arrange
        String channelName = "Existing Channel";
        channelService.addChannel(channelName);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            channelService.addChannel(channelName)
        );
    }
} 