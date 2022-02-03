package br.edu.ufcg.virtus.automation.tests;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import br.edu.ufcg.virtus.automation.config.EnvironmentSetup;
import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.dataset.LoginData;
import br.edu.ufcg.virtus.automation.page.LoginPage;
import br.edu.ufcg.virtus.automation.pagePO.LoginPagePO;

public class LoginTests {

    private LoginPagePO loginPagePO;
    private LoginPage loginPage;
    private SeleniumConfig seleniumConfig;

    @Parameters({ "env", "headless" })
    @BeforeClass(alwaysRun = true)
    public void setupBeforeTest(@Optional("local") final String env, @Optional("false") final String headless)
            throws SQLException, IOException {

        // Setup environment according parameter
        EnvironmentSetup.setup(env, headless);

        this.seleniumConfig = new SeleniumConfig();
        this.loginPagePO = new LoginPagePO(this.seleniumConfig);
        this.loginPage = this.loginPagePO.loginPage;
    }

    @AfterMethod(alwaysRun = true)
    public void setupAfterTest() {
        this.seleniumConfig.getDriver().quit();
    }

    // SuccessfulLoginToTheSystem_25272_A1
    @Test
    public void successfulLoginToTheSystem() {
        this.loginPagePO.fillUserNameField(LoginData.VALID_USERNAME);
        this.loginPagePO.fillPasswordField(LoginData.VALID_PASSWORD);
        this.loginPagePO.clickOnLoginButton();
        this.loginPage.driver.navigate().refresh();
        final boolean isWelcomeLabelTextDisplayed = LoginData.VALID_WELCOME_MESSAGE.contains(this.loginPagePO.getWelcomeMessage());
        Assert.assertTrue(isWelcomeLabelTextDisplayed);
        Assert.assertTrue(this.loginPage.getWelcomeLabel().isDisplayed());
    }

    // DenyLoginWithInvalidCredentials_25276_A1
    @Test
    public void caseInvalidUsername() {
        this.loginPagePO.fillUserNameField(LoginData.INVALID_USERNAME);
        this.loginPagePO.fillPasswordField(LoginData.VALID_PASSWORD);
        this.loginPagePO.clickOnLoginButton();

        Assert.assertEquals(this.loginPagePO.getAuthenticationFailedMessage(), LoginData.AUTHENTICATION_FAILED_MESSAGE);
        Assert.assertTrue(this.loginPage.getAuthenticationFailedLabel().isDisplayed());
    }

    // DenyLoginWithInvalidCredentials_25276_A2
    @Test
    public void caseInvalidPassword() {
        this.loginPagePO.fillUserNameField(LoginData.VALID_USERNAME);
        this.loginPagePO.fillPasswordField(LoginData.INVALID_PASSWORD);
        this.loginPagePO.clickOnLoginButton();

        Assert.assertEquals(this.loginPagePO.getAuthenticationFailedMessage(), LoginData.AUTHENTICATION_FAILED_MESSAGE);
        Assert.assertTrue(this.loginPage.getAuthenticationFailedLabel().isDisplayed());
    }

    // DenyLoginWithInvalidCredentials_25276_A3
    @Test
    public void caseBlankUsername() throws InterruptedException {
        this.loginPagePO.fillPasswordField(LoginData.VALID_PASSWORD);
        this.loginPagePO.clickOnLoginButton();

        Assert.assertEquals(this.loginPagePO.getUserBlankFieldMessage(), LoginData.BLANK_MESSAGE);
        Assert.assertTrue(this.loginPagePO.isUserFieldRequired());
        Assert.assertTrue(this.loginPage.getLoginButton().isDisplayed());
    }

    // DenyLoginWithInvalidCredentials_25276_A4
    @Test
    public void caseBlankPassword() {
        this.loginPagePO.fillUserNameField(LoginData.VALID_USERNAME);
        this.loginPagePO.clickOnLoginButton();

        Assert.assertEquals(this.loginPagePO.getPasswordBlankFieldMessage(), LoginData.BLANK_MESSAGE);
        Assert.assertTrue(this.loginPagePO.isPasswordFieldRequired());
        Assert.assertTrue(this.loginPage.getLoginButton().isDisplayed());
    }

    // DenyLoginWithInvalidCredentials_25276_A5
    @Test
    public void caseEmptyFields() {

        this.loginPagePO.clickOnLoginButton();

        Assert.assertEquals(this.loginPagePO.getUserBlankFieldMessage(), LoginData.BLANK_MESSAGE);
        Assert.assertTrue(this.loginPagePO.isUserFieldRequired());
        Assert.assertEquals(this.loginPagePO.getPasswordBlankFieldMessage(), LoginData.BLANK_MESSAGE);
        Assert.assertTrue(this.loginPagePO.isPasswordFieldRequired());
        Assert.assertTrue(this.loginPage.getLoginButton().isDisplayed());
    }
}
