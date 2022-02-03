package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Use this class for a Spring Jdbc migration.
 */
@SuppressWarnings("deprecation")
public class V1_1__Init_Migration implements SpringJdbcMigration { // NOSONAR
    @Override
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        // Method for migrations
    }
}
