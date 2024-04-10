package com.suhoi.util;

import com.suhoi.in.TrainingDailyRunner;

import java.time.Duration;
import java.time.LocalDate;

public class Parser {
    /**
     * Парсер для Duration
     * @param toParse
     * @return
     */
    public static Duration durationParse(String toParse) {
        try {
            Duration duration;
            if (toParse.contains(":")) {
                String[] parts = toParse.split(":");
                long hours = Long.parseLong(parts[0]);
                long minutes = Long.parseLong(parts[1]);
                duration = Duration.ofHours(hours).plusMinutes(minutes);
            } else {
                duration = Duration.parse(toParse);
            }
            System.out.println("Parse duration success: " + duration);
            return duration;
        } catch (Exception e) {
            System.out.println("Data no valid: " + e.getMessage());
            TrainingDailyRunner.menu();
        }
        return null;
    }
}
