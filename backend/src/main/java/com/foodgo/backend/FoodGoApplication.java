package com.foodgo.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

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

        System.out.println("--------------------------------------------------");
        System.out.println("✨ PostgreSQL Connection Details (Development Only):");

        // Sử dụng các biến đã tiêm
        System.out.println("👉 URL: " + dbUrl);
        System.out.println("👉 User: " + dbUser);
        System.out.println("👉 Password: " + dbPassword);
        System.out.println("👉 Driver: " + driver);
        System.out.println("--------------------------------------------------");
      } catch (Exception e) {
        System.err.println("❌ ERROR: Could not retrieve DataSource details. " + e.getMessage());
      }
    };
  }
}
