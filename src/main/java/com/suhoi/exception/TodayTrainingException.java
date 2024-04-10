package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;

public class TodayTrainingException extends RuntimeException {
    public TodayTrainingException(String message) {
        super(message);
        System.out.println(message);
        TrainingDailyRunner.menu();
    }
}
