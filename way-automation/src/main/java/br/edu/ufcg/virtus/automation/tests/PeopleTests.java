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
import br.edu.ufcg.virtus.automation.database.PeopleDatabase;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.PeopleData;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.page.PeoplePage;
import br.edu.ufcg.virtus.automation.pagePO.CommonPagePO;
import br.edu.ufcg.virtus.automation.pagePO.LoginPagePO;
import br.edu.ufcg.virtus.automation.pagePO.PeoplePagePO;

public class PeopleTests {

	private PeoplePagePO peoplePagePO;
	private PeoplePage peoplePage;
	private SeleniumConfig seleniumConfig;
	private LoginPagePO loginPagePO;
	private CommonPage commonPage;
	private CommonPagePO commonPagePO;

    @Parameters({ "env", "headless" })
    @BeforeClass(alwaysRun = true)
    public void setupBeforeTest(@Optional("local") final String env, @Optional("false") final String headless)
            throws SQLException, IOException {

        // Setup environment according parameter
        EnvironmentSetup.setup(env, headless);

		PeopleDatabase.clearPeopleDataOnDatabase();
		// Inject automation dataset on database
		PeopleDatabase.injectPeopleDataOnDatabase();

		// Initializing selenium config and page objects classes
		this.seleniumConfig = new SeleniumConfig();
		this.loginPagePO = new LoginPagePO(this.seleniumConfig);
		this.commonPagePO = new CommonPagePO(this.seleniumConfig);
		this.commonPage = new CommonPage(this.seleniumConfig);
		this.peoplePagePO = new PeoplePagePO(this.seleniumConfig, this.commonPagePO.commonPage);
		this.peoplePage = this.peoplePagePO.peoplePage;

		this.loginPagePO.loginSuccessfully();
	}

	@AfterClass(alwaysRun = true)
	public void setupAfterTest() throws SQLException {

		PeopleDatabase.clearPeopleDataOnDatabase();
		// Close organization database connection
		PeopleDatabase.closePeopleDatabaseConnection();

		// Quit selenium driver
		this.seleniumConfig.getDriver().quit();
	}

	// AddUserWithValidData_25325_A1
	// IMPLEMENT VERIFICATION BY PAGINATION
	@Test(priority = 0)
	public void addValidPeople() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillPeopleNameField(PeopleData.VALID_PEOPLE_NAME1);

