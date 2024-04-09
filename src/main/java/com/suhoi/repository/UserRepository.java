package com.suhoi.repository;

import com.suhoi.dto.UserDto;
import com.suhoi.model.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> getUserByUsername(String username);
}
