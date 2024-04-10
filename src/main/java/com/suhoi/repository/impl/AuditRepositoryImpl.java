package com.suhoi.repository.impl;

import com.suhoi.model.Audit;
import com.suhoi.repository.AuditRepository;
import com.suhoi.repository.RuntimeDB;

import java.util.List;

public class AuditRepositoryImpl implements AuditRepository {

    private static volatile AuditRepositoryImpl INSTANCE;

    private AuditRepositoryImpl() {
    }

    public static AuditRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (AuditRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AuditRepositoryImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void save(Audit audit) {
        RuntimeDB.addAudit(audit);
    }

    @Override
    public List<Audit> getAll() {
        return RuntimeDB.getAudits();
    }
}
