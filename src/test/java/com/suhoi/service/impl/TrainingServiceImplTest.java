package com.suhoi.service.impl;

import com.suhoi.dto.RangeDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.model.Role;
import com.suhoi.model.Training;
import com.suhoi.model.User;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.service.AuditService;
import com.suhoi.service.TrainingService;
import com.suhoi.util.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    private TrainingServiceImpl trainingService;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private AuditService auditService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingService = new TrainingServiceImpl(trainingRepository, auditService);
        UserUtils.setCurrentUser(new User(1L, "user1", "user1", Role.SIMPLE));
    }

    @Test
    @DisplayName("getTrainingForDateById without exception")
    void testGetTrainingForDateByIdTestSuccess() {
        Training training = Training.builder()
                .typeOfTrainingId(1L)
                .userId(1L)
                .duration(Duration.ofHours(1))
                .calories(5000)
                .advanced(new HashMap<>())
                .date(LocalDate.now())
                .build();

        when(trainingRepository.getTrainingForDateById(1L, 1L, LocalDate.now()))
                .thenReturn(java.util.Optional.empty());

        trainingService.addTrainingIfNotExist(training);

        verify(trainingRepository, times(1)).getTrainingForDateById(1L, 1L, LocalDate.now());
        verify(trainingRepository, times(1)).save(training);
    }

    @Test
    @DisplayName("getAllForUser without exception")
    void testGetAllForUserWithoutException() {
        // Arrange
        List<Training> mockTrainings = new ArrayList<>();
        mockTrainings.add(Training.builder()
                .typeOfTrainingId(1L)
                .userId(1L)
                .duration(Duration.ofHours(1))
                .calories(5000)
                .advanced(new HashMap<>())
                .date(LocalDate.now())
                .build());

        when(trainingRepository.getAllByUserIdOrderByDate(anyLong())).thenReturn(mockTrainings);

        List<Training> result = trainingService.getAllForUser();

        assertNotNull(result);
        assertEquals(mockTrainings, result);
        verify(trainingRepository, times(1)).getAllByUserIdOrderByDate(anyLong());
    }

    @Test
    @DisplayName("getTrainsBetweenDate without exception")
    void testGetTrainsBetweenDateSuccess() {
        Training gym = Training.builder()
                .duration(Duration.ofHours(1))
                .calories(5000)
                .advanced(new HashMap<>())
                .typeOfTrainingId(2L)
                .date(LocalDate.parse("2024-04-07"))
                .build();
        List<Training> testList = new ArrayList<>();
        testList.add(gym);

        when(trainingRepository.getTrainBetweenDate(LocalDate.parse("2024-04-06"), LocalDate.parse("2024-04-08"), UserUtils.getCurrentUser().getId())).thenReturn(testList);
        RangeDto dto = RangeDto.builder()
                .startDate(LocalDate.parse("2024-04-06"))
                .endDate(LocalDate.parse("2024-04-08"))
                .build();
        Integer trainsBetweenDate = trainingService.getTrainsBetweenDate(dto);
        assertThat(trainsBetweenDate).isEqualTo(5000);
    }

    @Test
    @DisplayName("getAllTrainsByUserId without exception")
    void testGetAllTrainsByUserIdSuccess() {
        Training build = Training.builder()
                .id(1L)
                .build();

        List<Training> testList = new ArrayList<>();
        testList.add(build);
        when(trainingRepository.getAllByUserIdOrderByDate(UserUtils.getCurrentUser().getId())).thenReturn(testList);
        List<Training> res = trainingService.getAllForUser();
        assertThat(res).isNotEmpty();
    }

    @Test
    @DisplayName("deleteById without exception")
    void testDeleteByIdWithoutException() {
        Training build = Training.builder()
                .id(1L)
                .build();
        List<Training> testList = new ArrayList<>();
        testList.add(build);
        Long trainingId = 1L;
        when(trainingRepository.getAllByUserIdOrderByDate(UserUtils.getCurrentUser().getId())).thenReturn(testList);
        trainingService.deleteById(trainingId);

        verify(trainingRepository, times(1)).delete(trainingId);
    }
    @Test
    @DisplayName("update without exception")
    void testUpdate() {
        UpdateTrainingDto dto = UpdateTrainingDto.builder()
                .userId(1L)
                .calories(1000)
                .advanced(new HashMap<>())
                .build();

        trainingService.update(dto);

        verify(trainingRepository, times(1)).update(dto);
    }
}
