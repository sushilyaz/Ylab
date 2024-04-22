package com.suhoi.util;

import com.suhoi.facade.TrainingFacade;
import com.suhoi.facade.TypeOfTrainingFacade;
import com.suhoi.facade.UserFacade;
import com.suhoi.facade.impl.TrainingFacadeImpl;
import com.suhoi.facade.impl.TypeOfTrainingFacadeImpl;
import com.suhoi.facade.impl.UserFacadeImpl;
import com.suhoi.in.controller.AuditController;
import com.suhoi.in.controller.TrainingController;
import com.suhoi.in.controller.TypeOfTrainingController;
import com.suhoi.in.controller.UserController;
import com.suhoi.in.controller.impl.AuditControllerImpl;
import com.suhoi.in.controller.impl.TrainingControllerImpl;
import com.suhoi.in.controller.impl.TypeOfTrainingControllerImpl;
import com.suhoi.in.controller.impl.UserControllerImpl;
import com.suhoi.repository.AuditRepository;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.repository.TypeOfTrainingRepository;
import com.suhoi.repository.UserRepository;
import com.suhoi.repository.impl.AuditRepositoryImpl;
import com.suhoi.repository.impl.TrainingRepositoryImpl;
import com.suhoi.repository.impl.TypeOfTrainingRepositoryImpl;
import com.suhoi.repository.impl.UserRepositoryImpl;
import com.suhoi.service.AuditService;
import com.suhoi.service.TrainingService;
import com.suhoi.service.TypeOfTrainingService;
import com.suhoi.service.UserService;
import com.suhoi.service.impl.AuditServiceImpl;
import com.suhoi.service.impl.TrainingServiceImpl;
import com.suhoi.service.impl.TypeOfTrainingServiceImpl;
import com.suhoi.service.impl.UserServiceImpl;

public class DI {
    public static UserController userControllerDI() {
        AuditRepository auditRepository = AuditRepositoryImpl.getInstance();
        AuditService auditService = new AuditServiceImpl(auditRepository);
        UserRepository userRepository = UserRepositoryImpl.getInstance();
        UserService userService = new UserServiceImpl(userRepository, auditService);
        UserFacade userFacade = new UserFacadeImpl(userService);
        return new UserControllerImpl(userFacade);
    }
    public static TrainingController trainingControllerDI() {
        AuditRepository auditRepository = AuditRepositoryImpl.getInstance();
        AuditService auditService = new AuditServiceImpl(auditRepository);
        TrainingRepository trainingRepository = TrainingRepositoryImpl.getInstance();
        TrainingService trainingService = new TrainingServiceImpl(trainingRepository, auditService);
        TypeOfTrainingRepository typeOfTrainingRepository = TypeOfTrainingRepositoryImpl.getInstance();
        TypeOfTrainingService typeOfTrainingService = new TypeOfTrainingServiceImpl(typeOfTrainingRepository, auditService);
        TrainingFacade trainingFacade = new TrainingFacadeImpl(trainingService, typeOfTrainingService);
        return new TrainingControllerImpl(trainingFacade);
    }

    public static TypeOfTrainingController typeOfTrainingControllerDI() {
        AuditRepository auditRepository = AuditRepositoryImpl.getInstance();
        AuditService auditService = new AuditServiceImpl(auditRepository);
        TypeOfTrainingRepository typeOfTrainingRepository = TypeOfTrainingRepositoryImpl.getInstance();
        TypeOfTrainingService typeOfTrainingService = new TypeOfTrainingServiceImpl(typeOfTrainingRepository, auditService);
        TypeOfTrainingFacade typeOfTrainingFacade = new TypeOfTrainingFacadeImpl(typeOfTrainingService);
        return new TypeOfTrainingControllerImpl(typeOfTrainingFacade);
    }

    public static AuditController auditControllerDI() {
        AuditRepository auditRepository = AuditRepositoryImpl.getInstance();
        AuditService auditService = new AuditServiceImpl(auditRepository);
        return new AuditControllerImpl(auditService);
    }
}
