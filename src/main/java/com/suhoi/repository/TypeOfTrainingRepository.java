package com.suhoi.repository;

import com.suhoi.model.TypeOfTraining;

import java.util.List;
import java.util.Optional;

public interface TypeOfTrainingRepository {

    /**
     * INSERT
     *
     */
    void save(TypeOfTraining typeOfTraining);

    /**
     * SELECT * FROM type_of_trainings WHERE name = ?
     *
     * @param name
     * @return
     */
    Optional<TypeOfTraining> getTypeByName(String name);

    /**
     * SELECT * FROM type_of_trainings
     *
     * @return
     */
    List<TypeOfTraining> getTypesOfTrain();
}
