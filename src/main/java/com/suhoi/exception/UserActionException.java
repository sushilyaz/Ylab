package com.suhoi.exception;

/**
 * Когда появится web, удалю TrainingDailyRunner.start();
 */
public class UserActionException extends RuntimeException {
    public UserActionException(String message) {
        super(message);
    }
}
