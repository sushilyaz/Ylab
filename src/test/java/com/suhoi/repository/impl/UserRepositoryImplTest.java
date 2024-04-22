package com.suhoi.repository.impl;

import com.suhoi.model.Role;
import com.suhoi.model.User;
import com.suhoi.repository.UserRepository;
import com.suhoi.util.ConnectionPool;
import com.suhoi.util.LiquibaseRunner;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryImplTest extends PostgresContainer {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Test
    @DisplayName("success save user")
    void testSaveSuccess() {
        User build = User.builder()
                .username("user3")
                .password("user3")
                .role(Role.SIMPLE)
                .build();
        userRepository.save(build);
        Optional<User> user = userRepository.getUserByUsername(build.getUsername());
        assertThat(user.get()).isEqualTo(build);
    }

    @Test
    @DisplayName("getUserByUsername success")
    void testGetUserByUsernameSuccess() {
        Optional<User> userByUsername = userRepository.getUserByUsername("user1");
        assertThat(userByUsername.get()).isNotNull();
    }
}
