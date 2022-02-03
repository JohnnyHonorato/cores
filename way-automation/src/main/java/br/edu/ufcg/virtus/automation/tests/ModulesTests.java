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
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.ModulesData;
import br.edu.ufcg.virtus.automation.page.ModulesPage;
import br.edu.ufcg.virtus.automation.pagePO.CommonPagePO;
import br.edu.ufcg.virtus.automation.pagePO.LoginPagePO;
import br.edu.ufcg.virtus.automation.pagePO.ModulesPagePO;

public class ModulesTests {

	private ModulesPage modulesPage;
	private ModulesPagePO modulesPagePO;
	private SeleniumConfig seleniumConfig;
	private LoginPagePO loginPagePO;
	private CommonPagePO commonPagePO;

    @Parameters({ "env", "headless" })
    @BeforeClass(alwaysRun = true)
    public void setupBeforeTest(@Optional("local") final String env, @Optional("false") final String headless)
            throws SQLException, IOException {

        // Setup environment according parameter
        EnvironmentSetup.setup(env, headless);

		ModuleDatabase.clearModuleDataOnDatabase();
		// Inject automation dataset for modules on database
		ModuleDatabase.injectModuleDataOnDatabase();

		// Initializing selenium config and page objects classes
		this.seleniumConfig = new SeleniumConfig();
		this.modulesPagePO = new ModulesPagePO(this.seleniumConfig);
		this.modulesPage = this.modulesPagePO.modulesPage;
		this.commonPagePO = new CommonPagePO(this.seleniumConfig);
		this.loginPagePO = new LoginPagePO(this.seleniumConfig);
		this.loginPagePO.loginSuccessfully();
	}

	@AfterClass(alwaysRun = true)
	public void setupAfterTest() throws SQLException {

		ModuleDatabase.clearModuleDataOnDatabase();
		ModuleDatabase.closeModuleDatabaseConnection();

		// Quit selenium driver
		this.seleniumConfig.getDriver().quit();
	}

	// AddModulesWithValidData_25490_A1
	@Test(priority = 0)
	public void addValidModule() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillFormForValidModule(ModulesData.VALID_MODULE_NAME1, ModulesData.VALID_LINK1,
				ModulesData.VALID_PATH1);

