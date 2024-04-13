package com.suhoi.service.impl;

import com.suhoi.dto.AuthDto;
import com.suhoi.model.Role;
import com.suhoi.model.User;
import com.suhoi.repository.UserRepository;
import com.suhoi.service.UserService;
import com.suhoi.util.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;


    @BeforeEach
    void setUp() {
        userService = UserServiceImpl.getInstance();
        ((UserServiceImpl) userService).setUserRepository(userRepository);
    }

    @Test
    @DisplayName("createUserIfNotExist user does not exist")
    void testCreateUserIfNotExistSuccess() {
        User createUser = new User(1L, "newUser", "password", Role.SIMPLE);

        when(userRepository.getUserByUsername(createUser.getUsername())).thenReturn(Optional.empty());

        userService.createUserIfNotExist(createUser);

        verify(userRepository, times(1)).getUserByUsername(createUser.getUsername());
        verify(userRepository, times(1)).save(createUser);
    }

    @Test
    @DisplayName("auth with correct credentials")
    void testAuthSuccess() {
        String username = "validUsername";
        String password = "validPassword";

        User validUser = User.builder()
                .username(username)
                .password(password)
                .role(Role.SIMPLE)
                .build();
        when(userRepository.getUserByUsername(username)).thenReturn(Optional.of(validUser));
        userService.auth(validUser);
        User currentUser = UserUtils.getCurrentUser();
        assertThat(currentUser.getUsername()).isEqualTo(username);
    }
}