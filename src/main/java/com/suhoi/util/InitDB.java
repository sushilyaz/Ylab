package com.suhoi.util;

import com.suhoi.model.Role;
import com.suhoi.model.Training;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.model.User;
import com.suhoi.repository.RuntimeDB;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;


public class InitDB {
    /**
     * Инициализация БД
     */
    public static void importData() {

        User user1 = User.builder()
                .username("user1")
                .password("user1")
                .role(Role.SIMPLE)
                .build();

        User admin = User.builder()
                .username("admin")
                .password("admin")
                .role(Role.ADMIN)
                .build();

        RuntimeDB.addUser(user1);
        RuntimeDB.addUser(admin);

        Map<String, String> advanced = Map.of(
                "count excercise", "10",
                "weight", "20"
        );

        Training training1 = Training.builder()
                .userId(1L)
                .typeOfTrainingId(1L)
                .duration(Duration.ofHours(2))
                .calories(2000)
                .advanced(advanced)
                .date(LocalDate.now())
                .build();

        Training training2 = Training.builder()
                .userId(1L)
                .typeOfTrainingId(2L)
                .duration(Duration.ofHours(3))
                .calories(4000)
                .advanced(advanced)
                .date(LocalDate.parse("2024-04-05"))
                .build();

        RuntimeDB.addTrain(training1);
        RuntimeDB.addTrain(training2);

        TypeOfTraining type1 = TypeOfTraining.builder()
                .name("strength")
                .build();

        TypeOfTraining type2 = TypeOfTraining.builder()
                .name("yoga")
                .build();
        TypeOfTraining type3 = TypeOfTraining.builder()
                .name("cardio")
                .build();

        RuntimeDB.addTypeOfTrain(type1);
        RuntimeDB.addTypeOfTrain(type2);
        RuntimeDB.addTypeOfTrain(type3);

        System.out.println("Init database success");
    }
}
