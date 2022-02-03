package br.edu.ufcg.virtus.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.edu.ufcg.virtus.automation.dataset.ConfigData;
import br.edu.ufcg.virtus.automation.dataset.DBHelperData;

public class EnvironmentSetup {

    private static final Logger LOGGER = Logger.getLogger(EnvironmentSetup.class.getName());

    public static final List<String> LOCAL_ENVIRONMENTS = List.of("local");

    public static void setup(String env, String headless) {

        // Setup to run tests on headless mode
        if (Boolean.parseBoolean(headless)) {
            ConfigData.HEADLESS = true;
        } else {
            ConfigData.HEADLESS = false;
        }

        ConfigData.ENVIRONMENT = env.toLowerCase();
        boolean isLocalEnvironment = LOCAL_ENVIRONMENTS.contains(ConfigData.ENVIRONMENT);

        final String envFile = isLocalEnvironment ? "environment.properties" : String.format("environment.%s.properties", ConfigData.ENVIRONMENT);

        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(envFile)) {

            final Properties environment = new Properties();

            // load a properties filequa
            environment.load(input);

            ConfigData.PORTAL_APPLICATION_URL = environment.getProperty("webapp.portal");
            ConfigData.CORE_APPLICATION_URL = environment.getProperty("webapp.core");
            ConfigData.BUSINESS_APPLICATION_URL = environment.getProperty("webapp.business");

            DBHelperData.DATABASE_URL = environment.getProperty("database.url");

            if (isLocalEnvironment) {
                DBHelperData.DB_USER = environment.getProperty("database.username");
                DBHelperData.DB_PASSWORD = environment.getProperty("database.password");
            } else {
                DBHelperData.DB_USER = System.getenv(environment.getProperty("database.username"));
                DBHelperData.DB_PASSWORD = System.getenv(environment.getProperty("database.password"));
            }

            System.out.println("Environment loaded: " + ConfigData.ENVIRONMENT);

        } catch (final IOException ex) {
            LOGGER.log(Level.SEVERE, String.format("%s not found", envFile), ex);
        }
    }
}
