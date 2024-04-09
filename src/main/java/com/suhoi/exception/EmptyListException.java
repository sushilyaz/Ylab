package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;

public class EmptyListException extends RuntimeException {
    public EmptyListException(String message) {
        super(message);
        System.out.println(message);
        TrainingDailyRunner.menu();
    }
}
