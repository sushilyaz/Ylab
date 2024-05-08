package com.suhoi.service.impl;

import com.suhoi.annotation.Auditable;
import com.suhoi.annotation.Loggable;
import com.suhoi.exception.NoValidDataException;
import com.suhoi.exception.UserActionException;
import com.suhoi.model.User;
import com.suhoi.repository.UserRepository;
import com.suhoi.service.UserService;
import com.suhoi.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final String PASSWORD_REGEX = "[a-zA-Z0-9]{4,}";

    @Loggable
    @Auditable
    @Override
    public User createUserIfNotExist(User createUser) {
        if (createUser.getUsername() == null || createUser.getPassword() == null || createUser.getUsername().isEmpty() || createUser.getPassword().isEmpty()) {
            throw new NoValidDataException("Incorrect data");
        }
        if (createUser.getUsername().length() < 4 || !createUser.getPassword().matches(PASSWORD_REGEX)) {
            throw new NoValidDataException("Argument no valid");
        }
        Optional<User> existUser = userRepository.getUserByUsername(createUser.getUsername());
        if (existUser.isPresent()) throw new UserActionException("User already exists");
        return userRepository.save(createUser);
    }

    @Loggable
    @Auditable
    @Override
    public void auth(User authUser) {
        Optional<User> user = userRepository.getUserByUsername(authUser.getUsername());
        if (user.isEmpty() || !user.get().getPassword().equals(authUser.getPassword())) {
            throw new UserActionException("Invalid username or password");
        }
        UserUtils.setCurrentUser(user.get());
    }
}
