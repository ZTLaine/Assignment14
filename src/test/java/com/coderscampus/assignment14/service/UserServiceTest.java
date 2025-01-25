package com.coderscampus.assignment14.service;

import com.coderscampus.assignment14.domain.User;
import com.coderscampus.assignment14.repository.UserRepository;
import com.coderscampus.assignment14.repository.ChannelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserRepository userRepository;
    private ChannelRepository channelRepository;
    private ChannelService channelService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        channelRepository = new ChannelRepository();
        channelService = new ChannelService(channelRepository);
        userService = new UserService(userRepository, channelService);
    }

    @Test
    void getUserById_WithValidId_ReturnsUser() {
        // Arrange
        User savedUser = userService.addUser("testUser");

        // Act
        User result = userService.getUserById(savedUser.getUserId());

        // Assert
        assertNotNull(result);
        assertEquals(savedUser.getUserId(), result.getUserId());
        assertEquals("testUser", result.getUsername());
    }

    @Test
    void numberOfUsers_ReturnsCorrectCount() {
        // Arrange
        userService.addUser("user1");
        userService.addUser("user2");

        // Act
        Integer result = userService.numberOfUsers();

        // Assert
        assertEquals(2, result);
    }

    @Test
    void addUser_WithNewUsername_CreatesUser() {
        // Arrange
        String username = "newUser";

        // Act
        User result = userService.addUser(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(0, result.getUserId());
    }

    @Test
    void addUser_WithExistingUsername_ThrowsException() {
        // Arrange
        String username = "existingUser";
        userService.addUser(username);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            userService.addUser(username)
        );
    }

    @Test
    void findByUsername_WithExistingUsername_ReturnsUser() {
        // Arrange
        String username = "testUser";
        userService.addUser(username);

        // Act
        User result = userService.findByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void findByUsername_WithNonexistentUsername_ReturnsNull() {
        // Arrange
        String username = "nonexistentUser";

        // Act
        User result = userService.findByUsername(username);

        // Assert
        assertNull(result);
    }

    @Test
    void addUser_CreatesGeneralChannel_WhenNoChannelsExist() {
        // Arrange
        String username = "newUser";

        // Act
        userService.addUser(username);

        // Assert
        assertFalse(channelService.findAllChannels().isEmpty());
        assertEquals("General", channelService.findAllChannels().get(0).getName());
    }

    @Test
    void findAllUsers_UpdatesUsersList() {
        // Arrange
        userService.addUser("user1");
        userService.addUser("user2");

        // Act
        userService.findAllUsers();

        // Assert
        User foundUser = userService.findByUsername("user1");
        assertNotNull(foundUser);
        assertEquals("user1", foundUser.getUsername());
    }
} 