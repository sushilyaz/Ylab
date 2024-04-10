package com.suhoi.repository;

import com.suhoi.model.Audit;
import com.suhoi.model.Training;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.model.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Имитация БДшки
 */
public class RuntimeDB {

    private static Long seq_users = 1L;
    private static Long seq_training = 1L;
    private static Long seq_type_of_training = 1L;
    private static Long seq_audit = 1L;

    @Getter
    private static List<User> users = new ArrayList<>();
    @Getter
    private static List<Training> trainings = new ArrayList<>();
    @Getter
    private static List<TypeOfTraining> type_of_training = new ArrayList<>();
    @Getter
    private static List<Audit> audits = new ArrayList<>();

    public static void addUser(User user) {
        user.setId(seq_users++);
        users.add(user);
    }

    public static void addTrain(Training training) {
        training.setId(seq_training++);
        trainings.add(training);
    }

    public static void addTypeOfTrain(TypeOfTraining typeOfTraining) {
        typeOfTraining.setId(seq_type_of_training++);
        type_of_training.add(typeOfTraining);
    }
    public static void addAudit(Audit audit) {
        audit.setId(seq_audit++);
        audits.add(audit);
    }
}
