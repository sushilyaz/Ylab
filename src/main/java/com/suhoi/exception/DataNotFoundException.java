package com.suhoi.exception;

/**
 * Когда появится web, удалю TrainingDailyRunner.menu();
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
