package com.foodgo.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@Slf4j
@SpringBootApplication
public class FoodGoApplication {
  public static void main(String[] args) {
    SpringApplication.run(FoodGoApplication.class, args);
  }

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String dbUser;

  @Value("${spring.datasource.password}")
  private String dbPassword;

  @Bean
  public CommandLineRunner printConnectionDetails(@Autowired DataSource dataSource) {
    return args -> {
      try {
        // Lấy thông tin Driver Name từ DataSource Metadata
        String driver = dataSource.getConnection().getMetaData().getDriverName();

        log.info("--------------------------------------------------");
        log.info("✨ PostgreSQL Connection Details (Development Only):");
        log.info("👉 URL: {}", dbUrl);
        log.info("👉 User: {}", dbUser);
        log.info("👉 Password: {}", dbPassword != null ? "***" : "null");
        log.info("👉 Driver: {}", driver);
        log.info("--------------------------------------------------");
      } catch (Exception e) {
        log.error("❌ ERROR: Could not retrieve DataSource details", e);
      }
    };
  }
}
