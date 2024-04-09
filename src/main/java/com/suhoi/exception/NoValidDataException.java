package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;

public class NoValidDataException extends RuntimeException {
    public NoValidDataException(String message) {
        super(message);
        System.out.println(message);
        TrainingDailyRunner.start();
    }
}
