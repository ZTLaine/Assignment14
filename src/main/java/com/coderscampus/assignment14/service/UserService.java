package com.coderscampus.assignment14.service;

import com.coderscampus.assignment14.domain.User;
import com.coderscampus.assignment14.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ChannelService channelService;

    private List<User> users;

    public UserService(UserRepository userRepository, ChannelService channelService) {
        this.userRepository = userRepository;
        this.channelService = channelService;
        users = new ArrayList<>();
        findAllUsers();
    }

    public void findAllUsers() {
        users.clear();
        users.addAll(userRepository.findAll());
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public Integer numberOfUsers() {
        findAllUsers();
        return users.size();
    }

    public User addUser(String username) {
        if (findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User(username);
        userRepository.save(user);
        users.add(user);
        
        if (channelService.findAllChannels().isEmpty()){
            channelService.addChannel("General");
        }
        return user;
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
