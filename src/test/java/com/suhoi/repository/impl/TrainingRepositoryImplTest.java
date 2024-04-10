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

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrainingRepositoryImplTest {

    private TrainingRepository trainingRepository = TrainingRepositoryImpl.getInstance();
    @BeforeAll
    static void initDB() {
        InitDBTest.importData();
        UserUtils.setCurrentUser(new User(1L, "user1", "user1", Role.SIMPLE));
    }

    @Test
    @DisplayName("getTrainOrderByDate success for simple user")
    void test1() {
        List<TrainingDto> res = trainingRepository.getTrainOrderByDate(1L);
        assertThat(res).isNotEmpty();
    }

    @Test
    @DisplayName("getTrainOrderByDate failed, data not found")
    void test2() {
        List<TrainingDto> res = trainingRepository.getTrainOrderByDate(3L);
        assertThat(res).isEmpty();
    }
    @Test
    @DisplayName("update success")
    void test3() {
        UpdateTrainingDto updateTrainingDto = UpdateTrainingDto.builder()
                .id(1L)
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
    void test4() {
        trainingRepository.delete(1L);
        Training test = RuntimeDB.getTrainings().get(0);
        assertThat(test.getId()).isEqualTo(2);
    }
}
