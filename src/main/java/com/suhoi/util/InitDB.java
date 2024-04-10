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
    public static void importData() {

        User user1 = User.builder()
                .username("user1")
                .password("user1")
                .role(Role.SIMPLE)
                .build();

        User user2 = User.builder()
                .username("user2")
                .password("user2")
                .role(Role.SIMPLE)
                .build();

        User admin = User.builder()
                .username("admin")
                .password("admin")
                .role(Role.ADMIN)
                .build();

        RuntimeDB.addUser(user1);
        RuntimeDB.addUser(user2);
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

        Training training3 = Training.builder()
                .userId(2L)
                .typeOfTrainingId(1L)
                .duration(Duration.ofHours(1))
                .calories(1000)
                .advanced(advanced)
                .date(LocalDate.now())
                .build();

        RuntimeDB.addTrain(training1);
        RuntimeDB.addTrain(training2);
        RuntimeDB.addTrain(training3);

        TypeOfTraining type1 = TypeOfTraining.builder()
                .name("GYM")
                .build();

        TypeOfTraining type2 = TypeOfTraining.builder()
                .name("YOGA")
                .build();

        RuntimeDB.addTypeOfTrain(type1);
        RuntimeDB.addTypeOfTrain(type2);

        System.out.println("Init database success");
    }
}
