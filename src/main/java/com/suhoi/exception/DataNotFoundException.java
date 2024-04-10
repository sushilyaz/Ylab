package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
        System.out.println(message);
        TrainingDailyRunner.menu();
    }
}
