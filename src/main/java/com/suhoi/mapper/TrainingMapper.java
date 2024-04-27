package com.suhoi.mapper;

import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.dto.TrainingDto;
import com.suhoi.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Маппер для сущности тренировок
 */
@Mapper
public interface TrainingMapper {
    TrainingMapper MAPPER = Mappers.getMapper(TrainingMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "typeOfTrainingId", ignore = true)
    @Mapping(target = "date",expression = "java(java.time.LocalDate.now())")
    Training toEntity(CreateTrainingDto createTrainingDto);
    TrainingDto toDto(Training training);
}
