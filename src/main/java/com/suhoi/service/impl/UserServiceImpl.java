package com.suhoi.service.impl;

import com.suhoi.exception.UserActionException;
import com.suhoi.in.console.TrainingDailyRunner;
import com.suhoi.model.User;
import com.suhoi.repository.UserRepository;
import com.suhoi.repository.impl.UserRepositoryImpl;
import com.suhoi.service.AuditService;
import com.suhoi.service.UserService;
import com.suhoi.util.UserUtils;

import java.util.Optional;

/**
 * Javadoc в интерфейсе
 */
public class UserServiceImpl implements UserService {

    private static volatile UserServiceImpl INSTANCE;

    private final UserRepository userRepository;

    private final AuditService auditService;

    private UserServiceImpl() {
        this.userRepository = UserRepositoryImpl.getInstance();
        this.auditService = AuditServiceImpl.getInstance();
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
    public void createUserIfNotExist(User createUser) {
        Optional<User> existUser = userRepository.getUserByUsername(createUser.getUsername());
        try {
            if (existUser.isPresent()) {
                throw new UserActionException("User with username '" + createUser.getUsername() + "' already exist");
            }
        } catch (UserActionException e) {
            System.out.println(e.getMessage());
            TrainingDailyRunner.start();
        }
        userRepository.save(createUser);
        System.out.println("User with username '" + createUser.getUsername() + "' registered success");

    }

    @Override
    public void auth(User authUser) {
        Optional<User> user = userRepository.getUserByUsername(authUser.getUsername());
        try {
            if (user.isEmpty() || !user.get().getPassword().equals(authUser.getPassword())) {
                throw new UserActionException("Incorrect login or password");
            }
        } catch (UserActionException e) {
            System.out.println(e.getMessage());
            TrainingDailyRunner.start();
        }
        UserUtils.setCurrentUser(user.get());
        System.out.println("User with username '" + authUser.getUsername() + "' log in success");
        auditService.save("called UserService.auth");
    }
}
