package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;

/**
 * Когда появится web, удалю TrainingDailyRunner.start();
 */
public class UserActionException extends RuntimeException {
    public UserActionException(String message) {
        super(message);
        System.out.println(message);
        // Необходимо, чтобы приложение продолжило работу
        TrainingDailyRunner.start();
    }
}
