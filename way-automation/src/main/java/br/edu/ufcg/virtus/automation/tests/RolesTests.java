package br.edu.ufcg.virtus.automation.tests;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import br.edu.ufcg.virtus.automation.config.EnvironmentSetup;
import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.database.ModuleDatabase;
import br.edu.ufcg.virtus.automation.database.RolesDatabase;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.RolesData;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.page.RolesPage;
import br.edu.ufcg.virtus.automation.pagePO.CommonPagePO;
import br.edu.ufcg.virtus.automation.pagePO.LoginPagePO;
import br.edu.ufcg.virtus.automation.pagePO.RolesPagePO;

public class RolesTests {

	private RolesPagePO rolesPagePO;
	private CommonPagePO commonPagePO;
	private RolesPage rolesPage;
	private CommonPage commonPage;
	private SeleniumConfig seleniumConfig;
	private LoginPagePO loginPagePO;

    @Parameters({ "env", "headless" })
    @BeforeClass(alwaysRun = true)
    public void setupBeforeTest(@Optional("local") final String env, @Optional("false") final String headless)
            throws SQLException, IOException {

        // Setup environment according parameter
        EnvironmentSetup.setup(env, headless);

		// Clear database
		ModuleDatabase.clearModuleDataOnDatabase();
		RolesDatabase.clearRolesDataOnDatabase();

		// Inject automation dataset on database
		RolesDatabase.injectModuleDataOnDatabase();
		RolesDatabase.injectRolesDataOnDatabase();

		this.seleniumConfig = new SeleniumConfig();
		this.loginPagePO = new LoginPagePO(this.seleniumConfig);
		this.commonPagePO = new CommonPagePO(this.seleniumConfig);
		this.rolesPagePO = new RolesPagePO(this.seleniumConfig, this.commonPagePO.commonPage);
		this.rolesPage = this.rolesPagePO.rolesPage;
		this.commonPage = this.commonPagePO.commonPage;
		this.loginPagePO = new LoginPagePO(this.seleniumConfig);

		this.loginPagePO.loginSuccessfully();

	}

	@AfterClass(alwaysRun = true)
	public void setupAfterClass() throws SQLException {

		// Clear database
		ModuleDatabase.clearModuleDataOnDatabase();
		RolesDatabase.clearRolesDataOnDatabase();

		// Close organization database connection
		RolesDatabase.closeRolesDatabaseConnection();

		this.seleniumConfig.getDriver().quit();
	}

