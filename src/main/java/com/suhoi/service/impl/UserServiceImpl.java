package com.suhoi.service.impl;

import com.suhoi.dto.UserDto;
import com.suhoi.exception.UserActionException;
import com.suhoi.model.Role;
import com.suhoi.model.User;
import com.suhoi.repository.impl.UserRepositoryImpl;
import com.suhoi.service.UserService;
import com.suhoi.util.UserUtils;

import java.util.Optional;

/**
 * Чтобы не плодить объекты, написал потокобезопасный вариант синглтона. Пока не дойдем до спринга, будет здесь находиться
 */
public class UserServiceImpl implements UserService {

    private static volatile UserServiceImpl INSTANCE;

    private final UserRepositoryImpl userRepository;

    // Ленивая инициализация UserRepository
    private UserServiceImpl() {
        this.userRepository = UserRepositoryImpl.getInstance();
    }

    public static UserServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (UserServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void signUp(UserDto dto) {
        Optional<User> isUserExist = userRepository.getUserByUsername(dto.getUsername());
        if (isUserExist.isPresent()) {
            throw new UserActionException("User with username '" + dto.getUsername() + "' already exist");
        } else {
            User user = User.builder()
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .role(Role.SIMPLE)
                    .build();
            userRepository.save(user);
            // когда будет web sout ниже будет в виде response body на уровне контроллеров, пока пусть будет в сервисах, чтобы все было централизовано
            System.out.println("User with username '" + dto.getUsername() + "' registered success");
        }
    }

    @Override
    public void signIn(UserDto dto) {
        Optional<User> user = userRepository.getUserByUsername(dto.getUsername());
        if (user.isPresent()) {
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
