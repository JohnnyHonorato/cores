package br.edu.ufcg.virtus.automation.tests;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import br.edu.ufcg.virtus.automation.config.EnvironmentSetup;
import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.database.OrganizationsDatabase;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.ConfigData;
import br.edu.ufcg.virtus.automation.dataset.OrganizationsData;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.page.OrganizationsPage;
import br.edu.ufcg.virtus.automation.pagePO.CommonPagePO;
import br.edu.ufcg.virtus.automation.pagePO.LoginPagePO;
import br.edu.ufcg.virtus.automation.pagePO.OrganizationsPagePO;
import br.edu.ufcg.virtus.automation.utils.Utils;

public class OrganizationsTests {

	private OrganizationsPagePO organizationsPagePO;
	private OrganizationsPage organizationsPage;
	private SeleniumConfig seleniumConfig;
	private LoginPagePO loginPagePO;
	private CommonPagePO commonPagePO;
	private CommonPage commonPage;

    @Parameters({ "env", "headless" })
    @BeforeClass(alwaysRun = true)
    public void setupBeforeTest(@Optional("local") final String env, @Optional("false") final String headless)
            throws SQLException, IOException {

        // Setup environment according parameter
        EnvironmentSetup.setup(env, headless);

		// Clear database
		OrganizationsDatabase.clearOrganizationsDataOnDatabase();
		OrganizationsDatabase.clearImageLogoDirectory();

		// Inject automation dataset on database
		OrganizationsDatabase.injectOrganizationsDataOnDatabase();

		// Initializing selenium config and page objects classes
		this.seleniumConfig = new SeleniumConfig();
		this.commonPagePO = new CommonPagePO(this.seleniumConfig);
		this.organizationsPagePO = new OrganizationsPagePO(this.seleniumConfig, this.commonPagePO.commonPage,
				this.commonPagePO);
		this.organizationsPage = this.organizationsPagePO.organizationsPage;
		this.commonPage = this.commonPagePO.commonPage;
		this.loginPagePO = new LoginPagePO(this.seleniumConfig);
		this.loginPagePO.loginSuccessfully();
	}

	@AfterClass(alwaysRun = true)
	public void setupAfterTest() throws SQLException, IOException {

		// Delete automation dataset on database
		OrganizationsDatabase.clearOrganizationsDataOnDatabase();
		OrganizationsDatabase.clearImageLogoDirectory();

		// Close organization database connection
		OrganizationsDatabase.closeOrganizationsDatabaseConnection();

		// Quit selenium driver
		this.seleniumConfig.getDriver().quit();
	}

	// AddOrganizationWithValidData_25772_A1
	@Test(priority = 0)
	public void addOrganizationWithValidData() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.addOrganizationWithMandatoryFields(OrganizationsData.VALID_CNPJ_1,
				OrganizationsData.VALID_COMPANY_NAME_1, OrganizationsData.VALID_FANTASY_NAME_1,
				OrganizationsData.VALID_EMAIL_1, OrganizationsData.MAIN_EMAIL_TYPE);

