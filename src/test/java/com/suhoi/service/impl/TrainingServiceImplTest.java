package com.suhoi.service.impl;

import com.suhoi.InitDBTest;
import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.dto.TrainingDto;
import com.suhoi.model.Role;
import com.suhoi.model.Training;
import com.suhoi.model.User;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.service.TrainingService;
import com.suhoi.service.TypeOfTrainingService;
import com.suhoi.util.UserUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TypeOfTrainingService typeOfTrainingService;

    @Mock
    private TrainingRepository trainingRepository;

    private TrainingService trainingService;

    @BeforeAll
    static void initTestDB() {
        InitDBTest.importData();
    }
    @BeforeEach
    void init() {
        trainingService = new TrainingServiceImpl(trainingRepository);
        UserUtils.setCurrentUser(new User(1L, "user1", "user1", Role.SIMPLE));
    }

    @Test
    @DisplayName("addTrain without exception")
    void test1() {
        CreateTrainingDto dto = CreateTrainingDto.builder()
                .duration(Duration.ofHours(1))
                .calories(5000)
                .advanced(new HashMap<>())
                .typeOfTrain("GYM")
                .build();
        trainingService.addTrain(dto);
        verify(trainingRepository, times(1)).save(any(Training.class));
    }

    @Test
    @DisplayName("getTrains for simple user success")
    void test2() {
        trainingService.getTrains();
        verify(trainingRepository, times(1)).getTrainOrderByDate(any(Long.class));
    }

    @Test
    @DisplayName("getTrains for simple user failed")
    void test3() {
        trainingService.getTrains();
        verify(trainingRepository, never()).getTrainOrderByDate();
    }

    @Test
    @DisplayName("getTrainsBetweenDate success")
    void test4() {
        TrainingDto gym = TrainingDto.builder()
                .duration(Duration.ofHours(1))
                .calories(5000)
                .advanced(new HashMap<>())
                .typeOfTrain("GYM")
                .date(LocalDate.parse("2024-04-07"))
                .build();
        List<TrainingDto> testList = new ArrayList<>();
        testList.add(gym);

        when(trainingRepository.getTrainBetweenDate(LocalDate.parse("2024-04-06"), LocalDate.parse("2024-04-08"), UserUtils.getCurrentUser().getId())).thenReturn(testList);
        Integer trainsBetweenDate = trainingService.getTrainsBetweenDate(LocalDate.parse("2024-04-06"), LocalDate.parse("2024-04-08"));
        assertThat(trainsBetweenDate).isEqualTo(5000);
    }

    @Test
    @DisplayName("getAllTrainsByUserId success")
    void test5() {
        Training build = Training.builder()
                .id(1L)
                .build();

        List<Training> testList = new ArrayList<>();
        testList.add(build);
        when(trainingRepository.findAll(UserUtils.getCurrentUser().getId())).thenReturn(testList);
        List<Training> res = trainingService.getAllTrainsByUserId();
        assertThat(res).isNotEmpty();
    }
}
