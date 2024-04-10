package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;
import com.suhoi.repository.RuntimeDB;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message) {
        super(message);
        System.out.println(message);
        TrainingDailyRunner.menu();
    }
}
