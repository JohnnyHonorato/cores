package br.edu.ufcg.virtus.automation.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.edu.ufcg.virtus.automation.dataset.ConfigData;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumConfig {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final ChromeOptions options;

    public SeleniumConfig() {

        this.options = new ChromeOptions();
        this.options.addArguments("--lang=en");

        if (!EnvironmentSetup.LOCAL_ENVIRONMENTS.contains(ConfigData.ENVIRONMENT)) {
            this.options.addArguments("--no-sandbox");
        }

        if (ConfigData.HEADLESS) {
            this.options.addArguments("--headless");
        }

        WebDriverManager.chromedriver().setup();

        this.driver = new ChromeDriver(this.options);
        this.wait = new WebDriverWait(this.driver, ConfigData.WEB_DRIVER_WAIT_TIMEOUT);

        this.driver.get(ConfigData.PORTAL_APPLICATION_URL);
        this.driver.manage().window().maximize();
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public WebDriverWait getWait() {
        return this.wait;
    }
}
