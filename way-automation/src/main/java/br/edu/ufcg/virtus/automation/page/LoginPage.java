package br.edu.ufcg.virtus.automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;

public class LoginPage {

	public WebDriver driver;
	public SeleniumConfig seleniumConfig;
	
	private static final String USER_NAME_FIELD = "username";
	private static final String PASSWORD_FIELD = "password";
	private static final String LOGIN_BUTTON = "//*[@id=\"loginForm\"]/button";
	private static final String WELCOME_LABEL = "/html/body/app-root/div/div/div/app-main/div/div/div[1]/h3";
	private static final String AUTHENTICATION_FAILED_LABEL = "/html/body/div/div/div/div/div/div/div[2]/div/div";
	

	public LoginPage(SeleniumConfig seleniumConfig) {
		this.driver = seleniumConfig.getDriver();
		this.seleniumConfig = seleniumConfig;
	}

	public WebElement getUserNameField() {
		return this.driver.findElement(By.id(USER_NAME_FIELD));
	}

	public WebElement getPasswordField() {
		return this.driver.findElement(By.id(PASSWORD_FIELD));
	}

	public WebElement getLoginButton() {
		return this.driver.findElement(By.xpath(LOGIN_BUTTON));
	}

	public WebElement getWelcomeLabel() {
		return this.driver.findElement(By.xpath(WELCOME_LABEL));
	}

	public WebElement getAuthenticationFailedLabel() {
		return this.driver.findElement(By.xpath(AUTHENTICATION_FAILED_LABEL));
	}
}
