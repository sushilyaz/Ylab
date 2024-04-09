package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;

public class TodayTrainException extends RuntimeException {
    public TodayTrainException(String message) {
        super(message);
        System.out.println(message);
        TrainingDailyRunner.menu();
    }
}
