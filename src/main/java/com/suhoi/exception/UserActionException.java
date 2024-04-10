package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;

public class UserActionException extends RuntimeException {
    public UserActionException(String message) {
        super(message);
        System.out.println(message);
        TrainingDailyRunner.start();
    }
}
