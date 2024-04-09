package com.suhoi.exception;

import com.suhoi.in.TrainingDailyRunner;

public class TypeIsNotExistException extends RuntimeException {
    public TypeIsNotExistException(String message) {
        super(message);
        System.out.println(message);
        TrainingDailyRunner.menu();
    }
}
