package com.suhoi;

import com.suhoi.in.console.TrainingDailyRunner;
import com.suhoi.util.LiquibaseRunner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LiquibaseRunner.runLiquibaseMigration();
        Thread.sleep(1500);
        TrainingDailyRunner.start();
    }
}