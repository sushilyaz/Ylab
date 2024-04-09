package com.suhoi.repository.impl;

import com.suhoi.dto.UserDto;
import com.suhoi.model.User;
import com.suhoi.repository.RuntimeDB;
import com.suhoi.repository.UserRepository;
import com.suhoi.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private static volatile UserRepositoryImpl INSTANCE;

    private UserRepositoryImpl() {
    }

    public static UserRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (UserRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepositoryImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void save(User user) {;
        RuntimeDB.addUser(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return RuntimeDB.getUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
}
