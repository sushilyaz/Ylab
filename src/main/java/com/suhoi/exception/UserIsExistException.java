package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;

public class UserIsExistException extends RuntimeException {
    public UserIsExistException(String message) {
        super(message);
        System.out.println(message);
        TrainingDailyRunner.start();
    }
}
