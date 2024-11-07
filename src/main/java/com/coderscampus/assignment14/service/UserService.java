package com.coderscampus.assignment14.service;

import com.coderscampus.assignment14.domain.User;
import com.coderscampus.assignment14.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;

    private List<User> users;
    private ChannelService channelService;

    public UserService() {
        userRepository = new UserRepository();
        users = new ArrayList<>();
        channelService = new ChannelService();
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
}
