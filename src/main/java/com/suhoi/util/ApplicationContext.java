package com.suhoi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.suhoi.facade.TrainingFacade;
import com.suhoi.facade.TypeOfTrainingFacade;
import com.suhoi.facade.UserFacade;
import com.suhoi.facade.impl.TrainingFacadeImpl;
import com.suhoi.facade.impl.TypeOfTrainingFacadeImpl;
import com.suhoi.facade.impl.UserFacadeImpl;
import com.suhoi.mapper.TrainingMapper;
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
import jakarta.servlet.ServletContext;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * DI вынес в класс
 * Что касается мапы - она по факту нужна для аспектов, чтобы инициализировать постоянно одну и ту же зависимость auditService,
 * поэтому только его добавил. По-хорошему надо было все объекты в нее добавить, но на данном этапе в приложении этого не нужно
 */
public class ApplicationContext {

    @Getter
    private static final Map<String, Object> beanMap = new HashMap<String, Object>();

    public static void dependencyInjection (ServletContext context) {

        AuditRepository auditRepository = new AuditRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        TrainingRepository trainingRepository = new TrainingRepositoryImpl();
        TypeOfTrainingRepository typeOfTrainingRepository = new TypeOfTrainingRepositoryImpl();

        AuditService auditService = new AuditServiceImpl(auditRepository);
        TrainingService trainingService = new TrainingServiceImpl(trainingRepository);
        UserService userService = new UserServiceImpl(userRepository);
        TypeOfTrainingService typeOfTrainingService = new TypeOfTrainingServiceImpl(typeOfTrainingRepository);

        TypeOfTrainingFacade typeOfTrainingFacade = new TypeOfTrainingFacadeImpl(typeOfTrainingService);
        UserFacade userFacade = new UserFacadeImpl(userService);
        TrainingFacade trainingFacade = new TrainingFacadeImpl(trainingService, typeOfTrainingService);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        beanMap.put("auditService", auditService);

        context.setAttribute("objectMapper", objectMapper);
        context.setAttribute("typeOfTrainingFacade", typeOfTrainingFacade);
        context.setAttribute("userFacade", userFacade);
        context.setAttribute("trainingFacade", trainingFacade);
        context.setAttribute("auditService", auditService);
        context.setAttribute("trainingMapper", TrainingMapper.MAPPER);
    }
}
