package com.suhoi.repository.impl;

import com.suhoi.InitDBTest;
import com.suhoi.model.Role;
import com.suhoi.model.User;
import com.suhoi.repository.RuntimeDB;
import com.suhoi.repository.UserRepository;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryImplTest {

    private UserRepository userRepository = UserRepositoryImpl.getInstance();


    @BeforeEach
    void initDB() {
        InitDBTest.importData();
    }
    @AfterEach
    void clear() {
        RuntimeDB.getUsers().clear();
    }
    @Test
    @DisplayName("success save user")
    void test1() {
        User build = User.builder()
                .username("user3")
                .password("user3")
                .role(Role.SIMPLE)
                .build();
        userRepository.save(build);
        User user = RuntimeDB.getUsers().get(3);
        assertThat(user.getUsername()).isEqualTo("user3");
    }

    @Test
    @DisplayName("getUserByUsername success")
    void test() {
        Optional<User> userByUsername = userRepository.getUserByUsername("user1");
        assertThat(userByUsername.get()).isNotNull();
    }
}
