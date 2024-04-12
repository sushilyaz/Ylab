//package com.suhoi.service.impl;
//
//import com.suhoi.dto.AuthDto;
//import com.suhoi.model.Role;
//import com.suhoi.model.User;
//import com.suhoi.repository.UserRepository;
//import com.suhoi.service.UserService;
//import com.suhoi.util.UserUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    private UserService userService;
//
//
//    @BeforeEach
//    void setUp() {
//        userService = new UserServiceImpl(userRepository);
//    }
//
//    @Test
//    @DisplayName("signUp where user doesnt exist")
//    void test1() {
//        AuthDto dto = new AuthDto("username", "password");
//        when(userRepository.getUserByUsername(dto.getUsername())).thenReturn(Optional.empty());
//        userService.signUp(dto);
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    @DisplayName("signIn with correct credentials")
//    void test2() {
//        // Аргументы для signIn
//        String username = "validUsername";
//        String password = "validPassword";
//        AuthDto dto = new AuthDto(username, password);
//
//        // Подготовка данных для mock'а
//        User validUser = User.builder()
//                .username(username)
//                .password(password)
//                .role(Role.SIMPLE)
//                .build();
//        when(userRepository.getUserByUsername(username)).thenReturn(Optional.of(validUser));
//        userService.signIn(dto);
//        User currentUser = UserUtils.getCurrentUser();
//        assertThat(currentUser.getUsername()).isEqualTo(username);
//    }
//}