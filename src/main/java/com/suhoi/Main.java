package com.suhoi;

import com.suhoi.in.console.TrainingDailyRunner;
import com.suhoi.util.LiquibaseRunner;

public class Main {
    public static void main(String[] args) {
        LiquibaseRunner.runLiquibaseMigration();
        TrainingDailyRunner.start();
    }
}