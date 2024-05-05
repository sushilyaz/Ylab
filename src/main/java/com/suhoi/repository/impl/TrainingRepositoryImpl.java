package com.suhoi.repository.impl;

import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.model.Training;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.util.ConnectionPool;
import com.suhoi.util.Parser;
import com.suhoi.util.QuerySQL;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class TrainingRepositoryImpl implements TrainingRepository {

    private static final String GET_TRAINING_FOR_DATE_SQL = QuerySQL.GET_TRAINING_FOR_DATE_SQL;

    private static final String SAVE_SQL = QuerySQL.SAVE_SQL_TRAINING;

    private static final String GET_ALL_BY_USER_ID_SQL = QuerySQL.GET_ALL_BY_USER_ID_SQL;

    private static final String UPDATE_SQL = QuerySQL.UPDATE_SQL;

    private static final String DELETE_SQL = QuerySQL.DELETE_SQL;

    private static final String FIND_ALL_SQL = QuerySQL.FIND_ALL_SQL;

    private static final String FIND_BY_ID_SQL = QuerySQL.FIND_BY_ID_SQL;

    private static final String GET_TRAININGS_BETWEEN_DATE_SQL = QuerySQL.GET_TRAININGS_BETWEEN_DATE_SQL;

    @Override
    public Optional<Training> getTrainingForDateById(Long userId, Long typeOfTrainingId, LocalDate date) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TRAINING_FOR_DATE_SQL)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, typeOfTrainingId);
            preparedStatement.setObject(3, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            Training training = null;
            if (resultSet.next()) {
                training = buildTraining(resultSet);
            }
            return Optional.ofNullable(training);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Training training) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, training.getUserId());
            preparedStatement.setLong(2, training.getTypeOfTrainingId());
            preparedStatement.setString(3, String.valueOf(training.getDuration()));
            preparedStatement.setInt(4, training.getCalories());
            preparedStatement.setObject(5, training.getDate());
            preparedStatement.setObject(6, Parser.toJSONB(training.getAdvanced()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            connection.commit();
            if (generatedKeys.next()) {
                training.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Training> getAllByUserIdOrderByDate(Long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_USER_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Training> trainings = new ArrayList<>();
            while (resultSet.next()) {
                Training training = buildTraining(resultSet);
                trainings.add(training);
            }
            preparedStatement.close();
            resultSet.close();
            return trainings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UpdateTrainingDto dto) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, dto.getCalories());
            preparedStatement.setObject(2, Parser.toJSONB(dto.getAdvanced()));
            preparedStatement.setLong(3, dto.getId());
            preparedStatement.setLong(4, dto.getUserId());
            preparedStatement.executeUpdate();
//            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Training> getTrainBetweenDate(LocalDate startDate, LocalDate endDate, Long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TRAININGS_BETWEEN_DATE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setObject(2, startDate);
            preparedStatement.setObject(3, endDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Training> trainings = new ArrayList<>();
            while (resultSet.next()) {
                Training training = buildTraining(resultSet);
                trainings.add(training);
            }
            resultSet.close();
            preparedStatement.close();
            return trainings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Training> findAll() {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Training> trainings = new ArrayList<>();
            while (resultSet.next()) {
                Training training = buildTraining(resultSet);
                trainings.add(training);
            }
            resultSet.close();
            preparedStatement.close();
            return trainings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Training> findById(Long id, Long userId) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Training training = null;
            if (resultSet.next()) {
                training = buildTraining(resultSet);
            }
            resultSet.close();
            preparedStatement.close();
            return Optional.ofNullable(training);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Training buildTraining(ResultSet resultSet) throws SQLException {
        return Training.builder()
                .id(resultSet.getLong("id"))
                .userId(resultSet.getLong("user_id"))
                .typeOfTrainingId(resultSet.getLong("type_of_training_id"))
                .duration(Duration.parse(resultSet.getString("duration")))
                .calories(resultSet.getInt("calories"))
                .advanced(Parser.toMap(resultSet.getString("advanced")))
                .date(resultSet.getDate("date").toLocalDate())
                .build();
    }
}
