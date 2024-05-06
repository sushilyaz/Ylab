package com.suhoi.config;

import com.suhoi.util.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
@Data
public class YamlReader {

    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;
    @Value("${datasource.pool-size}")
    private String poolSize;
    @Value("${liquibase.change-log}")
    private String liquibaseChangeLog;
    @Value("${liquibase.liquibase-schema}")
    private String liquibaseSchema;
}
