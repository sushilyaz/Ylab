package com.suhoi.repository;

import com.suhoi.model.Train;
import com.suhoi.model.TypeOfTrain;
import com.suhoi.model.User;
import com.suhoi.util.InitDB;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Имитация БДшки
 */
public class RuntimeDB {

    private static Long seq_users = 1L;
    private static Long seq_trains = 1L;
    private static Long seq_type_of_trains = 1L;

    @Getter
    private static List<User> users = new ArrayList<>();
    @Getter
    private static List<Train> trains = new ArrayList<>();
    @Getter
    private static List<TypeOfTrain> type_of_trains = new ArrayList<>();

    public static void addUser(User user) {
        user.setId(seq_users++);
        users.add(user);
    }

    public static void addTrain(Train train) {
        train.setId(seq_trains++);
        trains.add(train);
    }

    public static void addTypeOfTrain(TypeOfTrain typeOfTrain) {
        typeOfTrain.setId(seq_type_of_trains++);
        type_of_trains.add(typeOfTrain);
    }

}
