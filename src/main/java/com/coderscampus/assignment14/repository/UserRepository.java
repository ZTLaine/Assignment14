package com.coderscampus.assignment14.repository;

import com.coderscampus.assignment14.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users;

    public UserRepository() {
        users = new ArrayList<User>();
    }

    public List<User> findAll() {
        return users;
    }

    public User findById(int id) {
        for (User user : users) {
            if(user.getUserId().equals(id)){
                return user;
            }
        }
        return null;
    }

    public void save(User user) {
        User existingUser = findById(user.getUserId());
        if(existingUser != null){
        }else{
            users.add(user);
        }
    }
}
