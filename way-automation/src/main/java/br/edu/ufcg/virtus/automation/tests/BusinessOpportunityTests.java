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
import br.edu.ufcg.virtus.automation.database.BusinessOpportunityDatabase;
import br.edu.ufcg.virtus.automation.database.OrganizationsDatabase;
import br.edu.ufcg.virtus.automation.dataset.BusinessOpportunityData;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.OrganizationsData;
import br.edu.ufcg.virtus.automation.page.BusinessOpportunityPage;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.pagePO.BusinessOpportunityPagePO;
import br.edu.ufcg.virtus.automation.pagePO.CommonPagePO;
import br.edu.ufcg.virtus.automation.pagePO.LoginPagePO;

public class BusinessOpportunityTests {

	private SeleniumConfig seleniumConfig;
	private LoginPagePO loginPagePO;
	private CommonPage commonPage;
	private CommonPagePO commonPagePO;
	private BusinessOpportunityPage businessOpportunityPage;
	private BusinessOpportunityPagePO businessOpportunityPagePO;

    @Parameters({ "env", "headless" })
    @BeforeClass(alwaysRun = true)
    public void setupBeforeTest(@Optional("local") final String env, @Optional("false") final String headless)
            throws SQLException, IOException {

        // Setup environment according parameter
        EnvironmentSetup.setup(env, headless);

		// Clear database
		OrganizationsDatabase.clearOrganizationsDataOnDatabase();
		OrganizationsDatabase.clearImageLogoDirectory();
		BusinessOpportunityDatabase.clearBusinessOpportunityDataOnDatabase();

		// Inject automation dataset on database
		BusinessOpportunityDatabase.injectOrganizationsDataOnDatabase();
		BusinessOpportunityDatabase.injectBusinessOpportunityDataOnDatabase();

		this.seleniumConfig = new SeleniumConfig();
		this.loginPagePO = new LoginPagePO(this.seleniumConfig);
		this.commonPagePO = new CommonPagePO(this.seleniumConfig);
		this.commonPage = new CommonPage(this.seleniumConfig);
		this.businessOpportunityPagePO = new BusinessOpportunityPagePO(this.seleniumConfig,
				this.commonPagePO.commonPage);
		this.businessOpportunityPage = this.businessOpportunityPagePO.businessOpportunityPage;

		this.loginPagePO.loginSuccessfully();
	}

	@AfterClass(alwaysRun = true)
	public void setupAfterClass() throws SQLException, IOException {

		// Delete automation dataset on database
		BusinessOpportunityDatabase.clearBusinessOpportunityDataOnDatabase();
		OrganizationsDatabase.clearOrganizationsDataOnDatabase();
		OrganizationsDatabase.clearImageLogoDirectory();

		// Close organization database connection
		OrganizationsDatabase.closeOrganizationsDatabaseConnection();
		BusinessOpportunityDatabase.closeBusinessOpportunityDatabaseConnection();

		this.seleniumConfig.getDriver().quit();
	}

