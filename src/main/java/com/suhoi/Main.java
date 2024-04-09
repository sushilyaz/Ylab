package com.suhoi;

import com.suhoi.in.TrainingDailyRunner;
import com.suhoi.util.InitDB;

public class Main {
    public static void main(String[] args) {
        InitDB.importData();
        TrainingDailyRunner.start();
    }
}