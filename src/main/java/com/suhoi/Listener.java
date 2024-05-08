package com.suhoi;

import com.suhoi.util.ConnectionPool;
import com.suhoi.util.LiquibaseRunner;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Listener implements ApplicationListener<ContextRefreshedEvent> {
    private final LiquibaseRunner liquibaseRunner;
    private final ConnectionPool connectionPool;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        connectionPool.initConnectionPool();
        liquibaseRunner.runLiquibaseMigration();
    }
}
