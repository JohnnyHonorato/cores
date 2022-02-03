package br.edu.ufcg.virtus.automation.pagePO;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.dataset.LoginData;
import br.edu.ufcg.virtus.automation.page.LoginPage;

public class LoginPagePO {
	
	public LoginPage loginPage;
	
	public LoginPagePO(SeleniumConfig seleniumConfig) {
		this.loginPage = new LoginPage(seleniumConfig);
	}

	public void fillUserNameField(String userName) {
		this.loginPage.getUserNameField().sendKeys(userName);
	}

	public void fillPasswordField(String password) {
		this.loginPage.getPasswordField().sendKeys(password);
	}

	public void clickOnLoginButton() {
		this.loginPage.getLoginButton().click();
	}

	public String getWelcomeMessage() {
		return this.loginPage.getWelcomeLabel().getText();
	}

	public String getAuthenticationFailedMessage() {
		return this.loginPage.getAuthenticationFailedLabel().getText();
	}
	
	public void loginSuccessfully() {
		this.fillUserNameField(LoginData.VALID_USERNAME);
		this.fillPasswordField(LoginData.VALID_PASSWORD);
		this.clickOnLoginButton();
		this.loginPage.driver.navigate().refresh();
	}
	
	public String getUserBlankFieldMessage() {
		return this.loginPage.getUserNameField().getAttribute("validationMessage");
	}
	
	public Boolean isUserFieldRequired() {
		return Boolean.parseBoolean(this.loginPage.getUserNameField().getAttribute("required"));
	}

	public String getPasswordBlankFieldMessage() {
		return this.loginPage.getPasswordField().getAttribute("validationMessage");
	}

	public boolean isPasswordFieldRequired() {
		return Boolean.parseBoolean(this.loginPage.getPasswordField().getAttribute("required"));
	}
}