	// Create filling all the fields - 29316_A1
	@Test(priority = 1)
	public void CreateBusinessOpportunityWithAllValidData() {

		this.businessOpportunityPagePO.insertBusinessOpportunity(BusinessOpportunityData.TITLE1,
				OrganizationsData.VALID_FANTASY_NAME_10, BusinessOpportunityData.DESCRIPTION1,
				OrganizationsData.VALID_COLLABORATOR_NAME_4);

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_ADDED);
		Assert.assertTrue(
				this.businessOpportunityPagePO.isBusinesOportunityDisplayedOnTable(BusinessOpportunityData.TITLE1));
	}

	// Create filling only mandatory fields - 29314_A1
	@Test(priority = 2)
	public void CreateBusinessOpportunityWithMandatoryFields() {

		this.businessOpportunityPagePO.insertBusinessOpportunity(BusinessOpportunityData.TITLE2,
				OrganizationsData.VALID_FANTASY_NAME_10, BusinessOpportunityData.DESCRIPTION2);

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_ADDED);
		Assert.assertTrue(
				this.businessOpportunityPagePO.isBusinesOportunityDisplayedOnTable(BusinessOpportunityData.TITLE2));
	}

	// Cancel insertion operation - 29319_A1
	@Test(priority = 3)
	public void CancelInsertionOperation() {

		this.businessOpportunityPagePO.fillAllBusinessOpportunityFields(BusinessOpportunityData.TITLE3,
				OrganizationsData.VALID_FANTASY_NAME_10, BusinessOpportunityData.DESCRIPTION3,
				OrganizationsData.VALID_COLLABORATOR_NAME_4);
		this.commonPage.getBackButton().click();

		Assert.assertTrue(this.businessOpportunityPage.getBusinessOpportunityTable().isDisplayed());
		Assert.assertFalse(
				this.businessOpportunityPagePO.isBusinesOportunityDisplayedOnTable(BusinessOpportunityData.TITLE3));
	}

	// Empty mandatory fields - 29325_A1
	@Test(priority = 4)
	public void OportunityTitleEmpty() {

		this.businessOpportunityPagePO.fillAllBusinessOpportunityFields("", OrganizationsData.VALID_FANTASY_NAME_10,
				BusinessOpportunityData.DESCRIPTION1, OrganizationsData.VALID_COLLABORATOR_NAME_4);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.businessOpportunityPage.getTitleFieldErrorLabel().getText(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
	}

	// Empty mandatory fields - 29325_A2
	@Test(priority = 5)
	public void OrganizationEmpty() {

		this.commonPagePO.goToBusinessOpportunityPage();
		this.commonPage.getAddButton().click();
		this.businessOpportunityPagePO.fillTitleField(BusinessOpportunityData.TITLE4);
		this.businessOpportunityPage.getOrganizationDropbox().click();
		this.businessOpportunityPagePO.fillDescriptionField(BusinessOpportunityData.DESCRIPTION4);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.businessOpportunityPage.getOrganizationErrorLabel().getText(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
	}

	// Empty mandatory fields - 29325_A3
	@Test(priority = 6)
	public void DescriptionEmpty() {

		this.businessOpportunityPagePO.fillAllBusinessOpportunityFields(BusinessOpportunityData.TITLE4,
				OrganizationsData.VALID_FANTASY_NAME_10, "", OrganizationsData.VALID_COLLABORATOR_NAME_4);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.businessOpportunityPage.getDescriptionErrorLabel().getText(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
	}

	// Invalid data in fields - 29333_A1
	@Test(priority = 7)
	public void CreateBOWithBiggerThanAllowedTitle() {

		this.businessOpportunityPagePO.fillAllBusinessOpportunityFields(BusinessOpportunityData.TITLE_BIGGER,
				OrganizationsData.VALID_FANTASY_NAME_10, BusinessOpportunityData.DESCRIPTION4,
				OrganizationsData.VALID_COLLABORATOR_NAME_4);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.businessOpportunityPage.getTitleFieldErrorLabel().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
	}

	// Invalid data in fields - 29333_A2
	@Test(priority = 8)
	public void CreateBOWithBiggerThanAllowedDescription() {

		this.businessOpportunityPagePO.fillAllBusinessOpportunityFields(BusinessOpportunityData.TITLE4,
				OrganizationsData.VALID_FANTASY_NAME_10, BusinessOpportunityData.DESCRIPTION_BIGGER,
				OrganizationsData.VALID_COLLABORATOR_NAME_4);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.businessOpportunityPage.getDescriptionErrorLabel().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_5000);
	}

	// Invalid data in fields - 29333_A3
	@Test(priority = 9)
	public void CreateBOWithInvalidTitle() {

		this.businessOpportunityPagePO.fillAllBusinessOpportunityFields(
				BusinessOpportunityData.INVALID_TITLE + Keys.TAB, OrganizationsData.VALID_FANTASY_NAME_10,
				BusinessOpportunityData.DESCRIPTION4, OrganizationsData.VALID_COLLABORATOR_NAME_4);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.businessOpportunityPage.getTitleFieldErrorLabel().getText(),
				CommonData.INVALID_FIELD_MESSAGE_ERROR);
	}

	// Duplicate title - 29335_A1
	@Test(priority = 10)
	public void CreateBOWithDuplicateTitle() {

		this.businessOpportunityPagePO.insertBusinessOpportunity(BusinessOpportunityData.TITLE5,
				OrganizationsData.VALID_FANTASY_NAME_10, BusinessOpportunityData.DESCRIPTION5);

		Assert.assertTrue(this.commonPage.getSaveButton().isDisplayed());
		Assert.assertEquals(this.commonPage.getToast().getText(), BusinessOpportunityData.MSG_DUPLICATED_BO_ERROR);
	}

	// Only mandatory fields modified - 29336_A1
	@Test(priority = 11)
	public void editBusinessOpportunityTitle() {

		this.businessOpportunityPagePO.editBusinessOpportunityTitle(BusinessOpportunityData.TITLE6A,
				BusinessOpportunityData.TITLE6B);
		this.commonPage.getSaveButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		Assert.assertTrue(
				this.businessOpportunityPagePO.isBusinesOportunityDisplayedOnTable(BusinessOpportunityData.TITLE6B));
	}

	// Only mandatory fields modified - 29336_A2
	@Test(priority = 12)
	public void editBusinessOpportunityOrganization() {

		this.businessOpportunityPagePO.editBusinessOpportunityOrganization(BusinessOpportunityData.TITLE7A,
				OrganizationsData.VALID_FANTASY_NAME_5);
		this.commonPage.getSaveButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		Assert.assertTrue(
				this.businessOpportunityPagePO.isBusinesOportunityDisplayedOnTable(BusinessOpportunityData.TITLE7A));
	}

	// Only mandatory fields modified - 29336_A3
	@Test(priority = 13)
	public void editBusinessOpportunityDescription() {

		this.businessOpportunityPagePO.editBusinessOpportunityDescription(BusinessOpportunityData.TITLE8A,
				BusinessOpportunityData.EDITED_DESCRIPTION);
		this.commonPage.getSaveButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		Assert.assertTrue(
				this.businessOpportunityPagePO.isBusinesOportunityDisplayedOnTable(BusinessOpportunityData.TITLE8A));
	}

	// Modify all the fields - 29338_A1
	@Test(priority = 14)
	public void editBusinessOpportunityAllFields() {

		this.businessOpportunityPagePO.editBusinessOpportunityAllFields(BusinessOpportunityData.TITLE9A,
				BusinessOpportunityData.TITLE9B, OrganizationsData.VALID_FANTASY_NAME_5,
				BusinessOpportunityData.EDITED_DESCRIPTION);
		this.commonPage.getSaveButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		Assert.assertTrue(
				this.businessOpportunityPagePO.isBusinesOportunityDisplayedOnTable(BusinessOpportunityData.TITLE9B));
	}

	// Cancel modify operation - 29339_A1
	@Test(priority = 15)
	public void cancelBusinessOpportunityEdition() {

		this.businessOpportunityPagePO.editBusinessOpportunityTitle(BusinessOpportunityData.TITLE10A,
				BusinessOpportunityData.TITLE10B);
		this.commonPage.getBackButton().click();

		Assert.assertTrue(this.businessOpportunityPage.getBusinessOpportunityTable().isDisplayed());
		Assert.assertTrue(
				this.businessOpportunityPagePO.isBusinesOportunityDisplayedOnTable(BusinessOpportunityData.TITLE10A));
	}

	// Empty mandatory fields modified in edition - 29341_A1
	@Test(priority = 16)
	public void editBusinessOpportunityTitleToEmptyField() {

		this.businessOpportunityPagePO.editBusinessOpportunityTitle(BusinessOpportunityData.TITLE10A,
				" " + Keys.BACK_SPACE + Keys.TAB);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.businessOpportunityPage.getTitleFieldErrorLabel().getText(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
	}

	// Empty mandatory fields modified in edition - 29341_A2
	@Test(priority = 17)
	public void editBusinessOpportunityDescriptionToEmptyField() {

		this.businessOpportunityPagePO.editBusinessOpportunityDescription(BusinessOpportunityData.TITLE10A,
				" " + Keys.BACK_SPACE + Keys.TAB);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.businessOpportunityPage.getDescriptionErrorLabel().getText(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
	}

	// ECharacter limit of fields modified in edition - 29341_A1
	@Test(priority = 18)
	public void editBusinessOpportunityBiggerThanAllowedField() {

		this.businessOpportunityPagePO.editBusinessOpportunityTitle(BusinessOpportunityData.TITLE10A,
				BusinessOpportunityData.TITLE_BIGGER + Keys.TAB);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.businessOpportunityPage.getTitleFieldErrorLabel().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
	}

	// Character limit of fields modified in edition - 29341_A2
	@Test(priority = 19)
	public void editBusinessOpportunityBiggerThanAllowedDescriptionToField() {

		this.businessOpportunityPagePO.editBusinessOpportunityDescription(BusinessOpportunityData.TITLE10A,
				BusinessOpportunityData.DESCRIPTION_BIGGER + Keys.TAB);

		Assert.assertFalse(this.commonPage.getSaveButton().isEnabled());
		Assert.assertEquals(this.businessOpportunityPage.getDescriptionErrorLabel().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_5000);
	}

	// Remove business opportunity - 29343_A1
	@Test(priority = 20)
	public void removeBusinessOpportunity() {

		this.businessOpportunityPagePO.removeBusinessOpportunityFromTable(BusinessOpportunityData.TITLE11);

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_REMOVAL);
		Assert.assertFalse(
				this.businessOpportunityPagePO.isBusinesOportunityDisplayedOnTable(BusinessOpportunityData.TITLE11));
	}

	// Cancel Remove business opportunity - 29344_A1
	@Test(priority = 21)
	public void cancelRemoveBusinessOpportunity() {

		this.commonPagePO.goToBusinessOpportunityPage();
		this.businessOpportunityPagePO.fillSearchBusinessOpportunityField(BusinessOpportunityData.TITLE12);
		this.commonPage.getDeleteButton().click();
		this.businessOpportunityPage.getCancelRemovalButton().click();

		Assert.assertTrue(
				this.businessOpportunityPagePO.isBusinesOportunityDisplayedOnTable(BusinessOpportunityData.TITLE12));
		Assert.assertTrue(this.businessOpportunityPage.getBusinessOpportunityTable().isDisplayed());
	}
}
