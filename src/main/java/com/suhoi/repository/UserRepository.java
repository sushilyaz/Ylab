package com.suhoi.repository;

import com.suhoi.model.User;

import java.util.Optional;

public interface UserRepository {

    // return нужен для теста сервисного слоя
    void save(User user);
    Optional<User> getUserByUsername(String username);
}
