package com.coderscampus.assignment14.repository;

import com.coderscampus.assignment14.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

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

    public User save(User user) {
        if (user.getUserId() == null) {
            user.setUserId(idGenerator.getAndIncrement());
        }
        users.add(user);
        return user;
    }
}
