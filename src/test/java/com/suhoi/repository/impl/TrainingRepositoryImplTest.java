package com.suhoi.repository.impl;

import com.suhoi.InitDBTest;
import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.model.Role;
import com.suhoi.model.Training;
import com.suhoi.model.User;
import com.suhoi.repository.RuntimeDB;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.util.UserUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainingRepositoryImplTest {

    private TrainingRepository trainingRepository = TrainingRepositoryImpl.getInstance();
    @BeforeAll
    static void initDB() {
        InitDBTest.importData();
        UserUtils.setCurrentUser(new User(1L, "user1", "user1", Role.SIMPLE));
    }

    @Test
    @DisplayName("getTrainOrderByDate success for simple user")
    void testGetTrainOrderByDateSuccess() {
        List<Training> res = trainingRepository.getAllByUserIdOrderByDate(1L);
        assertThat(res).isNotEmpty();
    }

    @Test
    @DisplayName("getTrainOrderByDate failed, data not found")
    void testGetTrainOrderByDateFailed() {
        List<Training> res = trainingRepository.getAllByUserIdOrderByDate(3L);
        assertThat(res).isEmpty();
    }
    @Test
    @DisplayName("update success")
    void testUpdateSuccess() {
        UpdateTrainingDto updateTrainingDto = UpdateTrainingDto.builder()
                .id(1L)
                .userId(1L)
                .calories(10000)
                .advanced(new HashMap<>())
                .build();

        trainingRepository.update(updateTrainingDto);
        Training test = RuntimeDB.getTrainings().get(0);
        assertThat(test.getAdvanced()).isEmpty();
        assertThat(test.getCalories()).isEqualTo(10000);
    }
    @Test
    @DisplayName("delete success")
    void testDeleteSuccess() {
        trainingRepository.delete(1L, 1L);
        Training test = RuntimeDB.getTrainings().get(0);
        assertThat(test.getId()).isEqualTo(2);
    }
    @Test
    @DisplayName("findAll - Trainings exist for user success")
    void testFindAllTrainingsExistForUserSuccess() {
        List<Training> result = trainingRepository.findAll(1L);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("getTrainBetweenDate - No trainings between dates for user")
    void testGetTrainBetweenDateNoTrainingsBetweenDatesForUser() {
        // Arrange
        Long userId = 1L;
        LocalDate startDate = LocalDate.of(2024, 4, 4);
        LocalDate endDate = LocalDate.of(2024, 4, 6);

        List<Training> result = trainingRepository.getTrainBetweenDate(startDate, endDate, userId);

        assertEquals(1, result.size());
    }
}
