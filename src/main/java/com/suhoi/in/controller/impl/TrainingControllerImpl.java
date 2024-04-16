package com.suhoi.in.controller.impl;

import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.dto.RangeDto;
import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.facade.TrainingFacade;
import com.suhoi.facade.impl.TrainingFacadeImpl;
import com.suhoi.in.controller.TrainingController;
import com.suhoi.model.Training;

import java.util.List;

public class TrainingControllerImpl implements TrainingController {

    private final TrainingFacade trainingFacade;

    public TrainingControllerImpl(TrainingFacade trainingFacade) {
        this.trainingFacade = trainingFacade;
    }
    //    private static volatile TrainingControllerImpl INSTANCE;
//    private final TrainingFacade trainingFacade;
//
//    private TrainingControllerImpl() {
//        this.trainingFacade = TrainingFacadeImpl.getInstance();
//    }
//
//    public static TrainingControllerImpl getInstance() {
//        if (INSTANCE == null) {
//            synchronized (TrainingControllerImpl.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new TrainingControllerImpl();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    @Override
    public void addNewTraining(CreateTrainingDto dto) {
        trainingFacade.addNewTraining(dto);
    }



    @Override
    public List<TrainingDto> getAllTrainingForUser() {
        return trainingFacade.getTrainingsForUser();
    }

    @Override
    public List<Training> getAllTrainingForUserWithId() {
        return trainingFacade.getAllTrainingsForUserWithId();
    }

    @Override
    public Integer getCaloriesBetweenDates(RangeDto dto) {
        return trainingFacade.getCaloriesBetweenDate(dto);
    }

    @Override
    public void edit(UpdateTrainingDto dto) {
        trainingFacade.edit(dto);
    }

    @Override
    public void delete(Long id) {
        trainingFacade.delete(id);
    }
}
