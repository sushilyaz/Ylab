package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;
import com.suhoi.repository.RuntimeDB;

/**
 * Когда появится web, удалю TrainingDailyRunner.menu();
 */
public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message) {
        super(message);
        System.out.println(message);
        // Необходимо, чтобы приложение продолжило работу
        TrainingDailyRunner.menu();
    }
}