		this.peoplePagePO.fillEmailField(PeopleData.VALID_EMAIL);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);
		this.peoplePagePO.clickOnEmailAddButton();
		this.commonPagePO.clickOnSaveButton();

		Assert.assertTrue(this.peoplePagePO.isPeopleDisplayedOnTable(PeopleData.VALID_PEOPLE_NAME1));
		Assert.assertTrue(this.peoplePagePO.isMessageToastDisplayed());
		Assert.assertEquals(this.commonPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_ADDED);

	}

	// AddUserWithAllFieldsFilled_30575_A1
	@Test(priority = 1)
	public void addUserWithAllFields() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillPeopleNameField(PeopleData.VALID_PEOPLE_NAME2);
		this.peoplePagePO.fillHowToBeCalledField(PeopleData.VALID_HOW_TO_BE_CALLED_INPUT);
		this.peoplePagePO.fillCpfField(PeopleData.VALID_CPF);

		this.peoplePagePO.fillEmailField(PeopleData.VALID_EMAIL);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);
		this.peoplePagePO.clickOnEmailAddButton();
		this.peoplePagePO.clickOnPhoneTab();
		this.peoplePagePO.fillPhoneNumberField(PeopleData.VALID_PHONE_NUMBER);
		this.peoplePagePO.selectPhoneType(PeopleData.PHONE_TYPE_PRINCIPAL);
		this.peoplePagePO.clickOnAddPhoneButton();
		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.selectAdressType(PeopleData.ADRESS_TYPE_NATIONAL);
		this.peoplePagePO.fillAdressCEPField(PeopleData.ADRESS_CEP);
		this.peoplePagePO.fillAdressStreetField(PeopleData.ADRESS_STREET);
		this.peoplePagePO.fillAdressComplementField(PeopleData.ADRESS_COMPLEMENT);
		this.peoplePagePO.fillAdressNeighborhoodField(PeopleData.ADRESS_NEIGHBORHOOD);
		this.peoplePagePO.fillAdressCityField(PeopleData.ADRESS_CITY);
		this.peoplePagePO.fillAdressStateField(PeopleData.ADRESS_STATE);
		this.peoplePagePO.fillAdressCountryField(PeopleData.ADRESS_COUNTRY);
		this.peoplePagePO.clickOnAddAdressButton();
		this.commonPagePO.clickOnSaveButton();

		Assert.assertTrue(this.peoplePagePO.isPeopleDisplayedOnTable(PeopleData.VALID_PEOPLE_NAME2));
		Assert.assertTrue(this.peoplePagePO.isMessageToastDisplayed());
		Assert.assertEquals(this.commonPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_ADDED);
	}

	// AddUserWithInvalidData_25334_A1
	@Test(priority = 2)
	public void addPeopleWithInvalidEmail() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillPeopleNameField(PeopleData.VALID_PEOPLE_NAME3);

		this.peoplePagePO.fillEmailField(PeopleData.INVALID_EMAIL);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);

		Assert.assertFalse(this.peoplePage.getEmailAddButton().isEnabled());
		Assert.assertTrue(this.peoplePagePO.isInvalidEmailLabelDisplayed());

		this.peoplePagePO.clickOnSaveButton();
		Assert.assertTrue(this.commonPage.getToast().getText().equals(CommonData.INVALID_EMAIL_MISSING));
	}

	// AddUserWithInvalidData_25334_A2
	@Test(priority = 3)
	public void addPeopleWithBlankName() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillPeopleNameField(PeopleData.BLANK_INPUT);

		this.peoplePagePO.fillEmailField(PeopleData.VALID_EMAIL);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);

		Assert.assertTrue(this.peoplePage.getPeopleInvalidNameLabel().isDisplayed());
		Assert.assertFalse(this.peoplePage.getSaveButton().isEnabled());
	}

	// AddUserWithInvalidData_25334_A3
	@Test(priority = 4)
	public void addPeopleWithBlankEmail() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillPeopleNameField(PeopleData.VALID_PEOPLE_NAME4);

		this.peoplePagePO.fillEmailField(PeopleData.BLANK_INPUT);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);

		Assert.assertTrue(this.peoplePagePO.isInvalidEmailLabelDisplayed());
		Assert.assertFalse(this.peoplePage.getEmailAddButton().isEnabled());

		this.peoplePagePO.clickOnSaveButton();
		Assert.assertTrue(this.commonPage.getToast().getText().equals(CommonData.INVALID_EMAIL_MISSING));
	}

	// AddUserWithoutDefineEmailtype_25335_A1
	@Test(priority = 5)
	public void addPeopleWithoutEmailType() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillPeopleNameField(PeopleData.VALID_PEOPLE_NAME5);

		this.peoplePagePO.fillEmailField(PeopleData.VALID_EMAIL);

		Assert.assertFalse(this.peoplePage.getEmailAddButton().isEnabled());

		this.peoplePagePO.clickOnSaveButton();
		Assert.assertTrue(this.commonPage.getToast().getText().equals(CommonData.INVALID_EMAIL_MISSING));
	}

	// SearchForAnUser_25469_A1
	@Test(priority = 6)
	public void searchForValidPeople() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.fillSearchBarField(PeopleData.VALID_PEOPLE_NAME6);

		Assert.assertTrue(this.peoplePagePO.isPeopleDisplayedOnTable(PeopleData.VALID_PEOPLE_NAME6));
		Assert.assertFalse(this.peoplePagePO.isNoUserFoundLabelDisplayed());
	}

	// SearchForAnUser_25469_A2
	@Test(priority = 7)
	public void searchForInvalidPeople() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.fillSearchBarField(PeopleData.INVALID_PEOPLE_NAME);

		Assert.assertTrue(this.peoplePagePO.isNoUserFoundLabelDisplayed());
		Assert.assertFalse(this.peoplePagePO.isPeopleDisplayedOnTable(PeopleData.INVALID_PEOPLE_NAME));
	}

	// RemoveUser_25468_A1
	@Test(priority = 8)
	public void removePeople() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.fillSearchBarField(PeopleData.VALID_PEOPLE_NAME7);
		this.peoplePagePO.clickOnRemoveButton();
		this.peoplePagePO.clickOnYesRemoveModalButton();

		Assert.assertTrue(this.peoplePagePO.isNoUserFoundLabelDisplayed());
		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_REMOVAL);
	}

	// CancelDeletion_30500_A1
	@Test(priority = 9)
	public void cancelDeletion() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.fillSearchBarField(PeopleData.VALID_PEOPLE_NAME8);
		this.peoplePagePO.clickOnRemoveButton();
		this.peoplePagePO.clickOnNoRemoveModalButton();

		Assert.assertTrue(this.peoplePagePO.isPeopleDisplayedOnTable(PeopleData.VALID_PEOPLE_NAME8));
		Assert.assertTrue(this.commonPagePO.isDeleteButtonDisplayed());
	}

	// EditUserWithValidData_25455_A1
	@Test(priority = 10)
	public void editUserWithValidData() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.fillSearchBarField(PeopleData.VALID_PEOPLE_NAME9);
		this.peoplePagePO.clickOnEditButton();
		this.peoplePagePO.cleanForm();
		this.peoplePagePO.fillPeopleNameField(PeopleData.VALID_PEOPLE_NAME10);
		this.peoplePagePO.fillEmailField(PeopleData.VALID_EMAIL_TWO);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);
		this.commonPagePO.clickOnSaveButton();

		Assert.assertTrue(this.peoplePagePO.isPeopleDisplayedOnTable(PeopleData.VALID_PEOPLE_NAME10));
		Assert.assertTrue(this.peoplePagePO.isMessageToastDisplayed());
		Assert.assertEquals(this.commonPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_UPDATED);
	}

	// EditUserWithInvalidData_25456_A1
	@Test(priority = 11)
	public void editUserWithInvalidName() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.fillSearchBarField(PeopleData.VALID_PEOPLE_NAME11);
		this.peoplePagePO.clickOnEditButton();
		this.peoplePagePO.cleanForm();
		this.peoplePagePO.fillPeopleNameField(PeopleData.INVALID_PEOPLE_NAME);
		this.peoplePagePO.fillEmailField(PeopleData.VALID_EMAIL);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);
		this.peoplePagePO.clickOnEmailAddButton();

		Assert.assertTrue(this.peoplePagePO.isPeopleInvalidNameLabelDisplayed());
		Assert.assertFalse(this.peoplePagePO.isSaveButtonClickable());
	}

	// EditUserWithInvalidData_25456_A2
	@Test(priority = 12)
	public void editUserWithInvalidEmail() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.fillSearchBarField(PeopleData.VALID_PEOPLE_NAME12);
		this.peoplePagePO.clickOnEditButton();
		this.peoplePagePO.cleanForm();
		this.peoplePagePO.fillEmailField(PeopleData.INVALID_EMAIL);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);

		Assert.assertTrue(this.peoplePagePO.isInvalidEmailLabelDisplayed());
		Assert.assertFalse(this.peoplePagePO.isAddEmailButtonClickable());
	}

	// EditUserWithInvalidData_25456_A3
	@Test(priority = 13)
	public void editUserWithBlankName() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.fillSearchBarField(PeopleData.VALID_PEOPLE_NAME13);
		this.peoplePagePO.clickOnEditButton();
		this.peoplePagePO.cleanForm();
		this.peoplePagePO.fillEmailField(PeopleData.VALID_EMAIL);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);

		Assert.assertTrue(this.peoplePagePO.isPeopleInvalidNameLabelDisplayed());
		Assert.assertFalse(this.peoplePagePO.isSaveButtonClickable());
	}

	// EditUserWithInvalidData_25456_A4
	@Test(priority = 14)
	public void editUserWithBlankEmail() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.fillSearchBarField(PeopleData.VALID_PEOPLE_NAME14);
		this.peoplePagePO.clickOnEditButton();
		this.peoplePagePO.cleanForm();
		this.peoplePagePO.fillPeopleNameField(PeopleData.VALID_PEOPLE_NAME14);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);

		Assert.assertTrue(this.peoplePagePO.isInvalidEmailLabelDisplayed());
		Assert.assertFalse(this.peoplePagePO.isAddEmailButtonClickable());
	}

	// EditUserWithInvalidData_25456_A5
	@Test(priority = 15)
	public void editUserWithBlankFields() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.fillSearchBarField(PeopleData.VALID_PEOPLE_NAME15);
		this.peoplePagePO.clickOnEditButton();
		this.peoplePagePO.cleanForm();
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);

		Assert.assertTrue(this.peoplePagePO.isInvalidEmailLabelDisplayed());
		Assert.assertFalse(this.peoplePagePO.isAddEmailButtonClickable());
		Assert.assertFalse(this.peoplePagePO.isSaveButtonClickable());
	}

	// AddUserWithAccessToTheSystem_30679_A1
	@Test(priority = 16)
	public void addUserWithValidLogin() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillPeopleNameField(PeopleData.VALID_PEOPLE_NAME16);

		this.peoplePagePO.fillEmailField(PeopleData.VALID_EMAIL);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);
		this.peoplePagePO.clickOnEmailAddButton();
		this.peoplePagePO.clickOnAccessDataTab();
		this.peoplePagePO.clickOnUserAccessToogle();
		this.peoplePagePO.fillUserLoginField(PeopleData.VALID_USER_ACCESS_LOGIN);
		this.peoplePagePO.selectUserRole(PeopleData.USER_ROLE_COLLAB);
		this.commonPagePO.clickOnSaveButton();

		Assert.assertTrue(this.peoplePagePO.isPeopleDisplayedOnTable(PeopleData.VALID_PEOPLE_NAME16));
		Assert.assertTrue(this.peoplePagePO.isMessageToastDisplayed());
		Assert.assertEquals(this.commonPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_ADDED);
	}

	// AddUserWithAccessToTheSystem_30679_A2
	@Test(priority = 17)
	public void addUserWithBlankLogin() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillPeopleNameField(PeopleData.VALID_PEOPLE_NAME17);

		this.peoplePagePO.fillEmailField(PeopleData.VALID_EMAIL);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);
		this.peoplePagePO.clickOnEmailAddButton();
		this.peoplePagePO.clickOnAccessDataTab();
		this.peoplePagePO.clickOnUserAccessToogle();
		this.peoplePagePO.clearLoginField();
		this.peoplePagePO.selectUserRole(PeopleData.USER_ROLE_COLLAB);

		Assert.assertFalse(this.peoplePagePO.isSaveButtonClickable());
		Assert.assertTrue(this.peoplePagePO.isInvalidLoginLabelDisplayed());
	}

	/*
	 * // EditUserWithAccessOnTheSystem_30681_A1 // TO DO: INSERTION IS NOT WORKING
	 * CORRECTLY
	 * 
	 * @Test public void editUserWithValidLogin() {
	 * 
	 * }
	 * 
	 * // EditUserWithAccessOnTheSystem_30681_A2 // TO DO: INSERTION IS NOT WORKING
	 * CORRECTLY
	 * 
	 * @Test public void editUserWithBlankLogin() {
	 * 
	 * }
	 */

	// CharacterLimitOfFields_30714_A1
	@Test(priority = 18)
	public void validNameSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillPeopleNameField(PeopleData.VALID_255_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getPeopleNameFieldValue(), PeopleData.VALID_255_SIZE);
		Assert.assertFalse(this.peoplePagePO.isPeopleInvalidNameLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A2
	@Test(priority = 19)
	public void invalidNameSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillPeopleNameField(PeopleData.INVALID_256_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getPeopleInvalidNameLabel().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.peoplePagePO.isPeopleInvalidNameLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A3
	@Test(priority = 20)
	public void validHowToBeCalledFieldSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillHowToBeCalledField(PeopleData.VALID_50_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getHowToBeCalledFieldValue(), PeopleData.VALID_50_SIZE);
		Assert.assertFalse(this.peoplePagePO.isHowToBeCalledInvalidlabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A4
	@Test(priority = 21)
	public void invalidHowToBeCalledFieldSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillHowToBeCalledField(PeopleData.INVALID_51_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getHowTobeCalledLabel().getText(), PeopleData.INVALID_SIZE_50_MESSAGE);
		Assert.assertTrue(this.peoplePagePO.isHowToBeCalledInvalidlabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A5
	@Test(priority = 22)
	public void validCPFsize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillCpfField(PeopleData.VALID_CPF);

		Assert.assertEquals(this.peoplePagePO.getCpfFieldValue(), PeopleData.VALID_CPF);
		Assert.assertFalse(this.peoplePagePO.isCpfnvalidlabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A6
	@Test(priority = 23)
	public void invalidCPFsize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.fillCpfField(PeopleData.INVALID_CPF + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getCpfLabel().getText(), PeopleData.INVALID_CPF_MESSAGE);
		Assert.assertTrue(this.peoplePagePO.isCpfnvalidlabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A7
	@Test(priority = 24)
	public void validEmailSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.fillEmailField(PeopleData.VALID_100_EMAIL_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getEmailFieldValue(), PeopleData.VALID_100_EMAIL_SIZE);
		Assert.assertFalse(this.peoplePagePO.isInvalidEmailLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A8
	@Test(priority = 25)
	public void invalidEmailSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.fillEmailField(PeopleData.INVALID_101_EMAIL_SIZE);
		this.peoplePagePO.selectEmailType(PeopleData.EMAIL_TYPE_PRINCIPAL);

		Assert.assertEquals(this.peoplePage.getInvalidEmailLabel().getText(), PeopleData.INVALID_SIZE_100_MESSAGE);
		Assert.assertTrue(this.peoplePagePO.isInvalidEmailLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A9
	@Test(priority = 26)
	public void validCountryCodeSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnPhoneTab();
		this.peoplePagePO.fillPhoneCodeField(PeopleData.VALID_4_COUNTRY_CODE_SIZE);
		this.peoplePagePO.fillPhoneNumberField(PeopleData.VALID_PHONE_NUMBER + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getPhoneCodeValue(), PeopleData.VALID_4_COUNTRY_CODE_MESSAGE);
		Assert.assertFalse(this.peoplePagePO.isInvalidCountryCodeLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A10
	@Test(priority = 27)
	public void invalidCountryCodeSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnPhoneTab();
		this.peoplePagePO.fillPhoneCodeField(PeopleData.INVALID_5_COUNTRY_CODE_SIZE);
		this.peoplePagePO.fillPhoneNumberField(PeopleData.VALID_PHONE_NUMBER + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getCountryCodeLabel().getText(), PeopleData.INVALID_COUNTRY_CODE_MESSAGE);
		Assert.assertTrue(this.peoplePagePO.isInvalidCountryCodeLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A11
	@Test(priority = 28)
	public void validPhoneNumberSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnPhoneTab();
		this.peoplePagePO.fillPhoneNumberField(PeopleData.VALID_PHONE_NUMBER_20_SIZE_);
		this.peoplePagePO.fillPhoneCodeField(PeopleData.VALID_4_COUNTRY_CODE_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getPhoneNumberValue(), PeopleData.VALID_PHONE_NUMBER_20_SIZE_);
		Assert.assertFalse(this.peoplePagePO.isInvalidPhoneLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A12
	@Test(priority = 29)
	public void invalidPhoneNumberSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnPhoneTab();
		this.peoplePagePO.fillPhoneNumberField(PeopleData.INVALID_PHONE_NUMBER_21_SIZE_);
		this.peoplePagePO.fillPhoneCodeField(PeopleData.VALID_4_COUNTRY_CODE_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getPhoneNumberLabel().getText(), PeopleData.INVALID_SIZE_20_MESSAGE);
		Assert.assertTrue(this.peoplePagePO.isInvalidPhoneLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A13
	@Test(priority = 30)
	public void validCEPSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE);
		this.peoplePagePO.fillAdressStreetField(PeopleData.VALID_50_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getAdressCepValue(), PeopleData.VALID_CEP_NUMBER_8_MESSAGE);
		Assert.assertFalse(this.peoplePagePO.isInvalidCEPLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A14
	@Test(priority = 31)
	public void invalidCEPSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressCEPField(PeopleData.INVALID_CEP_NUMBER_7_SIZE);
		this.peoplePagePO.fillAdressStreetField(PeopleData.VALID_50_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getCepLabel().getText(), PeopleData.INVALID_CEP_MESSAGE);
		Assert.assertTrue(this.peoplePagePO.isInvalidCEPLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A15
	@Test(priority = 32)
	public void validStreetSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressStreetField(PeopleData.VALID_255_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getAdressStreetValue(), PeopleData.VALID_255_SIZE);
		Assert.assertFalse(this.peoplePagePO.isInvalidStreetLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A16
	@Test(priority = 33)
	public void invalidStreetSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressStreetField(PeopleData.INVALID_256_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getStreetLabel().getText(), CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.peoplePagePO.isInvalidStreetLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A17
	@Test(priority = 34)
	public void validNumberSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressNumberField(PeopleData.VALID_255_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getAdressNumberValue(), PeopleData.VALID_255_SIZE);
		Assert.assertFalse(this.peoplePagePO.isInvalidNumberLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A18
	@Test(priority = 35)
	public void invalidNumberSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressNumberField(PeopleData.INVALID_256_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getNumberLabel().getText(), CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.peoplePagePO.isInvalidNumberLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A19
	@Test(priority = 36)
	public void validComplementSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressComplementField(PeopleData.VALID_100_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getAdressComplementValue(), PeopleData.VALID_100_SIZE);
		Assert.assertFalse(this.peoplePagePO.isInvalidComplementLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A20
	@Test(priority = 37)
	public void invalidComplementSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressComplementField(PeopleData.INVALID_101_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getComplementLabel().getText(), PeopleData.INVALID_SIZE_100_MESSAGE);
		Assert.assertTrue(this.peoplePagePO.isInvalidComplementLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A21
	@Test(priority = 38)
	public void validNeighborhoodSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressNeighborhoodField(PeopleData.VALID_100_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getAdressNeighborhoodValue(), PeopleData.VALID_100_SIZE);
		Assert.assertFalse(this.peoplePagePO.isInvalidNeighborhoodLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A22
	@Test(priority = 39)
	public void invalidNeighborhoodSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressNeighborhoodField(PeopleData.INVALID_101_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getNeighborhoodLabel().getText(), PeopleData.INVALID_SIZE_100_MESSAGE);
		Assert.assertTrue(this.peoplePagePO.isInvalidNeighborhoodLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A23
	@Test(priority = 40)
	public void validCitySize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressCityField(PeopleData.VALID_100_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getAdressCityValue(), PeopleData.VALID_100_SIZE);
		Assert.assertFalse(this.peoplePagePO.isInvalidCityLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A24
	@Test(priority = 41)
	public void invalidCitySize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressCityField(PeopleData.INVALID_101_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getCityLabel().getText(), PeopleData.INVALID_SIZE_100_MESSAGE);
		Assert.assertTrue(this.peoplePagePO.isInvalidCityLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A25
	@Test(priority = 42)
	public void validStateSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressStateField(PeopleData.VALID_255_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getAdressStateValue(), PeopleData.VALID_255_SIZE);
		Assert.assertFalse(this.peoplePagePO.isInvalidStateLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A26
	@Test(priority = 43)
	public void invalidStateSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressStateField(PeopleData.INVALID_256_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getStateLabel().getText(), CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.peoplePagePO.isInvalidStateLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A27
	@Test(priority = 44)
	public void validCountrySize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressCountryField(PeopleData.VALID_255_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePagePO.getAdressCountryValue(), PeopleData.VALID_255_SIZE);
		Assert.assertFalse(this.peoplePagePO.isInvalidCountryLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A28
	@Test(priority = 45)
	public void invalidCountrySize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();

		this.peoplePagePO.clickOnAdressTab();
		this.peoplePagePO.fillAdressCountryField(PeopleData.INVALID_256_SIZE);
		this.peoplePagePO.fillAdressCEPField(PeopleData.VALID_CEP_NUMBER_8_SIZE + Keys.TAB);

		Assert.assertEquals(this.peoplePage.getCountryLabel().getText(), CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.peoplePagePO.isInvalidCountryLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A29
	@Test(priority = 46)
	public void validLoginSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.clickOnAccessDataTab();
		this.peoplePagePO.clickOnUserAccessToogle();
		this.peoplePagePO.fillUserLoginField(PeopleData.VALID_255_SIZE + Keys.TAB);
		this.peoplePagePO.selectUserRole(PeopleData.USER_ROLE_COLLAB);

		Assert.assertEquals(this.peoplePagePO.getUserLoginFieldValue(), PeopleData.VALID_255_SIZE);
		Assert.assertFalse(this.peoplePagePO.isInvalidLoginLabelDisplayed());
	}

	// CharacterLimitOfFields_30714_A30
	@Test(priority = 47)
	public void invalidLoginSize() {
		this.commonPagePO.goToPeoplePage();
		this.peoplePagePO.clickOnAddButton();
		this.peoplePagePO.clickOnAccessDataTab();
		this.peoplePagePO.clickOnUserAccessToogle();
		this.peoplePagePO.fillUserLoginField(PeopleData.INVALID_256_SIZE + Keys.TAB);
		this.peoplePagePO.selectUserRole(PeopleData.USER_ROLE_COLLAB);

		Assert.assertEquals(this.peoplePage.getInvalidUserLabel().getText(), CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.peoplePagePO.isInvalidLoginLabelDisplayed());
	}

}
