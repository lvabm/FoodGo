package com.foodgo.backend.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;

/**
 * Reset sequences for H2 database after import.sql runs
 * This fixes the issue where profile ID conflicts occur when registering new users
 */
@Slf4j
@Component
@Order(1) // Run after data initialization
public class SequenceResetRunner implements CommandLineRunner {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public SequenceResetRunner(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            
            // Only reset sequences for H2 database
            if ("H2".equalsIgnoreCase(databaseProductName)) {
                log.info("🔄 Detected H2 database, resetting sequences...");
                resetH2Sequences();
                log.info("✅ H2 sequences reset completed");
            } else {
                log.debug("ℹ️  Database is not H2 ({}), skipping sequence reset", databaseProductName);
            }
        } catch (Exception e) {
            log.warn("⚠️  Could not reset sequences: {}", e.getMessage());
            // Don't fail startup if sequence reset fails
        }
    }

    private void resetH2Sequences() {
        try {
            // Reset Profile sequence (30 profiles imported from import.sql)
            jdbcTemplate.execute("ALTER TABLE profile ALTER COLUMN id RESTART WITH 31");
            log.debug("✅ Reset profile sequence to 31");
            
            // Reset other sequences if needed
            // Note: Only reset sequences for tables that have data imported from import.sql
            
        } catch (Exception e) {
            log.warn("⚠️  Error resetting H2 sequences: {}", e.getMessage());
            // Continue even if some sequences fail
        }
    }
}

