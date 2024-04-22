package com.suhoi.repository.impl;

import com.suhoi.model.TypeOfTraining;
import com.suhoi.repository.TypeOfTrainingRepository;
import com.suhoi.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TypeOfTrainingRepositoryImpl implements TypeOfTrainingRepository {

    private static final String SAVE_SQL = """
            INSERT INTO ylab.type_of_trainings (name)
            VALUES (?);
            """;

    private static final String GET_BY_NAME_SQL = """
            SELECT id, name
            FROM ylab.type_of_trainings
            WHERE name = ?;
            """;

    private static final String GET_ALL_SQL = """
            SELECT id, name
            FROM ylab.type_of_trainings
            """;

    @Override
    public void save(TypeOfTraining typeOfTraining) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, typeOfTraining.getName());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                typeOfTraining.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<TypeOfTraining> getTypeByName(String name) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME_SQL)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            TypeOfTraining typeOfTraining = null;
            if (resultSet.next()) {
                typeOfTraining = buildTypeOfTraining(resultSet);
            }
            return Optional.ofNullable(typeOfTraining);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TypeOfTraining> getTypesOfTrain() {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TypeOfTraining> typeOfTrainings = new ArrayList<>();
            while (resultSet.next()) {
                TypeOfTraining typeOfTraining = buildTypeOfTraining(resultSet);
                typeOfTrainings.add(typeOfTraining);
            }
            return typeOfTrainings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private TypeOfTraining buildTypeOfTraining(ResultSet resultSet) throws SQLException {
        return TypeOfTraining.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
