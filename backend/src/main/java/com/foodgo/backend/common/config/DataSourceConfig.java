package com.foodgo.backend.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Configuration class to handle database connection for production environments
 * like Render, which provides DATABASE_URL in postgres:// format
 */
@Slf4j
@Configuration
@Profile("prod")
public class DataSourceConfig {

    private final Environment environment;

    public DataSourceConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * Configure DataSource by parsing DATABASE_URL if provided
     * Render provides DATABASE_URL in format: postgres:// or postgresql:// user:password@host:port/dbname
     * Spring Boot expects: jdbc:postgresql://host:port/dbname with separate username/password
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        String databaseUrl = environment.getProperty("DATABASE_URL");
        
        // If DATABASE_URL is provided and in postgres:// or postgresql:// format, parse it
        if (databaseUrl != null && !databaseUrl.isEmpty() && (databaseUrl.startsWith("postgres://") || databaseUrl.startsWith("postgresql://"))) {
            try {
                URI dbUri = new URI(databaseUrl);
                
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String host = dbUri.getHost();
                int port = dbUri.getPort() == -1 ? 5432 : dbUri.getPort();
                String database = dbUri.getPath().replaceFirst("/", "");
                
                // Build JDBC URL
                String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
                
                log.info("✅ Parsed DATABASE_URL from Render format");
                log.info("   Host: {}", host);
                log.info("   Port: {}", port);
                log.info("   Database: {}", database);
                log.info("   Username: {}", username);
                
                return DataSourceBuilder.create()
                        .url(jdbcUrl)
                        .username(username)
                        .password(password)
                        .driverClassName("org.postgresql.Driver")
                        .build();
                
            } catch (URISyntaxException | ArrayIndexOutOfBoundsException e) {
                log.error("❌ Failed to parse DATABASE_URL: {}", databaseUrl, e);
                log.warn("⚠️  Falling back to default datasource configuration");
                // Fall through to default configuration
            }
        } else if (databaseUrl != null && !databaseUrl.isEmpty() && (databaseUrl.startsWith("jdbc:postgresql://") || databaseUrl.startsWith("jdbc:postgres://"))) {
            // Already in JDBC format, use as-is
            log.info("✅ Using DATABASE_URL in JDBC format");
            String username = environment.getProperty("DATABASE_USERNAME", 
                    environment.getProperty("spring.datasource.username", "admin"));
            String password = environment.getProperty("DATABASE_PASSWORD", 
                    environment.getProperty("spring.datasource.password", "admin"));
            
            return DataSourceBuilder.create()
                    .url(databaseUrl)
                    .username(username)
                    .password(password)
                    .driverClassName("org.postgresql.Driver")
                    .build();
        }
        
        // DATABASE_URL not provided or empty, use default configuration from application-prod.yml
        log.info("ℹ️  DATABASE_URL not provided, using spring.datasource.* properties");
        String defaultUrl = environment.getProperty("spring.datasource.url", "jdbc:postgresql://localhost:5432/foodgo_db");
        String defaultUsername = environment.getProperty("spring.datasource.username", "admin");
        String defaultPassword = environment.getProperty("spring.datasource.password", "admin");
        
        return DataSourceBuilder.create()
                .url(defaultUrl)
                .username(defaultUsername)
                .password(defaultPassword)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}

