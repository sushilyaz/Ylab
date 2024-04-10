package com.suhoi.service.impl;

import com.suhoi.model.Audit;
import com.suhoi.repository.AuditRepository;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.repository.impl.AuditRepositoryImpl;
import com.suhoi.repository.impl.TrainingRepositoryImpl;
import com.suhoi.service.AuditService;
import com.suhoi.service.TypeOfTrainingService;

import java.util.List;

/**
 * Javadoc в интерфейсе
 */
public class AuditServiceImpl implements AuditService {

    private static volatile AuditServiceImpl INSTANCE;

    private final AuditRepository auditRepository;

    private AuditServiceImpl() {
        this.auditRepository = AuditRepositoryImpl.getInstance();
    }

    public static AuditServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (AuditServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AuditServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void save(Audit audit) {
        auditRepository.save(audit);
    }

    @Override
    public List<Audit> getAll() {
        return auditRepository.getAll();
    }
}
