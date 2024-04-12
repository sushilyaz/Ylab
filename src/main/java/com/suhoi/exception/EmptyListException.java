package com.suhoi.exception;

/**
 * Когда появится web, удалю TrainingDailyRunner.menu();
 */
public class EmptyListException extends RuntimeException {
    public EmptyListException(String message) {
        super(message);
    }
}
