package com.suhoi.service.impl;

import com.suhoi.model.Audit;
import com.suhoi.repository.AuditRepository;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.repository.impl.AuditRepositoryImpl;
import com.suhoi.repository.impl.TrainingRepositoryImpl;
import com.suhoi.service.AuditService;
import com.suhoi.service.TypeOfTrainingService;
import com.suhoi.util.UserUtils;

import java.time.LocalDateTime;
import java.util.List;

public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;

    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public void save(String action) {
        Audit build = Audit.builder()
                .username(UserUtils.getCurrentUser().getUsername())
                .action(action)
                .dateTime(LocalDateTime.now())
                .build();
        auditRepository.save(build);
    }

    @Override
    public List<Audit> getAll() {
        return auditRepository.getAll();
    }
}