		Assert.assertEquals(this.organizationsPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_ADDED);
		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_1);
		Assert.assertTrue(
				this.organizationsPagePO.isOrganizationDisplayedOnTable(OrganizationsData.VALID_FANTASY_NAME_1));
		Assert.assertTrue(this.organizationsPage.getAddButton().isDisplayed());
	}

	// AddOrganizationWithInvalidCNPJ_25773_A1
	@Test(priority = 1)
	public void caseBlankCNPJ() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.clickOnAddButton();
		this.organizationsPagePO.fillCNPJField(CommonData.EMPTY_VALUE);

		Assert.assertFalse(this.organizationsPage.getVerifyButton().isEnabled());
		Assert.assertTrue(this.organizationsPage.getCNPJField().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getInvalidCNPJMessage(), CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
	}

	// AddOrganizationWithInvalidCNPJ_25773_A2
	@Test(priority = 2)
	public void caseInvalidMaskCharactersForCNPJ() throws InterruptedException {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.clickOnAddButton();
		this.organizationsPagePO.fillCNPJField(OrganizationsData.INVALID_CNPJ_MASK);

		Assert.assertFalse(this.organizationsPage.getVerifyButton().isEnabled());
		Assert.assertTrue(this.organizationsPage.getCNPJField().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getCNPJValue(), CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.organizationsPagePO.getInvalidCNPJMessage(), CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
	}

	// AddOrganizationWithInvalidCNPJ_25773_A3
	@Test(priority = 3)
	public void invalidCNPJ() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.clickOnAddButton();
		this.organizationsPagePO.fillCNPJField(OrganizationsData.INVALID_CNPJ);

		Assert.assertFalse(this.organizationsPage.getVerifyButton().isEnabled());
		Assert.assertTrue(this.organizationsPage.getCNPJField().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getInvalidCNPJMessage(), OrganizationsData.INVALID_CNPJ_MESSAGE);
	}

	// AddOrganizationWithInvalidData_25806_A1
	@Test(priority = 4)
	public void caseBlankFields() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.clickOnAddButton();
		this.organizationsPagePO.fillCNPJField(OrganizationsData.VALID_CNPJ_2);
		this.organizationsPagePO.clickOnVerifyButton();
		this.organizationsPagePO.fillCompanyName(CommonData.EMPTY_VALUE);
		this.organizationsPagePO.fillFantasyName(CommonData.EMPTY_VALUE);

		this.organizationsPagePO.fillEmailField(CommonData.EMPTY_VALUE);
		this.organizationsPagePO.clickOnEmailTypeField();
		this.organizationsPagePO.fillCompanyName(CommonData.EMPTY_VALUE);

		Assert.assertTrue(this.organizationsPage.getMandatoryCompanyNameHint().isDisplayed());
		Assert.assertTrue(this.organizationsPage.getMandatoryFantasyNameHint().isDisplayed());
		Assert.assertTrue(this.organizationsPage.getMandatoryEmailHint().isDisplayed());
		Assert.assertTrue(this.organizationsPage.getMandatoryEmailTypeHint().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getMandatoryCompanyNameErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertEquals(this.organizationsPagePO.getMandatoryFantasyNameErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertEquals(this.organizationsPagePO.getMandatoryEmailErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertEquals(this.organizationsPagePO.getMandatoryEmailTypeErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// AddOrganizationWithInvalidData_25806_A2
	@Test(priority = 5)
	public void caseBlankEmail() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.clickOnAddButton();
		this.organizationsPagePO.fillCNPJField(OrganizationsData.VALID_CNPJ_2);
		this.organizationsPagePO.clickOnVerifyButton();
		this.organizationsPagePO.fillCompanyName(OrganizationsData.VALID_COMPANY_NAME_2);
		this.organizationsPagePO.fillFantasyName(OrganizationsData.VALID_FANTASY_NAME_2);

		this.organizationsPagePO.fillEmailField(CommonData.EMPTY_VALUE);
		this.organizationsPagePO.selectEmailType(OrganizationsData.MAIN_EMAIL_TYPE);

		Assert.assertTrue(this.organizationsPage.getMandatoryEmailHint().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getMandatoryEmailErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// AddOrganizationWithInvalidData_25806_A3
	@Test(priority = 6)
	public void caseBlankEmailType() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.clickOnAddButton();
		this.organizationsPagePO.fillCNPJField(OrganizationsData.VALID_CNPJ_2);
		this.organizationsPagePO.clickOnVerifyButton();

		this.organizationsPagePO.fillEmailField(OrganizationsData.VALID_EMAIL_2);
		this.organizationsPagePO.clickOnEmailTypeField();
		this.organizationsPagePO.fillCompanyName(OrganizationsData.VALID_COMPANY_NAME_2);
		this.organizationsPagePO.fillFantasyName(OrganizationsData.VALID_FANTASY_NAME_2);

		Assert.assertTrue(this.organizationsPage.getMandatoryEmailTypeHint().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getMandatoryEmailTypeErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// AddOrganizationWithInvalidData_25806_A4
	@Test(priority = 7)
	public void caseBlankCompanyName() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.clickOnAddButton();
		this.organizationsPagePO.fillCNPJField(OrganizationsData.VALID_CNPJ_2);
		this.organizationsPagePO.clickOnVerifyButton();
		this.organizationsPagePO.fillCompanyName(CommonData.EMPTY_VALUE);
		this.organizationsPagePO.fillFantasyName(OrganizationsData.VALID_FANTASY_NAME_2);

		this.organizationsPagePO.fillEmailField(OrganizationsData.VALID_EMAIL_2);
		this.organizationsPagePO.selectEmailType(OrganizationsData.MAIN_EMAIL_TYPE);

		Assert.assertTrue(this.organizationsPage.getMandatoryCompanyNameHint().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getMandatoryCompanyNameErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// AddOrganizationWithInvalidData_25806_A5
	@Test(priority = 8)
	public void caseBlankFantasyName() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.clickOnAddButton();
		this.organizationsPagePO.fillCNPJField(OrganizationsData.VALID_CNPJ_2);
		this.organizationsPagePO.clickOnVerifyButton();
		this.organizationsPagePO.fillCompanyName(OrganizationsData.VALID_COMPANY_NAME_2);
		this.organizationsPagePO.fillFantasyName(CommonData.EMPTY_VALUE);

		this.organizationsPagePO.fillEmailField(OrganizationsData.VALID_EMAIL_2);
		this.organizationsPagePO.selectEmailType(OrganizationsData.MAIN_EMAIL_TYPE);

		Assert.assertTrue(this.organizationsPage.getMandatoryFantasyNameHint().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getMandatoryFantasyNameErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// AddOrganizationsWithTheSameNameAndDifferentCNPJ_25807_A1
	@Test(priority = 9)
	public void addOrganizationsWithTheSameNameAndDifferentCNPJ() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.addOrganizationWithMandatoryFields(OrganizationsData.VALID_CNPJ_4,
				OrganizationsData.VALID_COMPANY_NAME_3, OrganizationsData.VALID_FANTASY_NAME_3,
				OrganizationsData.VALID_EMAIL_3, OrganizationsData.MAIN_EMAIL_TYPE);

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_COMPANY_NAME_3);

		Assert.assertEquals(this.organizationsPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_ADDED);

		this.organizationsPage.waitForOrganizationDisplayedOnTable(OrganizationsData.VALID_FANTASY_NAME_3);
		Assert.assertTrue(
				this.organizationsPagePO.isOrganizationDisplayedOnTable(OrganizationsData.VALID_FANTASY_NAME_3));
		Assert.assertTrue(this.organizationsPage.getAddButton().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getCountOfOrganizationsDisplayedOnTable(
				OrganizationsData.VALID_FANTASY_NAME_3), OrganizationsData.NUMBER_OF_ORGANIZATIONS_INSERTED);
	}

	// AddOrganizationWithCNPJThatAlreadyExists_25808_A1
	@Test(priority = 10)
	public void AddOrganizationWithCNPJThatAlreadyExists() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.clickOnAddButton();
		this.organizationsPagePO.fillCNPJField(OrganizationsData.VALID_CNPJ_5);
		this.organizationsPagePO.clickOnVerifyButton();

		this.organizationsPagePO.clickOnEditEmailButton();

		Assert.assertEquals(this.organizationsPagePO.getCompanyNameValue(), OrganizationsData.VALID_COMPANY_NAME_4);
		Assert.assertEquals(this.organizationsPagePO.getFantasyNameValue(), OrganizationsData.VALID_FANTASY_NAME_4);
		Assert.assertEquals(this.organizationsPagePO.getEmailValue(), OrganizationsData.VALID_EMAIL_4);
		Assert.assertEquals(this.organizationsPagePO.getEmailTypeValue(), OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);
	}

	// AddOrganizationWithAllFieldsFilledSucessfully_30488_A1
	@Test(priority = 11)
	public void AddOrganizationWithAllFieldsFilledSucessfully() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.addOrganizationWithAllFields(OrganizationsData.VALID_CNPJ_6,
				OrganizationsData.VALID_COMPANY_NAME_5, OrganizationsData.VALID_FANTASY_NAME_5,
				OrganizationsData.VALID_DESCRIPTION_1, Utils.getProjectPath() + OrganizationsData.IMAGE_PATH_2,
				OrganizationsData.VALID_EMAIL_5, OrganizationsData.MAIN_EMAIL_TYPE, OrganizationsData.VALID_DDI_CODE_1,
				OrganizationsData.VALID_PHONE_NUMBER_1, OrganizationsData.MAIN_PHONE_TYPE,
				OrganizationsData.NATIONAL_DOMAIN, OrganizationsData.VALID_CEP_1, OrganizationsData.VALID_STREET_1,
				OrganizationsData.VALID_ADDRESS_NUMBER_1, OrganizationsData.VALID_COMPLEMENT_1,
				OrganizationsData.VALID_DISTRICT_1, OrganizationsData.VALID_CITY_1, OrganizationsData.VALID_STATE_1,
				OrganizationsData.VALID_COUNTRY_1, OrganizationsData.VALID_COLLABORATOR_NAME_1,
				OrganizationsData.VALID_DEPARTAMENT_1, OrganizationsData.VALID_OFFICE_1);

		Assert.assertEquals(this.organizationsPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_ADDED);
		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_5);
		this.organizationsPage.waitForOrganizationDisplayedOnTable(OrganizationsData.VALID_FANTASY_NAME_5);
		Assert.assertTrue(
				this.organizationsPagePO.isOrganizationDisplayedOnTable(OrganizationsData.VALID_FANTASY_NAME_5));

		Assert.assertTrue(this.organizationsPage.getAddButton().isDisplayed());
	}

	// RemoveAnOrganizationSuccessfully_25776_A1
	@Test(priority = 12)
	public void RemoveAnOrganizationSuccessfully() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.removeOrganization(OrganizationsData.VALID_FANTASY_NAME_6);

		Assert.assertEquals(this.organizationsPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_REMOVAL);
		Assert.assertEquals(this.organizationsPagePO.getMsgRegisterNotFound(), CommonData.MSG_REGISTER_NOT_FOUND);
		Assert.assertFalse(
				this.organizationsPagePO.isOrganizationDisplayedOnTable(OrganizationsData.VALID_FANTASY_NAME_6));
	}

	// CancelRemovalOrganization_30630_A1
	@Test(priority = 13)
	public void CaseThroughButtonNo() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_7);
		this.organizationsPagePO.clickToRemove();
		this.organizationsPagePO.clickOnNoToCancelRemoval();

		Assert.assertTrue(
				this.organizationsPagePO.isOrganizationDisplayedOnTable(OrganizationsData.VALID_FANTASY_NAME_7));
		Assert.assertTrue(this.commonPage.getDeleteButton().isDisplayed());
		Assert.assertTrue(this.commonPage.getDeleteButton().isEnabled());
	}

	// CancelRemovalOrganization_30630_A2
	@Test(priority = 14)
	public void CaseThroughButtonX() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_7);
		this.organizationsPagePO.clickToRemove();
		this.organizationsPagePO.clickOnXToCancelRemoval();

		Assert.assertTrue(
				this.organizationsPagePO.isOrganizationDisplayedOnTable(OrganizationsData.VALID_FANTASY_NAME_7));
		Assert.assertTrue(this.commonPage.getDeleteButton().isDisplayed());
		Assert.assertTrue(this.commonPage.getDeleteButton().isEnabled());
	}

	// EditOrganizationWithValidData_25774_A1
	@Test(priority = 15)
	public void CaseEditCNPJWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.fillCNPJField(OrganizationsData.NEW_CNPJ);
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);

		Assert.assertTrue(this.organizationsPage.getCNPJField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getCNPJValue(), OrganizationsData.VALID_CNPJ_9);
		Assert.assertEquals(this.organizationsPagePO.getCNPJValue(), OrganizationsData.NEW_CNPJ);
	}

	// EditOrganizationWithValidData_25774_A2
	@Test(priority = 16)
	public void CaseEditCompanyNameWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.fillCompanyName(OrganizationsData.NEW_COMPANY_NAME);
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);

		Assert.assertTrue(this.organizationsPage.getCompanyNameField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getCompanyNameValue(), OrganizationsData.VALID_COMPANY_NAME_8);
		Assert.assertEquals(this.organizationsPagePO.getCompanyNameValue(), OrganizationsData.NEW_COMPANY_NAME);
	}

	// EditOrganizationWithValidData_25774_A3
	@Test(priority = 17)
	public void CaseEditFantasyNameWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_11);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_11);
		this.organizationsPagePO.fillFantasyName(OrganizationsData.NEW_FANTASY_NAME);
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.NEW_FANTASY_NAME);
		this.organizationsPagePO.clickToEdit(OrganizationsData.NEW_FANTASY_NAME);

		Assert.assertTrue(this.organizationsPage.getFantasyNameField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getFantasyNameValue(), OrganizationsData.VALID_FANTASY_NAME_11);
		Assert.assertEquals(this.organizationsPagePO.getFantasyNameValue(), OrganizationsData.NEW_FANTASY_NAME);
	}

	// EditOrganizationWithValidData_25774_A4
	@Test(priority = 18)
	public void CaseEditImageLogoWithSuccess() {
		System.out.println("Implementar quando a logo img for adicionada na tela de organizations");
	}

	// EditOrganizationWithValidData_25774_A5
	@Test(priority = 19)
	public void CaseEditDescriptionWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.fillDescriptionField(OrganizationsData.NEW_DESCRIPTION);
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);

		Assert.assertTrue(this.organizationsPage.getDescriptionField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getDescriptionValue(), OrganizationsData.VALID_DESCRIPTION_2);
		Assert.assertEquals(this.organizationsPagePO.getDescriptionValue(), OrganizationsData.NEW_DESCRIPTION);
	}

	// EditOrganizationWithValidData_25774_A6
	@Test(priority = 20)
	public void CaseEditContactInfoEmailTabEmailWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnEditEmailButton();
		this.organizationsPagePO.fillEmailField(OrganizationsData.NEW_EMAIL);
		this.organizationsPagePO.clickOnSaveEditEmailButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnEditEmailButton();

		Assert.assertTrue(this.organizationsPage.getEmailField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getEmailValue(), OrganizationsData.VALID_EMAIL_8);
		Assert.assertEquals(this.organizationsPagePO.getEmailValue(), OrganizationsData.NEW_EMAIL);
	}

	// EditOrganizationWithValidData_25774_A7
	@Test(priority = 21)
	public void CaseEditContactInfoEmailTabEmailTypeWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnEditEmailButton();
		this.organizationsPagePO.selectEmailType(OrganizationsData.COMMERCIAL_EMAIL_TYPE);
		this.organizationsPagePO.clickOnSaveEditEmailButton();
		this.organizationsPagePO.clickOnEditEmailButton();

		Assert.assertTrue(this.commonPage.getEmailTypeField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getEmailTypeValue(), OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);
		Assert.assertEquals(this.organizationsPagePO.getEmailTypeValue(),
				OrganizationsData.COMMERCIAL_EMAIL_TYPE_ENGLISH);
	}

	// EditOrganizationWithValidData_25774_A8
	@Test(priority = 22)
	public void CaseEditContactInfoTelephoneTabPhoneCodeWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnTelephoneTab();
		this.organizationsPagePO.clickOnEditTelephoneButton();
		this.organizationsPagePO.fillDDICodeField(OrganizationsData.NEW_DDI_CODE);
		this.organizationsPagePO.clickOnSaveEditTelephoneButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnTelephoneTab();
		this.organizationsPagePO.clickOnEditTelephoneButton();

		Assert.assertTrue(this.organizationsPage.getDDICodeField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getPhoneDDICodeValue(), OrganizationsData.VALID_DDI_CODE_2);
		Assert.assertEquals(this.organizationsPagePO.getPhoneDDICodeValue(), OrganizationsData.NEW_DDI_CODE);
	}

	// EditOrganizationWithValidData_25774_A9
	@Test(priority = 23)
	public void CaseEditContactInfoTelephoneTabPhoneNumberWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnTelephoneTab();
		this.organizationsPagePO.clickOnEditTelephoneButton();
		this.organizationsPagePO.fillPhoneNumber(OrganizationsData.NEW_PHONE_NUMBER);
		this.organizationsPagePO.clickOnSaveEditTelephoneButton();
		this.organizationsPagePO.clickOnSaveButton();
		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnTelephoneTab();
		this.organizationsPagePO.clickOnEditTelephoneButton();

		Assert.assertTrue(this.organizationsPage.getPhoneNumberField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getPhoneNumberValue(), OrganizationsData.VALID_PHONE_NUMBER_2);
		Assert.assertEquals(this.organizationsPagePO.getPhoneNumberValue(), OrganizationsData.NEW_PHONE_NUMBER);
	}

	// EditOrganizationWithValidData_25774_A10
	@Test(priority = 24)
	public void CaseEditContactInfoTelephoneTabPhoneTypeWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnTelephoneTab();
		this.organizationsPagePO.clickOnEditTelephoneButton();
		this.organizationsPagePO.selectPhoneType(OrganizationsData.CELULAR_PHONE_TYPE);
		this.organizationsPagePO.clickOnSaveEditTelephoneButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnTelephoneTab();
		this.organizationsPagePO.clickOnEditTelephoneButton();

		Assert.assertTrue(this.commonPage.getPhoneTypeField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getPhoneTypeValue(), OrganizationsData.MAIN_PHONE_TYPE);
		Assert.assertEquals(this.organizationsPagePO.getPhoneTypeValue(), OrganizationsData.CELULAR_PHONE_TYPE_ENGLISH);
	}

	// EditOrganizationWithValidData_25774_A11
	@Test(priority = 25)
	public void CaseEditContactInfoAddressTabDomainWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();
		this.organizationsPagePO.selectDomain(OrganizationsData.INTERNACIONAL_DOMAIN);
		this.organizationsPagePO.clickOnSaveEditAddressButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();

		Assert.assertTrue(this.commonPage.getDomainField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getDomainValue(), OrganizationsData.NATIONAL_DOMAIN);
		Assert.assertEquals(this.organizationsPagePO.getDomainValue(), OrganizationsData.INTERNACIONAL_DOMAIN_ENGLISH);
	}

	// EditOrganizationWithValidData_25774_A12
	@Test(priority = 26)
	public void CaseEditContactInfoAddressTabCepWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();
		this.organizationsPagePO.fillCepField(OrganizationsData.NEW_CEP);
		this.organizationsPagePO.clickOnSaveEditAddressButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();

		Assert.assertTrue(this.organizationsPage.getCepField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getCepValue(), OrganizationsData.VALID_CEP_2);
		Assert.assertEquals(this.organizationsPagePO.getCepValue(), OrganizationsData.NEW_CEP);
	}

	// EditOrganizationWithValidData_25774_A13
	@Test(priority = 27)
	public void CaseEditContactInfoAddressTabStreetWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();
		this.organizationsPagePO.fillStreetField(OrganizationsData.NEW_STREET);
		this.organizationsPagePO.clickOnSaveEditAddressButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();

		Assert.assertTrue(this.organizationsPage.getStreetField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getStreetValue(), OrganizationsData.VALID_STREET_2);
		Assert.assertEquals(this.organizationsPagePO.getStreetValue(), OrganizationsData.NEW_STREET);
	}

	// EditOrganizationWithValidData_25774_A14
	@Test(priority = 28)
	public void CaseEditContactInfoAddressTabAddressNumberWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();
		this.organizationsPagePO.fillAddressNumber(OrganizationsData.NEW_ADDRESS_NUMBER);
		this.organizationsPagePO.clickOnSaveEditAddressButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();

		Assert.assertTrue(this.organizationsPage.getAddressNumber().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getAddressNumberValue(),
				OrganizationsData.VALID_ADDRESS_NUMBER_2);
		Assert.assertEquals(this.organizationsPagePO.getAddressNumberValue(), OrganizationsData.NEW_ADDRESS_NUMBER);
	}

	// EditOrganizationWithValidData_25774_A15
	@Test(priority = 29)
	public void CaseEditContactInfoAddressTabAddressComplementWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();
		this.organizationsPagePO.fillComplementField(OrganizationsData.NEW_COMPLEMENT_VALUE);
		this.organizationsPagePO.clickOnSaveEditAddressButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();

		Assert.assertTrue(this.organizationsPage.getComplementField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getAddressComplementValue(),
				OrganizationsData.VALID_COMPLEMENT_2);
		Assert.assertEquals(this.organizationsPagePO.getAddressComplementValue(),
				OrganizationsData.NEW_COMPLEMENT_VALUE);
	}

	// EditOrganizationWithValidData_25774_A16
	@Test(priority = 30)
	public void CaseEditContactInfoAddressTabAddressDistrictWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();
		this.organizationsPagePO.fillDistrictField(OrganizationsData.NEW_DISTRICT_VALUE);
		this.organizationsPagePO.clickOnSaveEditAddressButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();

		Assert.assertTrue(this.organizationsPage.getDistrictField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getDistrictValue(), OrganizationsData.VALID_DISTRICT_2);
		Assert.assertEquals(this.organizationsPagePO.getDistrictValue(), OrganizationsData.NEW_DISTRICT_VALUE);
	}

	// EditOrganizationWithValidData_25774_A17
	@Test(priority = 31)
	public void CaseEditContactInfoAddressTabAddressCityWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();
		this.organizationsPagePO.fillCityField(OrganizationsData.NEW_ADDRESS_CITY);
		this.organizationsPagePO.clickOnSaveEditAddressButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();

		Assert.assertTrue(this.organizationsPage.getCityField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getCityValue(), OrganizationsData.VALID_CITY_2);
		Assert.assertEquals(this.organizationsPagePO.getCityValue(), OrganizationsData.NEW_ADDRESS_CITY);
	}

	// EditOrganizationWithValidData_25774_A18
	@Test(priority = 32)
	public void CaseEditContactInfoAddressTabAddressStateWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();
		this.organizationsPagePO.fillStateField(OrganizationsData.NEW_ADDRESS_STATE);
		this.organizationsPagePO.clickOnSaveEditAddressButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();

		Assert.assertTrue(this.organizationsPage.getStateField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getStateValue(), OrganizationsData.VALID_STATE_2);
		Assert.assertEquals(this.organizationsPagePO.getStateValue(), OrganizationsData.NEW_ADDRESS_STATE);
	}

	// EditOrganizationWithValidData_25774_A19
	@Test(priority = 33)
	public void CaseEditContactInfoAddressTabAddressCountryWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();
		this.organizationsPagePO.fillCountryField(OrganizationsData.NEW_ADDRESS_COUNTRY);
		this.organizationsPagePO.clickOnSaveEditAddressButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.clickOnEditAddressButton();

		Assert.assertTrue(this.organizationsPage.getCountryField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getCountryValue(), OrganizationsData.VALID_COUNTRY_2);
		Assert.assertEquals(this.organizationsPagePO.getCountryValue(), OrganizationsData.NEW_ADDRESS_COUNTRY);
	}

	// EditOrganizationWithValidData_25774_A20
	@Test(priority = 34)
	public void CaseEditCollaboratorCollaboratorNameWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnEditCollaboratorButton();
		this.organizationsPagePO.clickToClearCollaboratorName();
		this.organizationsPagePO.fillCollaboratorField(OrganizationsData.NEW_COLLABORATOR_NAME);
		this.organizationsPagePO.clickOnSaveEditCollaboratorButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnEditCollaboratorButton();

		Assert.assertTrue(this.organizationsPage.getCollaboratorDropbox().isDisplayed());
		Assert.assertFalse(this.organizationsPagePO.getCollaboratorNameValue()
				.contains(OrganizationsData.VALID_COLLABORATOR_NAME_2));
		Assert.assertTrue(
				this.organizationsPagePO.getCollaboratorNameValue().contains(OrganizationsData.NEW_COLLABORATOR_NAME));
	}

	// EditOrganizationWithValidData_25774_A21
	@Test(priority = 35)
	public void CaseEditCollaboratorDepartamentWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnEditCollaboratorButton();

		this.organizationsPagePO.fillDepartamentField(OrganizationsData.NEW_DEPARTAMENT);
		this.organizationsPagePO.clickOnSaveEditCollaboratorButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnEditCollaboratorButton();

		Assert.assertTrue(this.organizationsPage.getDepartamentField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getDepartamentValue(), OrganizationsData.VALID_DEPARTAMENT_2);
		Assert.assertEquals(this.organizationsPagePO.getDepartamentValue(), OrganizationsData.NEW_DEPARTAMENT);
	}

	// EditOrganizationWithValidData_25774_A22
	@Test(priority = 36)
	public void CaseEditCollaboratorOfficeWithSuccess() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnEditCollaboratorButton();

		this.organizationsPagePO.fillOfficeField(OrganizationsData.NEW_OFFICE);
		this.organizationsPagePO.clickOnSaveEditCollaboratorButton();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickOnEditCollaboratorButton();

		Assert.assertTrue(this.organizationsPage.getOfficeField().isDisplayed());
		Assert.assertNotEquals(this.organizationsPagePO.getOfficeValue(), OrganizationsData.VALID_OFFICE_2);
		Assert.assertEquals(this.organizationsPagePO.getOfficeValue(), OrganizationsData.NEW_OFFICE);
	}

	// User Story - #34327 - Implementar testes automatizados para inserção de
	// imagem no crud de organização
	@Test(priority = 37)
	public void uploadImageLogoSuccessfully() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.addOrganizationWithAllFields(OrganizationsData.VALID_CNPJ_10,
				OrganizationsData.VALID_COMPANY_NAME_9, OrganizationsData.VALID_FANTASY_NAME_9,
				OrganizationsData.VALID_DESCRIPTION_3, Utils.getProjectPath() + OrganizationsData.IMAGE_PATH_1,
				OrganizationsData.VALID_EMAIL_9, OrganizationsData.MAIN_EMAIL_TYPE, OrganizationsData.VALID_DDI_CODE_3,
				OrganizationsData.VALID_PHONE_NUMBER_3, OrganizationsData.MAIN_PHONE_TYPE,
				OrganizationsData.NATIONAL_DOMAIN, OrganizationsData.VALID_CEP_3, OrganizationsData.VALID_STREET_3,
				OrganizationsData.VALID_ADDRESS_NUMBER_3, OrganizationsData.VALID_COMPLEMENT_3,
				OrganizationsData.VALID_DISTRICT_3, OrganizationsData.VALID_CITY_3, OrganizationsData.VALID_STATE_3,
				OrganizationsData.VALID_COUNTRY_3, OrganizationsData.VALID_COLLABORATOR_NAME_3,
				OrganizationsData.VALID_DEPARTAMENT_3, OrganizationsData.VALID_OFFICE_3);

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_9);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_9);

		Assert.assertTrue(this.organizationsPage.getImageUploaded().isDisplayed());
		Assert.assertNotNull(this.organizationsPagePO.getSrcFromImageUploaded());
	}

	// User Story - #34327 - Implementar testes automatizados para deleção de imagem
	// no crud de organização
	@Test(priority = 38)
	public void removeImageLogoSuccessfully() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_10);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_10);

		this.organizationsPagePO.clickToDeleteImageLogo();
		this.organizationsPagePO.clickOnYesToConfirmImageLogoRemoval();
		this.organizationsPagePO.clickOnSaveButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_10);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_10);

		Assert.assertFalse(this.organizationsPage.getRemoveImageLogoButton().isEnabled());

		final String imagePath = Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY
				+ OrganizationsData.IMAGE_NAME_3;

		Assert.assertFalse(Utils.isFileExists(imagePath));
		Assert.assertTrue(this.organizationsPage.getDefaultLogoImage().isDisplayed());
	}

	// Edit organization with invalid data_25775_A1
	@Test(priority = 39)
	public void CaseAllRequiredFieldsBlank() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);

		this.organizationsPagePO.clearField(this.organizationsPage.getCNPJField());
		this.organizationsPagePO.clearField(this.organizationsPage.getCompanyNameField());
		this.organizationsPagePO.clearField(this.organizationsPage.getFantasyNameField());
		this.organizationsPagePO.clickOnEditEmailButton();
		this.organizationsPagePO.clearField(this.organizationsPage.getEmailField());
		this.organizationsPagePO.clickOnEmailTypeField();

		Assert.assertTrue(this.organizationsPage.getMandatoryCNPJHint().isDisplayed());
		Assert.assertTrue(this.organizationsPage.getMandatoryCompanyNameHint().isDisplayed());
		Assert.assertTrue(this.organizationsPage.getMandatoryFantasyNameHint().isDisplayed());
		Assert.assertTrue(this.organizationsPage.getMandatoryEmailHint().isDisplayed());

		Assert.assertEquals(this.organizationsPagePO.getMandatoryCNPJErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertEquals(this.organizationsPagePO.getMandatoryCompanyNameErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertEquals(this.organizationsPagePO.getMandatoryFantasyNameErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertEquals(this.organizationsPagePO.getMandatoryEmailErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Edit organization with invalid data_25775_A2
	@Test(priority = 40)
	public void CaseBlankEmail() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);

		this.organizationsPagePO.clickOnEditEmailButton();

		this.organizationsPagePO.clearField(this.organizationsPage.getEmailField());
		this.organizationsPagePO.clickOnEmailTypeField();

		Assert.assertTrue(this.organizationsPage.getMandatoryEmailHint().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getMandatoryEmailErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.organizationsPage.getAddEmailButton().isEnabled());
	}

	// Edit organization with invalid data_25775_A3
	@Test(priority = 41)
	public void CaseBlankCompanyName() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);

		this.organizationsPagePO.clearField(this.organizationsPage.getCompanyNameField());
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertTrue(this.organizationsPage.getMandatoryCompanyNameHint().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getMandatoryCompanyNameErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Edit organization with invalid data_25775_A4
	@Test(priority = 42)
	public void CaseBlankFantasyName() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);

		this.organizationsPagePO.clearField(this.organizationsPage.getFantasyNameField());
		this.organizationsPagePO.clickOnCompanyNameField();

		Assert.assertTrue(this.organizationsPage.getMandatoryFantasyNameHint().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getMandatoryFantasyNameErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Edit organization with invalid data_25775_A5
	@Test(priority = 43)
	public void CaseBlankCNPJ() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);

		this.organizationsPagePO.clearField(this.organizationsPage.getCNPJField());
		this.organizationsPagePO.clickOnCompanyNameField();

		Assert.assertTrue(this.organizationsPage.getMandatoryCNPJHint().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getMandatoryCNPJErrorMessage(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Edit organization with invalid data_25775_A6
	@Test(priority = 44)
	public void CaseInvalidCNPJValue() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);

		this.organizationsPagePO.fillCNPJField(OrganizationsData.INVALID_CNPJ);
		this.organizationsPagePO.clickOnCompanyNameField();

		Assert.assertTrue(this.organizationsPage.getInvalidCNPJHint().isDisplayed());
		Assert.assertEquals(this.organizationsPagePO.getInvalidCNPJMessage(), OrganizationsData.INVALID_CNPJ_MESSAGE);
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Edit organization with invalid data_25775_A7
	@Test(priority = 45)
	public void CaseInvalidEmailFormat() {
		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);

		this.organizationsPagePO.clickOnEditEmailButton();
		this.organizationsPagePO.fillEmailField(OrganizationsData.INVALID_EMAIL_FORMAT);

		Assert.assertTrue(this.organizationsPage.getMandatoryEmailHint().isDisplayed());
		Assert.assertEquals(this.organizationsPage.getMandatoryEmailHint().getText(),
				OrganizationsData.INVALID_EMAIL_FORMAT_MESSAGE);
		Assert.assertFalse(this.organizationsPage.getAddEmailButton().isEnabled());
	}

	// Edit organization with invalid data_25775_A8
	@Test(priority = 46)
	public void CaseInvalidCNPJMask() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_8);

		this.organizationsPagePO.fillCNPJField(OrganizationsData.INVALID_CNPJ_MASK);
		this.organizationsPagePO.clickOnCompanyNameField();

		Assert.assertEquals(this.organizationsPagePO.getCNPJValue(), CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.organizationsPagePO.getInvalidCNPJMessage(), CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Cancel edit_30647_A1
	@Test(priority = 47)
	public void CancelEdit() {

		this.commonPagePO.goToOrganizationsPage();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_30);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_30);

		this.organizationsPagePO.fillCNPJField(OrganizationsData.NEW_CNPJ);
		this.organizationsPagePO.fillCompanyName(OrganizationsData.NEW_COMPANY_NAME);
		this.organizationsPagePO.fillFantasyName(OrganizationsData.NEW_FANTASY_NAME);

		this.organizationsPagePO.clickOnEditEmailButton();
		this.organizationsPagePO.fillEmailField(OrganizationsData.NEW_EMAIL);
		this.organizationsPagePO.selectEmailType(OrganizationsData.COMMERCIAL_EMAIL_TYPE);
		this.organizationsPagePO.clickOnSaveEditEmailButton();

		this.organizationsPagePO.clickOnBackButton();

		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_30);
		this.organizationsPagePO.clickToEdit(OrganizationsData.VALID_FANTASY_NAME_30);
		this.organizationsPagePO.clickOnEditEmailButton();

		Assert.assertNotEquals(Utils.removeCNPJMask(this.organizationsPagePO.getCNPJValue()),
				OrganizationsData.NEW_CNPJ);
		Assert.assertNotEquals(this.organizationsPagePO.getCompanyNameValue(), OrganizationsData.NEW_COMPANY_NAME);
		Assert.assertNotEquals(this.organizationsPagePO.getEmailValue(), OrganizationsData.NEW_EMAIL);
		Assert.assertNotEquals(this.organizationsPagePO.getEmailTypeValue(),
				OrganizationsData.COMMERCIAL_EMAIL_TYPE_ENGLISH);

		Assert.assertEquals(Utils.removeCNPJMask(this.organizationsPagePO.getCNPJValue()),
				OrganizationsData.VALID_CNPJ_30);
		Assert.assertEquals(this.organizationsPagePO.getCompanyNameValue(), OrganizationsData.VALID_COMPANY_NAME_30);
		Assert.assertEquals(this.organizationsPagePO.getFantasyNameValue(), OrganizationsData.VALID_FANTASY_NAME_30);
		Assert.assertEquals(this.organizationsPagePO.getEmailValue(), OrganizationsData.VALID_EMAIL_30);
		Assert.assertEquals(this.organizationsPagePO.getEmailTypeValue(), OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);
	}

	// Search Organization_37021_A1
	@Test(priority = 48)
	public void ValidOrganization() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.filterByFantasyName(OrganizationsData.VALID_FANTASY_NAME_8);

		Assert.assertEquals(this.organizationsPagePO.getFilterValue(), OrganizationsData.VALID_FANTASY_NAME_8);
		Assert.assertTrue(
				this.organizationsPagePO.isOrganizationDisplayedOnTable(OrganizationsData.VALID_FANTASY_NAME_8));
	}

	// Search Organization_37021_A2
	@Test(priority = 49)
	public void InvalidOrganization() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.filterByFantasyName(OrganizationsData.INVALID_FANTASY_NAME);

		Assert.assertEquals(this.organizationsPagePO.getFilterValue(), OrganizationsData.INVALID_FANTASY_NAME);
		Assert.assertEquals(this.organizationsPagePO.getMsgRegisterNotFound(), CommonData.MSG_REGISTER_NOT_FOUND);
	}

	// Character limit for fields_29370_A1
	@Test(priority = 50)
	public void ValidCharactersQuantityCNJP14() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.clickOnAddButton();
		this.organizationsPagePO.fillCNPJField(OrganizationsData.VALID_CNPJ_VALUE);

		final String cnpjValue = Utils.removeCNPJMask(this.organizationsPagePO.getCNPJValue());
		Assert.assertEquals(cnpjValue, OrganizationsData.VALID_CNPJ_VALUE);
		Assert.assertEquals(cnpjValue.length(), OrganizationsData.VALID_CNPJ_SIZE);
		Assert.assertTrue(this.organizationsPage.getVerifyButton().isEnabled());
	}

	// Character limit for fields_29370_A2
	@Test(priority = 51)
	public void InvalidCharactersQuantityCNJP15() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.clickOnAddButton();
		this.organizationsPagePO.fillCNPJField(OrganizationsData.INVALID_CNPJ_VALUE);

		final String cnpjValue = Utils.removeCNPJMask(this.organizationsPagePO.getCNPJValue());
		Assert.assertNotEquals(cnpjValue, OrganizationsData.INVALID_CNPJ_VALUE);
		Assert.assertEquals(cnpjValue,
				OrganizationsData.INVALID_CNPJ_VALUE.substring(0, OrganizationsData.INVALID_CNPJ_VALUE.length() - 1));

		Assert.assertNotEquals(cnpjValue.length(), OrganizationsData.INVALID_CNPJ_SIZE);
		Assert.assertEquals(cnpjValue.length(), OrganizationsData.INVALID_CNPJ_SIZE - 1);
	}

	// Character limit for fields_29370_A3
	@Test(priority = 52)
	public void ValidCharactersQuantityCompanyName255() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.fillCompanyName(OrganizationsData.STRING_255_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getCompanyNameValue(), OrganizationsData.STRING_255_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getCompanyNameValue().length(), OrganizationsData.STRING_255_SIZE);
	}

	// Character limit for fields_29370_A4
	@Test(priority = 53)
	public void InvalidCharactersQuantityCompanyName256() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.fillCompanyName(OrganizationsData.STRING_256_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getMandatoryCompanyNameErrorMessage(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A5
	@Test(priority = 54)
	public void ValidCharactersQuantityFantasyName255() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.fillFantasyName(OrganizationsData.STRING_255_CHARACTERS);
		this.organizationsPagePO.clickOnCompanyNameField();

		Assert.assertEquals(this.organizationsPagePO.getFantasyNameValue(), OrganizationsData.STRING_255_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getFantasyNameValue().length(), OrganizationsData.STRING_255_SIZE);
	}

	// Character limit for fields_29370_A6
	@Test(priority = 55)
	public void InvalidCharactersQuantityFantasyName256() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.fillFantasyName(OrganizationsData.STRING_256_CHARACTERS);
		this.organizationsPagePO.clickOnCompanyNameField();

		Assert.assertEquals(this.organizationsPage.getMandatoryFantasyNameHint().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A7
	@Test(priority = 56)
	public void ValidCharactersQuantityEmail100() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.fillEmailField(OrganizationsData.STRING_100_CHARACTERS);
		this.organizationsPagePO.clickOnEmailTypeField();

		Assert.assertEquals(this.organizationsPagePO.getEmailValue(), OrganizationsData.STRING_100_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getEmailValue().length(), OrganizationsData.STRING_100_SIZE);
	}

	// Character limit for fields_29370_A8
	@Test(priority = 57)
	public void InvalidCharactersQuantityEmail101() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.fillEmailField(OrganizationsData.STRING_101_CHARACTERS);
		this.organizationsPagePO.clickOnEmailTypeField();

		Assert.assertEquals(this.organizationsPagePO.getMandatoryEmailErrorMessage(),
				CommonData.ERROR_MSG_BIGGER_THAN_100);
		Assert.assertTrue(this.organizationsPage.getMandatoryEmailHint().isDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A9
	@Test(priority = 58)
	public void ValidCharactersQuantityDDICode4() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnTelephoneTab();
		this.organizationsPagePO.fillDDICodeField(OrganizationsData.DDI_CODE_4_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getPhoneDDICodeValue(), OrganizationsData.DDI_CODE_4_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getPhoneDDICodeValue().length(),
				OrganizationsData.DDI_CODE_4_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isInvalidCharacterQuantityMsgForDDICodeDisplayed());
	}

	// Character limit for fields_29370_A10
	@Test(priority = 59)
	public void InvalidCharactersQuantityDDICode5() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnTelephoneTab();
		this.organizationsPagePO.fillDDICodeField(OrganizationsData.DDI_CODE_5_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getInvalidCharacterQuantityMsgForDDICode(),
				CommonData.ERROR_MSG_INVALID_DDI_CODE);
		Assert.assertTrue(this.organizationsPagePO.isInvalidCharacterQuantityMsgForDDICodeDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A11
	@Test(priority = 60)
	public void ValidCharactersQuantityPhoneNumber20() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnTelephoneTab();
		this.organizationsPagePO.fillPhoneNumber(OrganizationsData.PHONE_NUMBER_20_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getPhoneNumberValue(),
				OrganizationsData.PHONE_NUMBER_20_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getPhoneNumberValue().length(),
				OrganizationsData.PHONE_NUMBER_20_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isInvalidCharacterQuantityMsgForPhoneNumberDisplayed());
	}

	// Character limit for fields_29370_A12
	@Test(priority = 61)
	public void InvalidCharactersQuantityPhoneNumber21() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnTelephoneTab();
		this.organizationsPagePO.fillPhoneNumber(OrganizationsData.PHONE_NUMBER_21_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getInvalidCharacterQuantityMsgForPhoneNumber(),
				CommonData.ERROR_MSG_BIGGER_THAN_20);
		Assert.assertTrue(this.organizationsPagePO.isInvalidCharacterQuantityMsgForPhoneNumberDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A13
	@Test(priority = 62)
	public void ValidCharactersQuantityCep9() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillCepField(OrganizationsData.CEP_9_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getCepValue(), OrganizationsData.CEP_9_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getCepValue().length(), OrganizationsData.CEP_9_SIZE);
	}

	// Character limit for fields_29370_A14
	@Test(priority = 63)
	public void InvalidCharactersQuantityCep10() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillCepField(OrganizationsData.CEP_10_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertNotEquals(this.organizationsPagePO.getCepValue(), OrganizationsData.CEP_10_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getCepValue(),
				OrganizationsData.CEP_10_CHARACTERS.substring(0, OrganizationsData.CEP_10_CHARACTERS.length() - 1));
		Assert.assertNotEquals(this.organizationsPagePO.getCepValue().length(), OrganizationsData.CEP_10_SIZE);
		Assert.assertEquals(this.organizationsPagePO.getCepValue().length(), OrganizationsData.CEP_10_SIZE - 1);
	}

	// Character limit for fields_29370_A15
	@Test(priority = 64)
	public void ValidCharactersQuantityStreet255() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillStreetField(OrganizationsData.STRING_255_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getStreetValue(), OrganizationsData.STRING_255_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getStreetValue().length(), OrganizationsData.STRING_255_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isInvalidCharacterQuantityMsgForStreetDisplayed());
	}

	// Character limit for fields_29370_A16
	@Test(priority = 65)
	public void InvalidCharactersQuantityStreet256() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillStreetField(OrganizationsData.STRING_256_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getInvalidCharacterQuantityMsgForStreet(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.organizationsPagePO.isInvalidCharacterQuantityMsgForStreetDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A17
	@Test(priority = 66)
	public void ValidCharactersQuantityAddressNumber255() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillAddressNumber(OrganizationsData.STRING_255_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getAddressNumberValue(), OrganizationsData.STRING_255_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getAddressNumberValue().length(),
				OrganizationsData.STRING_255_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isInvalidCharacterQuantityMsgForAddressNumberDisplayed());
	}

	// Character limit for fields_29370_A18
	@Test(priority = 67)
	public void InvalidCharactersQuantityAddressNumber255() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillAddressNumber(OrganizationsData.STRING_256_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getInvalidCharacterQuantityMsgForAddressNumber(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.organizationsPagePO.isInvalidCharacterQuantityMsgForAddressNumberDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A19
	@Test(priority = 68)
	public void ValidCharactersQuantityComplement100() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillComplementField(OrganizationsData.STRING_100_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getAddressComplementValue(),
				OrganizationsData.STRING_100_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getAddressComplementValue().length(),
				OrganizationsData.STRING_100_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isInvalidCharacterQuantityMsgForAddressComplementDisplayed());
	}

	// Character limit for fields_29370_A20
	@Test(priority = 69)
	public void InvalidCharactersQuantityComplement101() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillComplementField(OrganizationsData.STRING_101_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getInvalidCharacterQuantityMsgForAddressComplement(),
				CommonData.ERROR_MSG_BIGGER_THAN_100);
		Assert.assertTrue(this.organizationsPagePO.isInvalidCharacterQuantityMsgForAddressComplementDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A21
	@Test(priority = 70)
	public void ValidCharactersQuantityDistrict100() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillDistrictField(OrganizationsData.STRING_100_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getDistrictValue(), OrganizationsData.STRING_100_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getDistrictValue().length(), OrganizationsData.STRING_100_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isInvalidCharacterQuantityMsgForDistrictDisplayed());
	}

	// Character limit for fields_29370_A22
	@Test(priority = 71)
	public void InvalidCharactersQuantityDistrict101() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillDistrictField(OrganizationsData.STRING_101_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getInvalidCharacterQuantityMsgForDistrict(),
				CommonData.ERROR_MSG_BIGGER_THAN_100);
		Assert.assertTrue(this.organizationsPagePO.isInvalidCharacterQuantityMsgForDistrictDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A23
	@Test(priority = 72)
	public void ValidCharactersQuantityCity100() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillCityField(OrganizationsData.STRING_100_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getCityValue(), OrganizationsData.STRING_100_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getCityValue().length(), OrganizationsData.STRING_100_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isInvalidCharacterQuantityMsgForCityDisplayed());
	}

	// Character limit for fields_29370_A24
	@Test(priority = 73)
	public void InvalidCharactersQuantityCity101() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillCityField(OrganizationsData.STRING_101_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getInvalidCharacterQuantityMsgForCity(),
				CommonData.ERROR_MSG_BIGGER_THAN_100);
		Assert.assertTrue(this.organizationsPagePO.isInvalidCharacterQuantityMsgForCityDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A25
	@Test(priority = 74)
	public void ValidCharactersQuantityState255() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillStateField(OrganizationsData.STRING_255_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getStateValue(), OrganizationsData.STRING_255_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getStateValue().length(), OrganizationsData.STRING_255_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isInvalidCharacterQuantityMsgForStateDisplayed());
	}

	// Character limit for fields_29370_A26
	@Test(priority = 75)
	public void InvalidCharactersQuantityState256() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillStateField(OrganizationsData.STRING_256_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getInvalidCharacterQuantityMsgForState(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.organizationsPagePO.isInvalidCharacterQuantityMsgForStateDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A27
	@Test(priority = 76)
	public void ValidCharactersQuantityCountry255() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillCountryField(OrganizationsData.STRING_255_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getCountryValue(), OrganizationsData.STRING_255_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getCountryValue().length(), OrganizationsData.STRING_255_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isInvalidCharacterQuantityMsgForCountryDisplayed());
	}

	// Character limit for fields_29370_A28
	@Test(priority = 77)
	public void InvalidCharactersQuantityCountry256() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillCountryField(OrganizationsData.STRING_256_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getInvalidCharacterQuantityMsgForCountry(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.organizationsPagePO.isInvalidCharacterQuantityMsgForCountryDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A29
	@Test(priority = 78)
	public void ValidSizeLogo10mb() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickToEditImageLogo();
		this.organizationsPagePO.uploadImageLogo(Utils.getProjectPath() + OrganizationsData.INVALID_IMAGE_SIZE_10_MB);

		Assert.assertTrue(this.organizationsPage.getImageLogoSelected().isDisplayed());
		Assert.assertTrue(this.organizationsPagePO.isSaveImageLogoButtonDisplayed());
	}

	// Character limit for fields_29370_A30
	@Test(priority = 79)
	public void InvalidSizeLogo11mb() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickToEditImageLogo();
		this.organizationsPagePO.uploadImageLogo(Utils.getProjectPath() + OrganizationsData.INVALID_IMAGE_SIZE_11_MB);

		Assert.assertEquals(this.organizationsPagePO.getToastMsg(), CommonData.INVALID_IMAGE_LOGO_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isSaveImageLogoButtonDisplayed());
	}

	// Character limit for fields_29370_A31
	@Test(priority = 80)
	public void ValidCharactersQuantityDescription5000() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillDescriptionField(OrganizationsData.STRING_5000_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getDescriptionValue(), OrganizationsData.STRING_5000_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getDescriptionValue().length(),
				OrganizationsData.STRING_5000_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isInvalidCharacterQuantityMsgForDescriptionDisplayed());
	}

	// Character limit for fields_29370_A32
	@Test(priority = 81)
	public void InvalidCharactersQuantityDescription5001() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.clickOnAddressTab();
		this.organizationsPagePO.fillDescriptionField(OrganizationsData.STRING_5001_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getInvalidCharacterQuantityMsgForDescription(),
				CommonData.ERROR_MSG_BIGGER_THAN_5000);
		Assert.assertTrue(this.organizationsPagePO.isInvalidCharacterQuantityMsgForDescriptionDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A33
	@Test(priority = 82)
	public void ValidCharactersQuantityOffice255() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.fillOfficeField(OrganizationsData.STRING_255_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getOfficeValue(), OrganizationsData.STRING_255_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getOfficeValue().length(), OrganizationsData.STRING_255_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isInvalidCharacterQuantityMsgForOfficeDisplayed());
	}

	// Character limit for fields_29370_A34
	@Test(priority = 83)
	public void InvalidCharactersQuantityOffice256() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.fillOfficeField(OrganizationsData.STRING_256_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getInvalidCharacterQuantityMsgForOffice(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.organizationsPagePO.isInvalidCharacterQuantityMsgForOfficeDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}

	// Character limit for fields_29370_A35
	@Test(priority = 84)
	public void ValidCharactersQuantityDepartament255() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.fillDepartamentField(OrganizationsData.STRING_255_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getDepartamentValue(), OrganizationsData.STRING_255_CHARACTERS);
		Assert.assertEquals(this.organizationsPagePO.getDepartamentValue().length(), OrganizationsData.STRING_255_SIZE);
		Assert.assertFalse(this.organizationsPagePO.isInvalidCharacterQuantityMsgForDepartamentDisplayed());
	}

	// Character limit for fields_29370_A36
	@Test(priority = 85)
	public void InvalidCharactersQuantityDepartament256() {

		this.commonPagePO.goToOrganizationsPage();
		this.organizationsPagePO.fillCNPJAndClickOnVerify(OrganizationsData.VALID_CNPJ_VALUE);

		this.organizationsPagePO.fillDepartamentField(OrganizationsData.STRING_256_CHARACTERS);
		this.organizationsPagePO.clickOnFantasyNameField();

		Assert.assertEquals(this.organizationsPagePO.getInvalidCharacterQuantityMsgForDepartament(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.organizationsPagePO.isInvalidCharacterQuantityMsgForDepartamentDisplayed());
		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
	}
}
