package com.suhoi.util;

import com.google.gson.Gson;
import com.suhoi.annotation.Loggable;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

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
        }
        return null;
    }

    /**
     * Парсер из JSONB to Map<String, String>
     *
     * @param jsonb
     * @return
     */
    public static Map<String, String> toMap(String jsonb) {
        Gson gson = new Gson();
        return gson.fromJson(jsonb, Map.class);
    }

    /**
     * Парсер из Map<String, String> to JSONB
     * @param map
     * @return
     */
    public static String toJSONB(Map<String, String> map) {
        Gson gson = new Gson();
        return gson.toJson(map, Map.class);
    }

}
