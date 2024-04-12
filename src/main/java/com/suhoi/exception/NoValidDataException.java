package com.suhoi.exception;

/**
 * Когда появится web, удалю TrainingDailyRunner.start();
 */
public class NoValidDataException extends RuntimeException {
    public NoValidDataException(String message) {
        super(message);
    }
}
