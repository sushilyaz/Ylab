package com.suhoi.in.controller;

import com.suhoi.annotation.Authorize;
import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.model.Training;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.service.TypeOfTrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/type-of-training")
@RequiredArgsConstructor
public class TypeOfTrainingController {

    private final TypeOfTrainingService typeOfTrainingService;

    @Authorize
    @PostMapping
    public ResponseEntity<TypeOfTraining> create(@RequestBody String name,
                                           UriComponentsBuilder uriBuilder) {

        TypeOfTraining typeOfTraining = typeOfTrainingService.save(name);
        return ResponseEntity
                .created(uriBuilder.path("/{id}")
                        .buildAndExpand(typeOfTraining.getId()).toUri())
                .body(typeOfTraining);

    }
}
