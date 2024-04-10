package com.suhoi.service.impl;

import com.suhoi.dto.UserDto;
import com.suhoi.exception.UserActionException;
import com.suhoi.model.Role;
import com.suhoi.model.User;
import com.suhoi.repository.UserRepository;
import com.suhoi.repository.impl.UserRepositoryImpl;
import com.suhoi.service.UserService;
import com.suhoi.util.UserUtils;

import java.util.Optional;

/**
 * Javadoc в интерфейсе
 */
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signUp(UserDto dto) {
        Optional<User> isUserExist = userRepository.getUserByUsername(dto.getUsername());
        // Если пользователь существует - исключение
        if (isUserExist.isPresent()) {
            throw new UserActionException("User with username '" + dto.getUsername() + "' already exist");
        } else {
            User user = User.builder()
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .role(Role.SIMPLE)
                    .build();
            userRepository.save(user);
            System.out.println("User with username '" + dto.getUsername() + "' registered success");
        }
    }

    @Override
    public void signIn(UserDto dto) {
        // Достаем пользователя, если он существует
        Optional<User> user = userRepository.getUserByUsername(dto.getUsername());
        if (user.isPresent()) {
            // Проверка правильности пароля
            if (user.get().getPassword().equals(dto.getPassword())){
                UserUtils.setCurrentUser(user.get());
                System.out.println("User with username '" + dto.getUsername() + "' log in success");
            } else {
                throw new UserActionException("Incorrect password");
            }

        } else {
            UserUtils.setCurrentUser(null);
            throw new UserActionException("User with username '" + dto.getUsername() + "' doesnt exist");
        }
    }
}
