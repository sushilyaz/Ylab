package com.suhoi.in.controller;

public interface AuditController {
    void save(String action);

    void getAll();
}
