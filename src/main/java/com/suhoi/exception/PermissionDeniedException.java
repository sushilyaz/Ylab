package com.suhoi.exception;

/**
 * Когда появится web, удалю TrainingDailyRunner.menu();
 */
public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message) {
        super(message);
    }
}
