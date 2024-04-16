package com.suhoi.in.console;

import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.dto.RangeDto;
import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.exception.PermissionDeniedException;
import com.suhoi.in.controller.AuditController;
import com.suhoi.in.controller.TrainingController;
import com.suhoi.in.controller.TypeOfTrainingController;
import com.suhoi.in.controller.impl.AuditControllerImpl;
import com.suhoi.in.controller.impl.TrainingControllerImpl;
import com.suhoi.in.controller.impl.TypeOfTrainingControllerImpl;
import com.suhoi.model.Audit;
import com.suhoi.model.Role;
import com.suhoi.model.Training;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.util.DI;
import com.suhoi.util.Parser;
import com.suhoi.util.UserUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuHandler {

    private static final TrainingController trainingController = DI.trainingControllerDI();
    private static final TypeOfTrainingController typeOfTrainingController = DI.typeOfTrainingControllerDI();
    private static final AuditController auditController = DI.auditControllerDI();

    /**
     * Взаимодействие с пользователем в окне добавления новой тренировки
     */
    public static void addNewTraining() {
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
            addNewTraining();
        }

        Map<String, String> advanced = getAdvanced(scanner);

        CreateTrainingDto createTrainingDto = CreateTrainingDto.builder()
                .typeOfTrain(typeOfTrain)
                .calories(calories)
                .duration(duration)
                .advanced(advanced)
                .build();

        trainingController.addNewTraining(createTrainingDto);
        System.out.println("Data added success!");
    }

    /**
     * Взаимодействие с пользователем в окне получения тренировок пользователя
     */
    public static void getAllTrainingsForUser() {
        System.out.println();
        System.out.println("All trainings sorted by date: ");
        List<TrainingDto> trains = trainingController.getAllTrainingForUser();
        for (TrainingDto train : trains) {
            System.out.println(train);
        }
    }

    /**
     * Взаимодействие с пользователем в окне получения суммы калорий в срезе времени
     */
    public static void getCaloriesBetweenDates() {
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
            getCaloriesBetweenDates();
        }
        RangeDto build = RangeDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
        Integer calories = trainingController.getCaloriesBetweenDates(build);
        System.out.println(calories);
    }

    /**
     * Взаимодействие с пользователем в окне обновления тренировки
     */
    public static void edit() {
        System.out.println("""
                Enter the ID of the record you want to update:
                You can update only CALORIES and ADVANCED
                """);
        List<Training> allTrainings = trainingController.getAllTrainingForUserWithId();
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
            TrainingDailyRunner.menu();
        }
        System.out.print("Enter new calories: ");
        Integer calories = 0;
        try {
            calories = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid number");
            TrainingDailyRunner.menu();
        }
        System.out.println("Enter new optional info: ");
        Map<String, String> advanced = getAdvanced(scanner);

        UpdateTrainingDto build = UpdateTrainingDto.builder()
                .id(id)
                .calories(calories)
                .advanced(advanced)
                .build();

        trainingController.edit(build);
    }

    /**
     * Взаимодействие с пользователем в окне удаления тренировки
     */
    public static void delete() {
        System.out.println("""
                Enter the ID of the record you want to delete:
                """);
        List<Training> allTrainings = trainingController.getAllTrainingForUserWithId();
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
            TrainingDailyRunner.menu();
        }
        trainingController.delete(id);
    }

    /**
     * Взаимодействие с админом в окне добавления нового ТИПА тренировки
     */
    public static void addNewTypeOfTraining() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new type of train: ");
        String typeOfTrain = scanner.nextLine();

        typeOfTrainingController.addNewTypeOfTrainings(typeOfTrain);
    }

    /**
     * Взаимодействие с админом в окне получения аудита
     */
    public static void getAudit() {
        List<Audit> audits = auditController.getAll();
        for (Audit audit : audits) {
            System.out.println(audit);
        }
    }

    private static Map<String, String> getAdvanced(Scanner scanner) {
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
