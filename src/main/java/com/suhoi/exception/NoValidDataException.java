package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;

/**
 * Когда появится web, удалю TrainingDailyRunner.start();
 */
public class NoValidDataException extends RuntimeException {
    public NoValidDataException(String message) {
        super(message);
        System.out.println(message);
        // Необходимо, чтобы приложение продолжило работу
        TrainingDailyRunner.start();
    }
}
