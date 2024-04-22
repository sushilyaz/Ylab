package com.suhoi.repository.impl;

import com.github.dockerjava.api.model.*;
import com.suhoi.util.ConnectionPool;
import com.suhoi.util.LiquibaseRunner;
import liquibase.Liquibase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class PostgresContainer {
    private static final String POSTGRES_IMAGE_VERSION = "postgres:16";

    protected static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>(POSTGRES_IMAGE_VERSION)
            .withDatabaseName("suhoiTest")
            .withUsername("suhoiTest")
            .withPassword("qwerty")
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5433), new ExposedPort(5432)))
            ));

    @BeforeAll
    public static void init() {
        POSTGRES_CONTAINER.start();
        ConnectionPool.initConnectionPool();
        LiquibaseRunner.runLiquibaseMigration();
    }

    @AfterAll
    public static void destroy() {
        POSTGRES_CONTAINER.stop();
        ConnectionPool.closePool();
    }
}
