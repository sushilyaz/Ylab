package com.suhoi.repository;

import com.suhoi.model.User;

import java.util.Optional;

public interface UserRepository {

    /**
     * INSERT
     *
     * @param user
     */
    void save(User user);

    /**
     * SELECT * FROM users WHERE username = ?
     *
     * @param username
     * @return
     */
    Optional<User> getUserByUsername(String username);
}