	// Create a Role with Valid Data - 25592_A1
	@Test(priority = 1)
	public void CreateARoleWithValidData() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.clickOnAddButton();
		this.rolesPagePO.fillRoleNameField(RolesData.VALID_ROLE_NAME_1);
		Assert.assertTrue(this.commonPage.getSaveButton().isEnabled());
		this.commonPagePO.clickOnSaveButton();
		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_ADDED);
		Assert.assertTrue(this.rolesPagePO.isRoleDisplayedOnTable(RolesData.VALID_ROLE_NAME_1));

	}

	// Create a Role with Invalid Data - 25593_A1
	@Test(priority = 2)
	public void CreateARoleWithInvalidName() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.clickOnAddButton();
		this.rolesPagePO.fillRoleNameField(RolesData.INVALID_ROLE_NAME + Keys.TAB);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.rolesPage.getRequiredNameLabel().getText(), CommonData.INVALID_FIELD_MESSAGE_ERROR);

	}

	// Create a Role with Invalid Data - 25593_A2
	@Test(priority = 3)
	public void CreateARoleWithExistingName() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.clickOnAddButton();
		this.rolesPagePO.fillRoleNameField(RolesData.DUPLICATED_ROLE_NAME);
		Assert.assertTrue(this.commonPage.getSaveButton().isEnabled());
		this.commonPagePO.clickOnSaveButton();

		Assert.assertEquals(this.commonPage.getToast().getText(), RolesData.MSG_DUPLICATED_ROLE_ERROR);
		Assert.assertTrue(this.commonPage.getSaveButton().isDisplayed());

	}

	// Create a Role with Invalid Data - 25593_A3
	@Test(priority = 4)
	public void CreateARoleWithBlankedName() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.clickOnAddButton();
		this.rolesPagePO.fillRoleNameField(CommonData.EMPTY_VALUE + Keys.TAB);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.rolesPage.getRequiredNameLabel().getText(), CommonData.MANDATORY_FIELD_MESSAGE_ERROR);

	}

	// Create a Role with Invalid Data - 25593_A4
	@Test(priority = 5)
	public void CreateARoleWithBiggerThanAllowedName() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.clickOnAddButton();
		this.rolesPagePO.fillRoleNameField(RolesData.ROLE_NAME_BIGGER + Keys.TAB);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.rolesPage.getRequiredNameLabel().getText(), CommonData.ERROR_MSG_BIGGER_THAN_255);

	}

	// Verify if registered permissions are shown at roles screen - 25588_A1
	@Test(priority = 6)
	public void VerifyRegisteredPermissionsOnRoleScreen() {
		this.commonPagePO.goToRolesPage();
		Assert.assertTrue(this.rolesPagePO.isModuleNameDisplayedOnTable(RolesData.MODULE_NAME));
		Assert.assertTrue(this.rolesPagePO.isPermissionNameDisplayedOnTable(RolesData.LABEL));

	}

	// Assign permission to a role - 25590_A1
	@Test(priority = 7)
	public void AssignPermissionToARole() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.selectARoleFromTable(RolesData.VALID_ROLE_NAME_2);
		this.rolesPagePO.selectAPermissionFromTable(RolesData.LABEL, 0, 0);
		this.rolesPage.getUpdateButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		Assert.assertTrue(this.rolesPage.getAllPermissionsCheckbox().get(0).isSelected());

	}

	// Edit an existing role with valid data - 25594_A1
	@Test(priority = 8)
	public void EditARoleWithValidData() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.fillSearchRoleField(RolesData.VALID_ROLE_NAME_3);
		this.commonPage.getEditButton().click();
		this.rolesPagePO.fillRoleNameField(RolesData.EDITED_ROLE_NAME);
		this.commonPagePO.clickOnSaveButton();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		this.rolesPage.driver.navigate().refresh();
		Assert.assertTrue(this.rolesPagePO.isRoleDisplayedOnTable(RolesData.EDITED_ROLE_NAME));

	}

	// Edit an existing role with invalid data - 25595_A1
	@Test(priority = 9)
	public void EditARoleWithInvalidName() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.fillSearchRoleField(RolesData.VALID_ROLE_NAME_4);
		this.commonPage.getEditButton().click();
		this.rolesPagePO.fillRoleNameField(RolesData.INVALID_ROLE_NAME + Keys.TAB);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.rolesPage.getRequiredNameLabel().getText(), CommonData.INVALID_FIELD_MESSAGE_ERROR);

	}

	// Edit an existing role with invalid data - 25595_A2
	@Test(priority = 10)
	public void EditARoleWithExistingName() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.fillSearchRoleField(RolesData.VALID_ROLE_NAME_5);
		this.commonPage.getEditButton().click();
		this.rolesPagePO.fillRoleNameField(RolesData.DUPLICATED_ROLE_NAME + Keys.TAB);
		this.commonPagePO.clickOnSaveButton();

		Assert.assertEquals(this.commonPage.getToast().getText(), RolesData.MSG_DUPLICATED_ROLE_ERROR);
		Assert.assertTrue(this.commonPage.getSaveButton().isDisplayed());

	}

	// Edit an existing role with invalid data - 25595_A3
	@Test(priority = 11)
	public void EditARoleWithBlankedName() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.fillSearchRoleField(RolesData.VALID_ROLE_NAME_6);
		this.commonPage.getEditButton().click();
		this.rolesPagePO.fillRoleNameField(RolesData.INVALID_ROLE_NAME + Keys.BACK_SPACE + Keys.TAB);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.rolesPage.getRequiredNameLabel().getText(), CommonData.MANDATORY_FIELD_MESSAGE_ERROR);

	}

	// Edit an existing role with invalid data - 25595_A4
	@Test(priority = 12)
	public void EditARoleWithBiggerThanAllowedName() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.fillSearchRoleField(RolesData.VALID_ROLE_NAME_7);
		this.commonPage.getEditButton().click();
		this.rolesPagePO.fillRoleNameField(RolesData.ROLE_NAME_BIGGER + Keys.TAB);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.rolesPage.getRequiredNameLabel().getText(), CommonData.ERROR_MSG_BIGGER_THAN_255);

	}

	// Remove an existing role - 25596_A1
	@Test(priority = 13)
	public void RemoveExistingRole() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.removeRoleFromTable(RolesData.VALID_ROLE_NAME_8);

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_REMOVAL);
		this.rolesPage.driver.navigate().refresh();
		Assert.assertFalse(this.rolesPagePO.isRoleDisplayedOnTable(RolesData.VALID_ROLE_NAME_8));

	}

	// Remove an existing role - 25596_A1
	@Test(priority = 14)
	public void CancelRemoveExistingRole() {
		this.commonPagePO.goToRolesPage();
		this.rolesPagePO.fillSearchRoleField(RolesData.VALID_ROLE_NAME_9);
		this.commonPage.getDeleteButton().click();
		this.rolesPage.getCancelRemovalButton().click();

		this.rolesPagePO.fillSearchRoleField(CommonData.EMPTY_VALUE + Keys.TAB);
		Assert.assertTrue(this.rolesPagePO.isRoleDisplayedOnTable(RolesData.VALID_ROLE_NAME_9));
		Assert.assertTrue(this.rolesPage.getRolesTable().isDisplayed());

	}

}
