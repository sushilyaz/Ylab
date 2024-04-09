package com.suhoi.in.controller.impl;

import com.suhoi.dto.CreateTrainDto;
import com.suhoi.dto.TrainDto;
import com.suhoi.in.TrainingDailyRunner;
import com.suhoi.in.controller.TrainController;
import com.suhoi.service.TrainService;
import com.suhoi.service.impl.TrainServiceImpl;
import com.suhoi.util.Parser;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TrainControllerImpl implements TrainController {

    private final TrainService trainService;

    public TrainControllerImpl() {
        this.trainService = TrainServiceImpl.getInstance();
    }

    @Override
    public void addTrain() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter type of train: ");
        String typeOfTrain = scanner.nextLine();

        System.out.print("Enter duration of train. Format: '1:20' (means 1 hours 20 minutes): ");
        String stringDuration = scanner.nextLine();
        Duration duration = Parser.durationParse(stringDuration);

        System.out.print("Enter burned calories: ");
        Integer calories = null;
        try {
            calories = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid number");
            addTrain();
        }

        System.out.println("Enter optional info. Format: key: 'smth' value: 'smth'");
        Map<String, String> advanced = new HashMap<>();
        while (true) {
            System.out.print("Do you want add optional? (yes/no): ");
            scanner.nextLine();
            String addField = scanner.nextLine().trim();
            if (addField.equalsIgnoreCase("no")) {
                break;
            } else if (!addField.equalsIgnoreCase("yes")) {
                System.out.println("Invalid data. Please write 'yes' or 'no'.");
                continue;
            }

            System.out.print("Enter key: ");
            String key = scanner.nextLine().trim();

            System.out.print("Enter values: ");
            String value = scanner.nextLine().trim();

            advanced.put(key, value);
        }



        CreateTrainDto dto = CreateTrainDto.builder()
                .typeOfTrain(typeOfTrain)
                .calories(calories)
                .duration(duration)
                .advanced(advanced)
                .build();

        trainService.addTrain(dto);
        System.out.println("Data added success!");
        TrainingDailyRunner.menu();
    }

    @Override
    public void getAllSortedTrainings() {
        System.out.println("All trainings sorted by date: ");
        List<TrainDto> trains = trainService.getTrains();
        for (TrainDto train : trains) {
            System.out.println(train);
        }
        TrainingDailyRunner.menu();
    }

    @Override
    public void getCaloriesBetweenDates() {
        System.out.println("""
                You must enter the date in the format yyyy-mm-dd
                For example: 2024-04-07 (НЕ ВКЛЮЧИТЕЛЬНО !!! НЕРАВЕНСТВО НЕ СТРОГОЕ !!!)
                """);
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter start date: ");
        String startDateString = scanner.nextLine();
        System.out.print("Enter end date: ");
        String endDateString = scanner.nextLine();
        LocalDate startDate = null;
        LocalDate endDate = null;
        try {
            startDate = LocalDate.parse(startDateString);
            endDate = LocalDate.parse(endDateString);
        } catch (Exception e) {
            System.out.println("Cant parse your data");
            getAllSortedTrainings();
        }

        System.out.println(trainService.getTrainsBetweenDate(startDate, endDate));

        TrainingDailyRunner.menu();
    }
}
