package com.suhoi.in.controller.impl;

import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.in.TrainingDailyRunner;
import com.suhoi.in.controller.TrainingController;
import com.suhoi.model.Training;
import com.suhoi.service.TrainingService;
import com.suhoi.service.impl.TrainingServiceImpl;
import com.suhoi.util.Parser;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TrainingControllerImpl implements TrainingController {

    private final TrainingService trainingService;

    public TrainingControllerImpl() {
        this.trainingService = TrainingServiceImpl.getInstance();
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

        Map<String, String> advanced = getStringStringMap(scanner);


        CreateTrainingDto dto = CreateTrainingDto.builder()
                .typeOfTrain(typeOfTrain)
                .calories(calories)
                .duration(duration)
                .advanced(advanced)
                .build();

        trainingService.addTrain(dto);
        System.out.println("Data added success!");
        TrainingDailyRunner.menu();
    }



    @Override
    public void getAllSortedTrainings() {
        System.out.println("All trainings sorted by date: ");
        List<TrainingDto> trains = trainingService.getTrains();
        for (TrainingDto train : trains) {
            System.out.println(train);
        }
        TrainingDailyRunner.menu();
    }

    @Override
    public void getCaloriesBetweenDates() {
        System.out.println("""
                You must enter the date in the format yyyy-mm-dd
                For example: 2024-04-07 (NOT INCLUSIVE !!! INEQUALITY IS NOT STRICT !!!)
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

        System.out.println(trainingService.getTrainsBetweenDate(startDate, endDate));

        TrainingDailyRunner.menu();
    }

    @Override
    public void edit() {
        System.out.println("""
                Enter the ID of the record you want to update:
                You can update only CALORIES and ADVANCED
                """);
        List<Training> allTrainings = trainingService.getAllTrainsByUserId();
        for (Training training : allTrainings) {
            System.out.println(training);
        }
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ID: ");
        Long id = 0L;
        try {
            id = scanner.nextLong();
        } catch (Exception e) {
            System.out.println("Invalid number");
            edit();
        }

        System.out.print("Enter new calories: ");
        Integer calories = 0;
        try {
            calories = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid number");
            edit();
        }
        System.out.println("Enter new optional info: ");
        Map<String, String> advanced = getStringStringMap(scanner);

        UpdateTrainingDto build = UpdateTrainingDto.builder()
                .id(id)
                .calories(calories)
                .advanced(advanced)
                .build();
        trainingService.update(build);
        TrainingDailyRunner.menu();
    }

    @Override
    public void delete() {
        System.out.println("""
                Enter the ID of the record you want to delete:
                """);
        List<Training> allTrainings = trainingService.getAllTrainsByUserId();

        for (Training training : allTrainings) {
            System.out.println(training);
        }
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ID: ");
        Long id = 0L;
        try {
            id = scanner.nextLong();
        } catch (Exception e) {
            System.out.println("Invalid number");
            edit();
        }
        trainingService.deleteById(id);
        TrainingDailyRunner.menu();
    }
    private Map<String, String> getStringStringMap(Scanner scanner) {
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
        return advanced;
    }
}
