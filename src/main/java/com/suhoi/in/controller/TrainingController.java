package com.suhoi.in.controller;

import com.suhoi.annotation.Authorize;
import com.suhoi.dto.*;
import com.suhoi.facade.TrainingFacade;
import com.suhoi.model.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/training")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingFacade trainingFacade;

    @Authorize
    @PostMapping
    public ResponseEntity<Training> create(@RequestBody CreateTrainingDto createUserDto,
                                       UriComponentsBuilder uriBuilder) {

        Training training = trainingFacade.addNewTraining(createUserDto);
        return ResponseEntity
                .created(uriBuilder.path("/api/trainings/{id}")
                        .buildAndExpand(training.getId()).toUri())
                .body(training);

    }

    @Authorize
    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        List<TrainingDto> trainingsForUser = trainingFacade.getTrainingsForUser();
        return ResponseEntity.ok(trainingsForUser);
    }

    @Authorize
    @PatchMapping
    public ResponseEntity<String> update(@RequestBody UpdateTrainingDto updateUserDto) {
        trainingFacade.edit(updateUserDto);
        return ResponseEntity.ok().body("updated training success");
    }

    @Authorize
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody Long id) {
        trainingFacade.delete(id);
        return ResponseEntity.ok().body("deleted training success");
    }

    @Authorize
    @GetMapping("/statistic")
    public ResponseEntity<Integer> getTrainingStatistic(@RequestBody RangeDto dto) {
        Integer caloriesBetweenDate = trainingFacade.getCaloriesBetweenDate(dto);
        return ResponseEntity.ok().body(caloriesBetweenDate);
    }
}
