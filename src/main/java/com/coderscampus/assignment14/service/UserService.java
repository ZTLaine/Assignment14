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
        users = userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public Integer numberOfUsers() {
        findAllUsers();
        return users.size();
    }

    public User addUser(String username) {
        User user = new User(username);
        if(user.getUserId() == null){
            user.setUserId(numberOfUsers());
        }
        users.add(user);
        System.out.println("New user: " + user + " added");
        userRepository.save(user);

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
