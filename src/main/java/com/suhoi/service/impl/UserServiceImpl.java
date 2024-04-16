package com.suhoi.service.impl;

import com.suhoi.exception.UserActionException;
import com.suhoi.in.console.TrainingDailyRunner;
import com.suhoi.model.User;
import com.suhoi.repository.UserRepository;
import com.suhoi.repository.impl.UserRepositoryImpl;
import com.suhoi.service.AuditService;
import com.suhoi.service.UserService;
import com.suhoi.util.UserUtils;
import lombok.Setter;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuditService auditService;

    public UserServiceImpl(UserRepository userRepository, AuditService auditService) {
        this.userRepository = userRepository;
        this.auditService = auditService;
    }
    //    private static volatile UserServiceImpl INSTANCE;
//
//    @Setter
//    private UserRepository userRepository;
//
//    private final AuditService auditService;
//
//    private UserServiceImpl() {
//        this.userRepository = UserRepositoryImpl.getInstance();
//        this.auditService = AuditServiceImpl.getInstance();
//    }
//
//    public static UserServiceImpl getInstance() {
//        if (INSTANCE == null) {
//            synchronized (UserServiceImpl.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new UserServiceImpl();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    @Override
    public void createUserIfNotExist(User createUser) {
        Optional<User> existUser = userRepository.getUserByUsername(createUser.getUsername());
        if (existUser.isPresent()) {
            System.out.println("User already exists!");
            TrainingDailyRunner.start();
        }
        userRepository.save(createUser);
        System.out.println("User with username '" + createUser.getUsername() + "' registered success");
    }

    @Override
    public void auth(User authUser) {
        Optional<User> user = userRepository.getUserByUsername(authUser.getUsername());
        if (user.isEmpty() || !user.get().getPassword().equals(authUser.getPassword())) {
            System.out.println("user does not exist");
            TrainingDailyRunner.start();
        }
        UserUtils.setCurrentUser(user.get());
        System.out.println("User with username '" + authUser.getUsername() + "' log in success");
        auditService.save("called UserService.auth");
    }
}
