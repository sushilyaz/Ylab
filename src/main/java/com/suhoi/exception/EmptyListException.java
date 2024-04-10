package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;

/**
 * Когда появится web, удалю TrainingDailyRunner.menu();
 */
public class EmptyListException extends RuntimeException {
    public EmptyListException(String message) {
        super(message);
        System.out.println(message);
        // Необходимо, чтобы приложение продолжило работу
        TrainingDailyRunner.menu();
    }
}