		Assert.assertTrue(this.commonPagePO.isSaveButtonEnabled());
		this.commonPagePO.clickOnSaveButton();
		Assert.assertTrue(this.modulesPagePO.isModuleDisplayedOnTable(ModulesData.VALID_MODULE_NAME1));
	}

	// AddModulesWithInvalidData_25516_A1
	@Test(priority = 1)
	public void addModuleWithBlankName() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillModulesNameField(ModulesData.BLANK_INPUT);
		this.modulesPagePO.fillModulesLinkField(ModulesData.VALID_LINK2);
		this.modulesPagePO.fillModulesIconField(ModulesData.VALID_ICON);
		this.modulesPagePO.selectOpenMode(ModulesData.OPEN_MODE_TYPE_SPA);
		this.modulesPagePO.fillPathField(ModulesData.VALID_PATH2);
		this.modulesPagePO.clickOnPublicModeToogle();
		this.modulesPagePO.clickOnAddPermissionsButton();
		this.modulesPagePO.fillPermissionNameField(ModulesData.VALID_PERMISSION_NAME);
		this.modulesPagePO.fillPermissionLabelField(ModulesData.VALID_PERMISSION_LABEL);
		this.modulesPagePO.fillPermissionDescriptionField(ModulesData.VALID_PERMISSION_DESCRIPTION);

		Assert.assertFalse(this.commonPagePO.isSaveButtonEnabled());
		Assert.assertTrue(this.modulesPagePO.isEmptyNameLabelDisplayed());
	}

	// AddModulesWithInvalidData_25516_A2
	@Test(priority = 2)
	public void addModuleWithInvalidLink() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillFormForValidModule(ModulesData.VALID_MODULE_NAME3, ModulesData.INVALID_LINK,
				ModulesData.VALID_PATH3);

		Assert.assertFalse(this.commonPagePO.isSaveButtonEnabled());
		Assert.assertTrue(this.modulesPagePO.isInvalidLinkLabelDisplayed());
	}

	// AddModulesWithInvalidData_25516_A3
	@Test(priority = 3)
	public void addModuleWithInvalidIcon() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillModulesNameField(ModulesData.VALID_MODULE_NAME4);
		this.modulesPagePO.fillModulesLinkField(ModulesData.VALID_LINK4);
		this.modulesPagePO.fillModulesIconField(ModulesData.INVALID_ICON);
		this.modulesPagePO.selectOpenMode(ModulesData.OPEN_MODE_TYPE_SPA);
		this.modulesPagePO.fillPathField(ModulesData.VALID_PATH4);
		this.modulesPagePO.clickOnPublicModeToogle();
		this.modulesPagePO.clickOnAddPermissionsButton();
		this.modulesPagePO.fillPermissionNameField(ModulesData.VALID_PERMISSION_NAME);
		this.modulesPagePO.fillPermissionLabelField(ModulesData.VALID_PERMISSION_LABEL);
		this.modulesPagePO.fillPermissionDescriptionField(ModulesData.VALID_PERMISSION_DESCRIPTION);

		Assert.assertFalse(this.commonPagePO.isSaveButtonEnabled());
		Assert.assertTrue(this.modulesPagePO.isInvalidIconLabelDisplayed());
	}

	// AddModulesWithInvalidData_25516_A4
	@Test(priority = 4)
	public void addModuleWithBlankLink() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillModulesNameField(ModulesData.VALID_MODULE_NAME5);
		this.modulesPagePO.fillModulesLinkField(ModulesData.BLANK_INPUT);
		this.modulesPagePO.fillModulesIconField(ModulesData.VALID_ICON);
		this.modulesPagePO.selectOpenMode(ModulesData.OPEN_MODE_TYPE_SPA);
		this.modulesPagePO.fillPathField(ModulesData.VALID_PATH5);
		this.modulesPagePO.clickOnPublicModeToogle();
		this.modulesPagePO.clickOnAddPermissionsButton();
		this.modulesPagePO.fillPermissionNameField(ModulesData.VALID_PERMISSION_NAME);
		this.modulesPagePO.fillPermissionLabelField(ModulesData.VALID_PERMISSION_LABEL);
		this.modulesPagePO.fillPermissionDescriptionField(ModulesData.VALID_PERMISSION_DESCRIPTION);

		Assert.assertFalse(this.commonPagePO.isSaveButtonEnabled());
		Assert.assertTrue(this.modulesPagePO.isInvalidLinkLabelDisplayed());
	}

	// AddModulesWithInvalidData_25516_A5
	@Test(priority = 5)
	public void addModuleWithBlankIcon() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillModulesNameField(ModulesData.VALID_MODULE_NAME6);
		this.modulesPagePO.fillModulesLinkField(ModulesData.VALID_LINK6);
		this.modulesPagePO.fillModulesIconField(ModulesData.BLANK_INPUT);
		this.modulesPagePO.selectOpenMode(ModulesData.OPEN_MODE_TYPE_SPA);
		this.modulesPagePO.fillPathField(ModulesData.VALID_PATH6);
		this.modulesPagePO.clickOnPublicModeToogle();
		this.modulesPagePO.clickOnAddPermissionsButton();
		this.modulesPagePO.fillPermissionNameField(ModulesData.VALID_PERMISSION_NAME);
		this.modulesPagePO.fillPermissionLabelField(ModulesData.VALID_PERMISSION_LABEL);
		this.modulesPagePO.fillPermissionDescriptionField(ModulesData.VALID_PERMISSION_DESCRIPTION);

		Assert.assertFalse(this.commonPagePO.isSaveButtonEnabled());
		Assert.assertTrue(this.modulesPagePO.isInvalidIconLabelDisplayed());
	}

	// SearchForAModule_25481_A1
	@Test(priority = 6)
	public void searchForValidModule() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.fillSearchField(ModulesData.VALID_MODULE_NAME7);

		Assert.assertTrue(this.modulesPagePO.isModuleDisplayedOnTable(ModulesData.VALID_MODULE_NAME7));
		Assert.assertFalse(this.modulesPagePO.isNoModuleFoundLabelDisplayed());
	}

	// SearchForAModule_25481_A2
	@Test(priority = 7)
	public void searchForInvalidModule() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.fillSearchField(ModulesData.INVALID_MODULE_NAME);

		Assert.assertFalse(this.modulesPagePO.isModuleDisplayedOnTable(ModulesData.INVALID_MODULE_NAME));
		Assert.assertTrue(this.modulesPagePO.isNoModuleFoundLabelDisplayed());
	}

	// RemoveModule_25567_A1
	@Test(priority = 8)
	public void removeModuleSuccessfully() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.removeModule(ModulesData.VALID_MODULE_NAME8);

		Assert.assertTrue(this.modulesPagePO.isNoModuleFoundLabelDisplayed());
		Assert.assertFalse(this.modulesPagePO.isModuleDisplayedOnTable(ModulesData.VALID_MODULE_NAME8));
	}

	// CancelDeletion_35408_A1
	@Test(priority = 9)
	public void cancelModulesDeletion() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.fillSearchField(ModulesData.VALID_MODULE_NAME9);
		this.commonPagePO.clickOnDeleteButton();
		this.modulesPagePO.clickOnNoDeleteModal();

		Assert.assertFalse(this.modulesPagePO.isNoModuleFoundLabelDisplayed());
		Assert.assertTrue(this.modulesPagePO.isModuleDisplayedOnTable(ModulesData.VALID_MODULE_NAME9));
	}

	// EditModuleWithValidData_25568_A1
	@Test(priority = 10)
	public void editModuleWithValidData() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.fillSearchField(ModulesData.VALID_MODULE_NAME10);
		this.commonPagePO.clickOnEditButton();
		this.modulesPagePO.cleanForm();
		this.modulesPagePO.fillModulesNameField(ModulesData.VALID_MODULE_NAME11);
		this.modulesPagePO.fillModulesLinkField(ModulesData.VALID_LINK11);
		this.modulesPagePO.fillPathField(ModulesData.VALID_PATH11);
		this.modulesPagePO.fillPermissionNameField(ModulesData.VALID_PERMISSION_NAME);
		this.modulesPagePO.fillPermissionLabelField(ModulesData.VALID_PERMISSION_LABEL);
		this.modulesPagePO.fillPermissionDescriptionField(ModulesData.VALID_PERMISSION_DESCRIPTION);

		Assert.assertTrue(this.commonPagePO.isSaveButtonEnabled());
		this.commonPagePO.clickOnSaveButton();
		Assert.assertTrue(this.modulesPagePO.isModuleDisplayedOnTable(ModulesData.VALID_MODULE_NAME11));
		this.modulesPagePO.fillSearchField(ModulesData.VALID_MODULE_NAME11);
		this.commonPagePO.clickOnEditButton();
		Assert.assertEquals(this.modulesPagePO.getModuleNameFieldValue(), ModulesData.VALID_MODULE_NAME11);
		Assert.assertEquals(this.modulesPagePO.getModuleLinkFieldValue(), ModulesData.VALID_LINK11);
		Assert.assertEquals(this.modulesPagePO.getModuleModeSPAPathValue(), ModulesData.VALID_PATH11);

	}

	// EditModuleWithInvalidData_25569_A1
	@Test(priority = 11)
	public void editModuleWithInvalidData() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.fillSearchField(ModulesData.VALID_MODULE_NAME12);
		this.commonPagePO.clickOnEditButton();
		this.modulesPagePO.cleanForm();
		this.modulesPagePO.fillModulesNameField(ModulesData.INVALID_MODULE_NAME);
		this.modulesPagePO.fillModulesLinkField(ModulesData.INVALID_LINK);
		this.modulesPagePO.fillPathField(ModulesData.INVALID_PATH);

		Assert.assertFalse(this.commonPagePO.isSaveButtonEnabled());
		this.commonPagePO.clickOnBackButton();
		Assert.assertFalse(this.modulesPagePO.isModuleDisplayedOnTable(ModulesData.INVALID_MODULE_NAME));
	}

	// AddModulesWithBlankPermission_25535_A1
	@Test(priority = 12)
	public void addModuleWithBlankPermissions() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillModulesNameField(ModulesData.VALID_MODULE_NAME13);
		this.modulesPagePO.fillModulesLinkField(ModulesData.VALID_LINK13);
		this.modulesPagePO.fillModulesIconField(ModulesData.VALID_ICON);
		this.modulesPagePO.selectOpenMode(ModulesData.OPEN_MODE_TYPE_SPA);
		this.modulesPagePO.fillPathField(ModulesData.VALID_PATH13);
		this.modulesPagePO.clickOnPublicModeToogle();
		this.modulesPagePO.clickOnAddPermissionsButton();
		this.modulesPagePO.fillPermissionNameField(ModulesData.BLANK_INPUT);
		this.modulesPagePO.fillPermissionLabelField(ModulesData.BLANK_INPUT);
		this.modulesPagePO.fillPermissionDescriptionField(ModulesData.BLANK_INPUT);

		Assert.assertFalse(this.commonPagePO.isSaveButtonEnabled());
		this.commonPagePO.clickOnBackButton();
		Assert.assertFalse(this.modulesPagePO.isModuleDisplayedOnTable(ModulesData.VALID_MODULE_NAME13));
	}

	// EditPermissionWithValidData_25570_A1
	@Test(priority = 13)
	public void editPermisssionsWithValidData() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.fillSearchField(ModulesData.VALID_MODULE_NAME14);
		this.commonPagePO.clickOnEditButton();
		this.modulesPagePO.cleanPermissions();
		this.modulesPagePO.fillPermissionNameField(ModulesData.VALID_PERMISSION_NAME2);
		this.modulesPagePO.fillPermissionLabelField(ModulesData.VALID_PERMISSION_LABEL2);
		this.modulesPagePO.fillPermissionDescriptionField(ModulesData.VALID_PERMISSION_DESCRIPTION2);

		Assert.assertTrue(this.commonPagePO.isSaveButtonEnabled());
		this.commonPagePO.clickOnSaveButton();
		Assert.assertTrue(this.modulesPagePO.isModuleDisplayedOnTable(ModulesData.VALID_MODULE_NAME14));
		this.modulesPagePO.fillSearchField(ModulesData.VALID_MODULE_NAME14);
		this.commonPagePO.clickOnEditButton();
		Assert.assertEquals(this.modulesPage.getPermissionsNameField().getAttribute("value"),
				ModulesData.VALID_PERMISSION_NAME2);
		Assert.assertEquals(this.modulesPage.getPermissionsLabelField().getAttribute("value"),
				ModulesData.VALID_PERMISSION_LABEL2);
		Assert.assertEquals(this.modulesPage.getPermissionsDescriptionField().getAttribute("value"),
				ModulesData.VALID_PERMISSION_DESCRIPTION2);
	}

	// EditPermissionWithBlankData_25571_A1
	@Test(priority = 14)
	public void editPermisssionsWithBlankData() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.fillSearchField(ModulesData.VALID_MODULE_NAME15);
		this.commonPagePO.clickOnEditButton();
		this.modulesPagePO.cleanPermissions();
		this.modulesPagePO.fillPermissionNameField(ModulesData.BLANK_INPUT);
		this.modulesPagePO.fillPermissionLabelField(ModulesData.BLANK_INPUT);
		this.modulesPagePO.fillPermissionDescriptionField(ModulesData.BLANK_INPUT);

		Assert.assertFalse(this.commonPagePO.isSaveButtonEnabled());
		this.commonPagePO.clickOnBackButton();
		this.modulesPagePO.fillSearchField(ModulesData.VALID_MODULE_NAME15);
		this.commonPagePO.clickOnEditButton();
		Assert.assertEquals(this.modulesPagePO.getPermissionsNameFieldValue(), ModulesData.VALID_PERMISSION_NAME);
		Assert.assertEquals(this.modulesPagePO.getPermissionsLabelFieldValue(), ModulesData.VALID_PERMISSION_LABEL);
		Assert.assertEquals(this.modulesPagePO.getPermissionsDescriptionFieldValue(),
				ModulesData.VALID_PERMISSION_DESCRIPTION);
	}

	// RemovePermission_25572_A1
	@Test(priority = 15)
	public void removePermission() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.fillSearchField(ModulesData.VALID_MODULE_NAME16);
		this.commonPagePO.clickOnEditButton();
		this.modulesPagePO.clickOnPermissionDeleteButton();

		Assert.assertEquals(this.commonPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_REMOVAL);
		Assert.assertFalse(this.modulesPagePO.doesModuleHavePermissions());
	}

	// CharacterLimitOfFields_36204_A1
	@Test(priority = 16)
	public void validNameSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillModulesNameField(ModulesData.VALID_255_SIZE);

		Assert.assertEquals(this.modulesPagePO.getModuleNameFieldValue(), ModulesData.VALID_255_SIZE);
		Assert.assertFalse(this.modulesPagePO.isModuleInvalidNameLabelDisplayed());
	}

	// CharacterLimitOfFields_36204_A2
	@Test(priority = 17)
	public void invalidNameSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillModulesNameField(CommonData.FIELD_255 + Keys.TAB);

		Assert.assertEquals(this.modulesPagePO.getModuleNameFieldValue(), CommonData.FIELD_255);
		Assert.assertEquals(this.modulesPage.getInvalidNameLabel().getText(), CommonData.ERROR_MSG_BIGGER_THAN_255);
	}

	// CharacterLimitOfFields_36204_A3
	@Test(priority = 18)
	public void validLinkSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillModulesLinkField(ModulesData.VALID_255_SIZE + Keys.TAB);

		Assert.assertEquals(this.modulesPagePO.getModuleLinkFieldValue(), ModulesData.VALID_255_SIZE);
		Assert.assertFalse(this.modulesPagePO.isModuleInvalidLinkLabelDisplayed());
	}

	// CharacterLimitOfFields_36204_A4
	@Test(priority = 19)
	public void invalidLinkSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillModulesLinkField(CommonData.FIELD_255 + Keys.TAB);

		Assert.assertEquals(this.modulesPagePO.getModuleLinkFieldValue(), CommonData.FIELD_255);
		Assert.assertEquals(this.modulesPage.getInvalidLinkLabel().getText(), CommonData.ERROR_MSG_BIGGER_THAN_255);
	}

	// CharacterLimitOfFields_36204_A5
	@Test(priority = 20)
	public void validIconSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillModulesIconField(ModulesData.VALID_255_SIZE + Keys.TAB);

		Assert.assertEquals(this.modulesPagePO.getModuleIconFieldValue(), ModulesData.VALID_255_SIZE);
		Assert.assertFalse(this.modulesPagePO.isModuleInvalidIconLabelDisplayed());
	}

	// CharacterLimitOfFields_36204_A6
	@Test(priority = 21)
	public void invalidIconSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.fillModulesIconField(CommonData.SPACE_AFTER_CHARS + Keys.TAB);

		Assert.assertEquals(this.modulesPagePO.getModuleIconFieldValue(), CommonData.SPACE_AFTER_CHARS);
		Assert.assertEquals(this.modulesPage.getInvalidIconLabel().getText(),
				CommonData.INVALID_CARACTER_MESSAGE_ERROR);
	}

	// CharacterLimitOfFields_36204_A7
	@Test(priority = 22)
	public void validPathSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.selectOpenMode(ModulesData.OPEN_MODE_TYPE_SPA);
		this.modulesPagePO.fillPathField(ModulesData.VALID_255_SIZE + Keys.TAB);

		Assert.assertEquals(this.modulesPagePO.getModuleModeSPAPathValue(), ModulesData.VALID_255_SIZE);
		Assert.assertFalse(this.modulesPagePO.isModuleInvalidPathLabelDisplayed());
	}

	// CharacterLimitOfFields_36204_A8
	@Test(priority = 23)
	public void invalidPathSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.selectOpenMode(ModulesData.OPEN_MODE_TYPE_SPA);
		this.modulesPagePO.fillPathField(CommonData.SPACE_AFTER_CHARS + Keys.TAB);

		Assert.assertEquals(this.modulesPagePO.getModuleModeSPAPathValue(), CommonData.SPACE_AFTER_CHARS);
		Assert.assertEquals(this.modulesPage.getInvalidPathLabel().getText(),
				CommonData.INVALID_CARACTER_MESSAGE_ERROR);
	}

	// CharacterLimitOfFields_36204_A9
	@Test(priority = 24)
	public void validPermissionNameSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.clickOnAddPermissionsButton();
		this.modulesPagePO.fillPermissionNameField(ModulesData.VALID_255_SIZE + Keys.TAB);

		Assert.assertEquals(this.modulesPagePO.getPermissionsNameFieldValue(), ModulesData.VALID_255_SIZE);
		Assert.assertFalse(this.modulesPagePO.isModuleInvalidPermissionNameLabelDisplayed());
	}

	// CharacterLimitOfFields_36204_A10
	@Test(priority = 25)
	public void invalidPermissionNameSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.clickOnAddPermissionsButton();
		this.modulesPagePO.fillPermissionNameField(CommonData.FIELD_255 + Keys.TAB);

		Assert.assertEquals(this.modulesPagePO.getPermissionsNameFieldValue(), CommonData.FIELD_255);
		Assert.assertEquals(this.modulesPage.getInvalidPermissionNameLabel().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
	}

	// CharacterLimitOfFields_36204_A11
	@Test(priority = 26)
	public void validPermissionLabelSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.clickOnAddPermissionsButton();
		this.modulesPagePO.fillPermissionLabelField(ModulesData.VALID_255_SIZE + Keys.TAB);

		Assert.assertEquals(this.modulesPagePO.getPermissionsLabelFieldValue(), ModulesData.VALID_255_SIZE);
		Assert.assertFalse(this.modulesPagePO.isModuleInvalidPermissionLabelLabelDisplayed());
	}

	// CharacterLimitOfFields_36204_A12
	@Test(priority = 27)
	public void invalidPermissionLabelSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.clickOnAddPermissionsButton();
		this.modulesPagePO.fillPermissionLabelField(CommonData.FIELD_255 + Keys.TAB);

		Assert.assertEquals(this.modulesPagePO.getPermissionsLabelFieldValue(), CommonData.FIELD_255);
		Assert.assertEquals(this.modulesPage.getInvalidPermissionLabelLabel().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
	}

	// CharacterLimitOfFields_36204_A13
	@Test(priority = 28)
	public void validPermissionDescriptionSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.clickOnAddPermissionsButton();
		this.modulesPagePO.fillPermissionDescriptionField(ModulesData.VALID_255_SIZE + Keys.TAB);

		Assert.assertEquals(this.modulesPagePO.getPermissionsDescriptionFieldValue(), ModulesData.VALID_255_SIZE);
		Assert.assertFalse(this.modulesPagePO.isModuleInvalidPermissionDescriptionLabelDisplayed());
	}

	// CharacterLimitOfFields_36204_A14
	@Test(priority = 29)
	public void invalidPermissionDescriptionSize() {
		this.commonPagePO.goToModulePage();
		this.modulesPagePO.clickOnAddModulesButton();
		this.modulesPagePO.clickOnAddPermissionsButton();
		this.modulesPagePO.fillPermissionDescriptionField(CommonData.FIELD_255 + Keys.TAB);
		this.modulesPagePO.fillModulesNameField(ModulesData.BLANK_INPUT);

		Assert.assertEquals(this.modulesPagePO.getPermissionsDescriptionFieldValue(), CommonData.FIELD_255);
		Assert.assertEquals(this.modulesPage.getInvalidPermissionDescriptionLabel().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
	}
}
