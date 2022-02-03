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
import br.edu.ufcg.virtus.automation.database.TrackerBoardDatabase;
import br.edu.ufcg.virtus.automation.database.TrackerCardDatabase;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.TrackerBoardData;
import br.edu.ufcg.virtus.automation.dataset.TrackerCardData;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.page.TrackerCardPage;
import br.edu.ufcg.virtus.automation.pagePO.CommonPagePO;
import br.edu.ufcg.virtus.automation.pagePO.LoginPagePO;
import br.edu.ufcg.virtus.automation.pagePO.TrackerBoardPagePO;
import br.edu.ufcg.virtus.automation.pagePO.TrackerCardPagePO;

public class TrackerCardTests {

	private TrackerBoardPagePO trackerBoardPagePO;
	private TrackerCardPagePO trackerCardPagePO;
	private TrackerCardPage trackerCardPage;
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

		TrackerCardDatabase.clearCardsDataOnDatabase();
		TrackerBoardDatabase.clearBoardsDataOnDatabase();
		TrackerBoardDatabase.injectBoardsDataOnDatabase();
		TrackerCardDatabase.injectCardsDataOnDatabase();

		this.seleniumConfig = new SeleniumConfig();
		this.commonPagePO = new CommonPagePO(this.seleniumConfig);
		this.commonPage = this.commonPagePO.commonPage;
		this.trackerCardPagePO = new TrackerCardPagePO(this.seleniumConfig, this.commonPagePO.commonPage);
		this.trackerCardPage = this.trackerCardPagePO.trackerCardPage;
		this.trackerBoardPagePO = new TrackerBoardPagePO(this.seleniumConfig, this.commonPagePO.commonPage);
		this.loginPagePO = new LoginPagePO(this.seleniumConfig);

		this.loginPagePO.loginSuccessfully();
	}

	@AfterClass(alwaysRun = true)
	public void setupAfterTest() throws SQLException, IOException {

		TrackerCardDatabase.clearCardsDataOnDatabase();
		TrackerBoardDatabase.clearBoardsDataOnDatabase();
		TrackerBoardDatabase.closeBoardsDatabaseConnection();

		this.seleniumConfig.getDriver().quit();
	}

	// #26255 - Create card with valid data (no custom fields) - A1
	@Test(priority = 1)
	public void createCardWithValidData() {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_2,
				TrackerCardData.CARD_TITLE_3, TrackerCardData.CARD_DUE_DATE_3, TrackerCardData.CARD_DESCRIPTION_3,
				TrackerCardData.CARD_MEMBER_LIST_1);
		this.trackerCardPage.getCardSaveButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_ADDED);
		Assert.assertTrue(this.trackerBoardPagePO.isCardDisplayedOnTable(TrackerCardData.CARD_TITLE_3));
	}

	// #26256 - Create card with invalid data (no custom fields) - A1
	@Test(priority = 2)
	public void createCardWithBlankedData() {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				TrackerCardData.CARD_TITLE_4, TrackerCardData.CARD_DUE_DATE_3, TrackerCardData.CARD_DESCRIPTION_3);

		Assert.assertEquals(this.trackerCardPage.getCardNameErrorLabel().getText(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertTrue(this.trackerCardPage.getCardNameErrorLabel().isDisplayed());
	}

	// #26256 - Create card with invalid data (no custom fields) - A2
	@Test(priority = 3)
	public void createCardWithBiggerThanAllowedTitle() {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				CommonData.FIELD_255, TrackerCardData.CARD_DUE_DATE_3, TrackerCardData.CARD_DESCRIPTION_3);

		Assert.assertEquals(this.trackerCardPage.getCardNameErrorLabel().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.trackerCardPage.getCardNameErrorLabel().isDisplayed());
	}

	// #26256 - Create card with invalid data (no custom fields) - A3
	@Test(priority = 4)
	public void createCardWithInvalidData() {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				CommonData.INVALID_FIELD, TrackerCardData.CARD_DUE_DATE_3, TrackerCardData.CARD_DESCRIPTION_3);

		Assert.assertEquals(this.trackerCardPage.getCardNameErrorLabel().getText(),
				CommonData.INVALID_FIELD_MESSAGE_ERROR);
		Assert.assertTrue(this.trackerCardPage.getCardNameErrorLabel().isDisplayed());
	}

	// #27429 - Create a valid checklist - A1
	@Test(priority = 5)
	public void createAValidChecklist() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_5);
		this.trackerCardPagePO.insertChecklist(TrackerCardData.CHECKLIST_1);
		this.trackerCardPage.getSaveCheckListButton().click();
		this.trackerCardPage.getCardSaveButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		Assert.assertEquals(this.trackerCardPage.getChecklistsLabels().get(0).getText(), TrackerCardData.CHECKLIST_1);
	}

	// #27437 - Create an invalid checklist - A1
	@Test(priority = 6)
	public void createInvalidChecklistWithoutName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_5);
		this.trackerCardPagePO.insertChecklist(CommonData.EMPTY_VALUE + Keys.TAB);

		Assert.assertEquals(this.trackerCardPage.getChecklistNameFieldError().getText(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.trackerCardPage.getSaveCheckListButton().isEnabled());
	}

	// #27437 - Create an invalid checklist - A2
	@Test(priority = 7)
	public void createInvalidChecklistBiggerThanAllowedName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_5);
		this.trackerCardPagePO.insertChecklist(CommonData.FIELD_255 + Keys.TAB);

		Assert.assertEquals(this.trackerCardPage.getChecklistNameFieldError().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertFalse(this.trackerCardPage.getSaveCheckListButton().isEnabled());
	}

	// #27437 - Create an invalid checklist - A3
	@Test(priority = 8)
	public void createInvalidChecklistWithOnlySpaceName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_5);
		this.trackerCardPagePO.insertChecklist(CommonData.INVALID_FIELD + Keys.TAB);

		Assert.assertEquals(this.trackerCardPage.getChecklistNameFieldError().getText(),
				CommonData.INVALID_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.trackerCardPage.getSaveCheckListButton().isEnabled());
	}

	// #27443 - Create a valid checklist Item - A1
	@Test(priority = 9)
	public void createAValidItemInChecklist() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_6);
		this.trackerCardPagePO.insertChecklist(TrackerCardData.CHECKLIST_1);
		this.trackerCardPage.getSaveCheckListButton().click();

		this.trackerCardPage.getChecklistItemField(TrackerCardData.CHECKLIST_FRONT_ID_1)
				.sendKeys(TrackerCardData.CHECKLIST_ITEM_1);
		this.trackerCardPage.getAddCheckListItemButton().click();

		this.trackerCardPage.getCardSaveButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		Assert.assertTrue(this.trackerCardPagePO.isItemDisplayedOnChecklisTable(TrackerCardData.CHECKLIST_FRONT_ID_1,
				TrackerCardData.CHECKLIST_ITEM_1));
	}

	// #27443 - Create an invalid checklist Item - A1
	@Test(priority = 10)
	public void createAnInvalidItemwithoutName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_7);
		this.trackerCardPagePO.insertChecklist(TrackerCardData.CHECKLIST_1);
		this.trackerCardPage.getSaveCheckListButton().click();

		this.trackerCardPage.getChecklistItemField(TrackerCardData.CHECKLIST_FRONT_ID_1).click();
		this.trackerCardPage.getChecklistItemField(TrackerCardData.CHECKLIST_FRONT_ID_1)
				.sendKeys(CommonData.EMPTY_VALUE + Keys.TAB);

		Assert.assertEquals(
				this.trackerCardPage.getChecklistItemNameError(TrackerCardData.CHECKLIST_FRONT_ID_1).getText(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.trackerCardPage.getAddCheckListItemButton().isEnabled());
	}

	// #27443 - Create an invalid checklist Item - A2
	@Test(priority = 11)
	public void createAnInvalidItemBiggerThanAllowedName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_7);
		this.trackerCardPagePO.insertChecklist(TrackerCardData.CHECKLIST_1);
		this.trackerCardPage.getSaveCheckListButton().click();

		this.trackerCardPage.getChecklistItemField(TrackerCardData.CHECKLIST_FRONT_ID_1).click();
		this.trackerCardPage.getChecklistItemField(TrackerCardData.CHECKLIST_FRONT_ID_1)
				.sendKeys(CommonData.FIELD_255 + Keys.TAB);

		Assert.assertEquals(
				this.trackerCardPage.getChecklistItemNameError(TrackerCardData.CHECKLIST_FRONT_ID_1).getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertFalse(this.trackerCardPage.getAddCheckListItemButton().isEnabled());
	}

	// #27443 - Create an invalid checklist Item - A3
	@Test(priority = 12)
	public void createAnInvalidItemBlankedName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_7);
		this.trackerCardPagePO.insertChecklist(TrackerCardData.CHECKLIST_1);
		this.trackerCardPage.getSaveCheckListButton().click();

		this.trackerCardPage.getChecklistItemField(TrackerCardData.CHECKLIST_FRONT_ID_1).click();
		this.trackerCardPage.getChecklistItemField(TrackerCardData.CHECKLIST_FRONT_ID_1)
				.sendKeys(CommonData.INVALID_FIELD + Keys.TAB);

		Assert.assertEquals(
				this.trackerCardPage.getChecklistItemNameError(TrackerCardData.CHECKLIST_FRONT_ID_1).getText(),
				CommonData.INVALID_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.trackerCardPage.getAddCheckListItemButton().isEnabled());
	}

	// #29396 - Create card with one or more than one Member - A2
	@Test(priority = 13)
	public void createCardWithOneMember() {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_3,
				TrackerCardData.CARD_TITLE_8, TrackerCardData.CARD_DUE_DATE_8, TrackerCardData.CARD_DESCRIPTION_8,
				TrackerCardData.CARD_MEMBER_LIST_1);
		this.trackerCardPage.getCardSaveButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_ADDED);
		String member1 = TrackerCardData.CARD_MEMBER_LIST_1.get(0);
		Assert.assertTrue(this.trackerBoardPagePO.isCardDisplayedByMember(TrackerCardData.CARD_TITLE_8, member1));
	}

	// #29396 - Create card with one or more than one Member - A1
	@Test(priority = 14)
	public void createCardWithMoreThanOneMember() {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_3,
				TrackerCardData.CARD_TITLE_9, TrackerCardData.CARD_DUE_DATE_9, TrackerCardData.CARD_DESCRIPTION_9,
				TrackerCardData.CARD_MEMBER_LIST_2);
		this.trackerCardPage.getCardSaveButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_ADDED);
		Assert.assertTrue(this.trackerBoardPagePO.isCardDisplayedByMember(TrackerCardData.CARD_TITLE_9,
				TrackerCardData.CARD_MEMBER_LIST_2.get(0)));
		Assert.assertTrue(this.trackerBoardPagePO.isCardDisplayedByMember(TrackerCardData.CARD_TITLE_9,
				TrackerCardData.CARD_MEMBER_LIST_2.get(1)));
	}

	// #36952 - Create and continue with valid data - A1
	@Test(priority = 15)
	public void createCardAndContinueWithValidData() {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_2,
				TrackerCardData.CARD_TITLE_10, TrackerCardData.CARD_DUE_DATE_10, TrackerCardData.CARD_DESCRIPTION_10);
		this.trackerCardPage.getCardSaveAndContinueButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_ADDED);
		Assert.assertFalse(this.trackerCardPage.getCardSaveAndContinueButton().isEnabled());
		Assert.assertFalse(this.trackerCardPage.getCardSaveButton().isEnabled());
	}

	// #36953 - Create card and continue with invalid data (no custom fields) - A1
	@Test(priority = 16)
	public void createCardAndContinueWithBlankedData() {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				TrackerCardData.CARD_TITLE_11, TrackerCardData.CARD_DUE_DATE_11, TrackerCardData.CARD_DESCRIPTION_11);

		Assert.assertEquals(this.trackerCardPage.getCardNameErrorLabel().getText(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.trackerCardPage.getCardSaveAndContinueButton().isEnabled());
	}

	// #36953 - Create card and continue with invalid data (no custom fields) - A2
	@Test(priority = 17)
	public void createCardAndContinueWithBiggerThanAllowedTitle() {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				CommonData.FIELD_255, TrackerCardData.CARD_DUE_DATE_11, TrackerCardData.CARD_DESCRIPTION_11);

		Assert.assertEquals(this.trackerCardPage.getCardNameErrorLabel().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertFalse(this.trackerCardPage.getCardSaveAndContinueButton().isEnabled());
	}

	// #36953 - Create card and continue with invalid data (no custom fields) - A3
	@Test(priority = 18)
	public void createCardAndContinueWithInvalidData() {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				CommonData.INVALID_FIELD, TrackerCardData.CARD_DUE_DATE_11, TrackerCardData.CARD_DESCRIPTION_11);

		Assert.assertEquals(this.trackerCardPage.getCardNameErrorLabel().getText(),
				CommonData.INVALID_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.trackerCardPage.getCardSaveAndContinueButton().isEnabled());
	}

	// #26257 - Edit card with valid data (no custom fields) - A1
	@Test(priority = 19)
	public void editCardWithValidData() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_12);
		this.trackerCardPagePO.fillCardName(TrackerCardData.CARD_TITLE_EDITED_12);
		this.trackerCardPage.getCardSaveButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		Assert.assertEquals(this.trackerCardPage.getCardName().getText(), TrackerCardData.CARD_TITLE_EDITED_12);
	}

	// #26258 - Edit card with invalid data (no custom fields) - A1
	@Test(priority = 20)
	public void editCardWithWithBlankedData() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_13);
		this.trackerCardPagePO.fillCardName(" " + Keys.BACK_SPACE + Keys.TAB);

		Assert.assertEquals(this.trackerCardPage.getCardNameErrorLabel().getText(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertTrue(this.trackerCardPage.getCardNameErrorLabel().isDisplayed());
	}

	// #26258 - Edit card with invalid data (no custom fields) - A2
	@Test(priority = 21)
	public void editCardWithWithInvalidData() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_13);
		this.trackerCardPagePO.fillCardName(CommonData.INVALID_FIELD + Keys.TAB);

		Assert.assertEquals(this.trackerCardPage.getCardNameErrorLabel().getText(),
				CommonData.INVALID_FIELD_MESSAGE_ERROR);
		Assert.assertTrue(this.trackerCardPage.getCardNameErrorLabel().isDisplayed());
	}

	// #26258 - Edit card with invalid data (no custom fields) - A3
	@Test(priority = 22)
	public void editCardWithWithBiggerThanAllowedName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_13);
		this.trackerCardPagePO.fillCardName(CommonData.FIELD_255 + Keys.TAB);

		Assert.assertEquals(this.trackerCardPage.getCardNameErrorLabel().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertTrue(this.trackerCardPage.getCardNameErrorLabel().isDisplayed());
	}

	// #27431 - Edit a valid checklist - A1
	@Test(priority = 23)
	public void editAValidCheckList() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_14);
		this.trackerCardPagePO.editChecklist(TrackerCardData.CHECKLIST_1, TrackerCardData.CHECKLIST_2);

		this.trackerCardPage.getSaveCheckListButton().click();
		this.trackerCardPage.getCardSaveButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		Assert.assertEquals(this.trackerCardPage.getChecklistsLabels().get(0).getText(), TrackerCardData.CHECKLIST_2);
	}

	// #27438 - Edit an invalid checklist - A3
	@Test(priority = 24)
	public void editAnInvalidChecklistWithoutName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_15);
		this.trackerCardPagePO.editChecklist(TrackerCardData.CHECKLIST_3, CommonData.INVALID_FIELD + Keys.BACK_SPACE);
		this.trackerCardPage.getChecklistNameField().sendKeys(Keys.BACK_SPACE);

		Assert.assertEquals(this.trackerCardPage.getChecklistNameFieldError().getText(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.trackerCardPage.getAddCheckListItemButton().isEnabled());
	}

	// #27438 - Edit an invalid checklist - A2
	@Test(priority = 25)
	public void editAnInvalidChecklistBiggerThanAllowedName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_15);
		this.trackerCardPagePO.editChecklist(TrackerCardData.CHECKLIST_3, CommonData.FIELD_255);

		Assert.assertEquals(this.trackerCardPage.getChecklistNameFieldError().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertFalse(this.trackerCardPage.getAddCheckListItemButton().isEnabled());
	}

	// #27438 - Edit an invalid checklist - A1
	@Test(priority = 26)
	public void editAnInvalidChecklistBlankedName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_15);
		this.trackerCardPagePO.editChecklist(TrackerCardData.CHECKLIST_3, CommonData.INVALID_FIELD);

		Assert.assertEquals(this.trackerCardPage.getChecklistNameFieldError().getText(),
				CommonData.INVALID_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.trackerCardPage.getAddCheckListItemButton().isEnabled());
	}

	// #27447 - Edit a valid checklist Item - A1
	@Test(priority = 27)
	public void editAValidItemInCheckList() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_16);
		this.trackerCardPagePO.editChecklistItem(TrackerCardData.CHECKLIST_4, TrackerCardData.CHECKLIST_ITEM_2,
				TrackerCardData.CHECKLIST_ITEM_1);
		this.trackerCardPage.getAddCheckListItemButton().click();
		this.trackerCardPage.getCardSaveButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		Assert.assertTrue(this.trackerCardPagePO.isItemDisplayedOnChecklisTable(TrackerCardData.CHECKLIST_FRONT_ID_1,
				TrackerCardData.CHECKLIST_ITEM_1));
	}

	// #27438 - Edit an invalid checklist Item - A3
	@Test(priority = 28)
	public void editAnInvalidItemWithoutName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_17);
		this.trackerCardPagePO.editChecklistItem(TrackerCardData.CHECKLIST_5, TrackerCardData.CHECKLIST_ITEM_2,
				CommonData.INVALID_FIELD + Keys.BACK_SPACE.toString() + Keys.BACK_SPACE.toString());

		Assert.assertEquals(
				this.trackerCardPage.getChecklistItemNameError(TrackerCardData.CHECKLIST_FRONT_ID_1).getText(),
				CommonData.MANDATORY_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.trackerCardPage.getAddCheckListItemButton().isEnabled());
	}

	// #27438 - Edit an invalid checklist Item - A2
	@Test(priority = 29)
	public void editAnInvalidItemBiggerThanAllowedName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_17);
		this.trackerCardPagePO.editChecklistItem(TrackerCardData.CHECKLIST_5, TrackerCardData.CHECKLIST_ITEM_2,
				CommonData.FIELD_255);

		Assert.assertEquals(
				this.trackerCardPage.getChecklistItemNameError(TrackerCardData.CHECKLIST_FRONT_ID_1).getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertFalse(this.trackerCardPage.getAddCheckListItemButton().isEnabled());
	}

	// #27438 - Edit an invalid checklist Item - A1
	@Test(priority = 30)
	public void editAnInvalidItemBlankedName() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_17);
		this.trackerCardPagePO.editChecklistItem(TrackerCardData.CHECKLIST_5, TrackerCardData.CHECKLIST_ITEM_2,
				CommonData.INVALID_FIELD);

		Assert.assertEquals(
				this.trackerCardPage.getChecklistItemNameError(TrackerCardData.CHECKLIST_FRONT_ID_1).getText(),
				CommonData.INVALID_FIELD_MESSAGE_ERROR);
		Assert.assertFalse(this.trackerCardPage.getAddCheckListItemButton().isEnabled());
	}

	// #26259 - Remove card successfully - A1
	@Test(priority = 31)
	public void removeCardSuccessfully() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_18);
		this.trackerCardPage.getDeleteCardButton().click();
		this.trackerCardPage.getCardConfirmRemovalButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_REMOVAL);
		Assert.assertFalse(this.trackerBoardPagePO.isCardDisplayedOnTable(TrackerCardData.CARD_ID_18));
	}

	// #37841 - Cancel remotion of card successfully - A1
	@Test(priority = 32)
	public void cancelRemotionOfCardSuccessfully() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_19);
		this.trackerCardPage.getDeleteCardButton().click();
		this.trackerCardPage.getCardCancelRemovalButton().click();

		Assert.assertTrue(this.trackerCardPage.getCardSaveButton().isEnabled());
		this.trackerCardPage.getCardBackButton().click();
		this.trackerCardPage.getCardBackButton().click();
		Assert.assertFalse(this.trackerBoardPagePO.isCardDisplayedOnTable(TrackerCardData.CARD_ID_19));
	}

	// #27433 - Remove a Checklist from card successfully - A1
	@Test(priority = 33)
	public void removeChecklistSuccessfully() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_20);
		this.trackerCardPagePO.deleteChecklist(TrackerCardData.CHECKLIST_6);
		this.trackerCardPage.getChecklistConfirmRemovalButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_OPERATION);
		Assert.assertFalse(this.trackerCardPagePO.isChecklistDisplayedInEdition(TrackerCardData.CHECKLIST_6));
	}

	// #37843 - Cancel Remove of Checklist from card - A1
	@Test(priority = 34)
	public void cancelRemovalOfChecklist() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_21);
		this.trackerCardPagePO.deleteChecklist(TrackerCardData.CHECKLIST_7);
		this.trackerCardPage.getChecklistCancelRemovalButton().click();

		Assert.assertTrue(this.trackerCardPage.getCardSaveButton().isEnabled());
		Assert.assertTrue(this.trackerCardPagePO.isChecklistDisplayedInEdition(TrackerCardData.CHECKLIST_7));
	}

	// #27450 - Remove a Item from Checklist successfully - A1
	@Test(priority = 35)
	public void removeItemFromChecklistSuccessfully() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_22);
		this.trackerCardPagePO.deleteChecklistItem(TrackerCardData.CHECKLIST_8, TrackerCardData.CHECKLIST_ITEM_4);
		this.trackerCardPage.getChecklistItemConfirmRemovalButton().click();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_OPERATION);
		this.trackerCardPage.getCardSaveButton().click();
		Assert.assertFalse(this.trackerCardPagePO.isItemDisplayedOnChecklisTable(TrackerCardData.CHECKLIST_FRONT_ID_1,
				TrackerCardData.CHECKLIST_ITEM_4));
	}

	// #37844 - Cancel removal from Item from Checklist successfully - A1
	@Test(priority = 36)
	public void cancelRemoveItemFromChecklistSuccessfully() {
		this.trackerCardPagePO.goToCardEdition(TrackerBoardData.BOARD_ID_1, TrackerCardData.CARD_ID_23);
		this.trackerCardPagePO.deleteChecklistItem(TrackerCardData.CHECKLIST_9, TrackerCardData.CHECKLIST_ITEM_5);
		this.trackerCardPage.getChecklistItemCancelRemovalButton().click();

		Assert.assertTrue(this.trackerCardPage.getCardSaveButton().isEnabled());
		Assert.assertTrue(this.trackerCardPagePO.isItemDisplayedOnChecklisTable(TrackerCardData.CHECKLIST_FRONT_ID_1,
				TrackerCardData.CHECKLIST_ITEM_5));
	}

	// #36956 - Auto-save user changes on card creation form - A1
	@Test(priority = 37)
	public void autoSaveSuccessfully() throws Throwable {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				TrackerCardData.CARD_TITLE_24, TrackerCardData.CARD_DUE_DATE_24,
				TrackerCardData.CARD_DESCRIPTION_24 + Keys.TAB);
		Thread.sleep(5000); // sleep used to wait for the draft to be created

		this.commonPagePO.goToBoard(TrackerBoardData.BOARD_ID_1);
		this.trackerBoardPagePO.clickAddCardButton(TrackerBoardData.STATUS_ID_1);
		this.trackerCardPage.getYesButtonRecoveryOnCard().click();

		Assert.assertEquals(this.trackerCardPage.getCardNameField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				TrackerCardData.CARD_TITLE_24);
		Assert.assertEquals(this.trackerCardPage.getCardDueDateField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				TrackerCardData.CARD_DUE_DATE_24);
		Assert.assertEquals(this.trackerCardPage.getCardDescriptionField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				TrackerCardData.CARD_DESCRIPTION_24);

		this.trackerCardPage.getCardBackButton().click();
		this.trackerCardPage.getYesButtonRecoveryOnCard().click();
	}

	// #36956 - Auto-save user changes on card creation form - A2
	@Test(priority = 38)
	public void autoSaveCanceled() throws Throwable {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				TrackerCardData.CARD_TITLE_24, TrackerCardData.CARD_DUE_DATE_24,
				TrackerCardData.CARD_DESCRIPTION_24 + Keys.TAB);
		Thread.sleep(5000); // sleep used to wait for the draft to be created

		this.commonPagePO.goToBoard(TrackerBoardData.BOARD_ID_1);
		this.trackerBoardPagePO.clickAddCardButton(TrackerBoardData.STATUS_ID_1);
		this.trackerCardPage.getNoButtonRecoveryOnCard().click();

		Assert.assertEquals(this.trackerCardPage.getCardNameField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerCardPage.getCardDueDateField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerCardPage.getCardDescriptionField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
	}

	// #36958 - Discard draft when creating a card with success - A1
	@Test(priority = 39)
	public void discardSaveWhenCardIsCreated() throws Throwable {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				TrackerCardData.CARD_TITLE_24, TrackerCardData.CARD_DUE_DATE_24, TrackerCardData.CARD_DESCRIPTION_24);
		this.trackerCardPage.getCardSaveButton().click();
		Thread.sleep(5000); // sleep used to wait for the draft to be created
		this.trackerBoardPagePO.clickAddCardButton(TrackerBoardData.STATUS_ID_1);

		Assert.assertEquals(this.trackerCardPage.getCardNameField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerCardPage.getCardDueDateField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerCardPage.getCardDescriptionField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
	}

	// #36959 - Save draft when a card is not successfully created. - A1
	@Test(priority = 40)
	public void autoSaveWhenDataIsInvalid() throws Throwable {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				CommonData.INVALID_FIELD, TrackerCardData.CARD_DUE_DATE_25, TrackerCardData.CARD_DESCRIPTION_25);
		Thread.sleep(5000); // sleep used to wait for the draft to be created

		this.commonPagePO.goToBoard(TrackerBoardData.BOARD_ID_1);
		this.trackerBoardPagePO.clickAddCardButton(TrackerBoardData.STATUS_ID_1);
		this.trackerCardPage.getYesButtonRecoveryOnCard().click();

		Assert.assertEquals(this.trackerCardPage.getCardNameField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.INVALID_FIELD);
		Assert.assertEquals(this.trackerCardPage.getCardDueDateField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				TrackerCardData.CARD_DUE_DATE_25);
		Assert.assertEquals(this.trackerCardPage.getCardDescriptionField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				TrackerCardData.CARD_DESCRIPTION_25);

		this.trackerCardPage.getCardBackButton().click();
		this.trackerCardPage.getYesButtonRecoveryOnCard().click();
	}

	// #36960 - Canceling card creation Draft confirmation - A1
	@Test(priority = 41)
	public void cancelingDraftCreation() throws Throwable {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				TrackerCardData.CARD_TITLE_25, TrackerCardData.CARD_DUE_DATE_25, TrackerCardData.CARD_DESCRIPTION_25);
		Thread.sleep(5000); // sleep used to wait for the draft to be created
		this.trackerCardPage.getCardBackButton().click();
		this.trackerCardPage.getYesButtonRecoveryOnCard().click();

		this.trackerBoardPagePO.clickAddCardButton(TrackerBoardData.STATUS_ID_1);

		Assert.assertEquals(this.trackerCardPage.getCardNameField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerCardPage.getCardDueDateField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerCardPage.getCardDescriptionField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
	}

	// #36960 - Canceling card creation Draft confirmation - A2
	@Test(priority = 42)
	public void givingUpCancelingDraftCreation() throws Throwable {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				TrackerCardData.CARD_TITLE_25, TrackerCardData.CARD_DUE_DATE_25, TrackerCardData.CARD_DESCRIPTION_25);
		Thread.sleep(5000); // sleep used to wait for the draft to be created
		this.trackerCardPage.getCardBackButton().click();
		this.trackerCardPage.getNoButtonRecoveryOnCard().click();

		this.trackerBoardPagePO.clickAddCardButton(TrackerBoardData.STATUS_ID_1);
		this.trackerCardPage.getYesButtonRecoveryOnCard().click();

		Assert.assertEquals(this.trackerCardPage.getCardNameField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				TrackerCardData.CARD_TITLE_25);
		Assert.assertEquals(this.trackerCardPage.getCardDueDateField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				TrackerCardData.CARD_DUE_DATE_25);
		Assert.assertEquals(this.trackerCardPage.getCardDescriptionField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				TrackerCardData.CARD_DESCRIPTION_25);

		this.trackerCardPage.getCardBackButton().click();
		this.trackerCardPage.getYesButtonRecoveryOnCard().click();
	}

	// #36968 - Card creation on another board should not show draft recovery
	// message - A1
	@Test(priority = 43)
	public void notAutoSaveOnAnotherBoard() throws Throwable {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				TrackerCardData.CARD_TITLE_25, TrackerCardData.CARD_DUE_DATE_25,
				TrackerCardData.CARD_DESCRIPTION_25 + Keys.TAB);
		Thread.sleep(5000); // sleep used to wait for the draft to be created

		this.commonPagePO.goToBoard(TrackerBoardData.BOARD_ID_12);
		this.trackerBoardPagePO.clickAddCardButton(TrackerBoardData.STATUS_ID_25);

		Assert.assertEquals(this.trackerCardPage.getCardNameField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerCardPage.getCardDueDateField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerCardPage.getCardDescriptionField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);

		this.commonPagePO.goToBoard(TrackerBoardData.BOARD_ID_1);
		this.trackerBoardPagePO.clickAddCardButton(TrackerBoardData.STATUS_ID_1);
		this.trackerCardPage.getNoButtonRecoveryOnCard().click();
	}

	// #36968 - A new draft on a different board should discard an older one. - A1
	@Test(priority = 44)
	public void replaceAutoSaveOnAnotherBoard() throws Throwable {
		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_1, TrackerBoardData.STATUS_ID_1,
				TrackerCardData.CARD_TITLE_25, TrackerCardData.CARD_DUE_DATE_25,
				TrackerCardData.CARD_DESCRIPTION_25 + Keys.TAB);
		Thread.sleep(5000); // sleep used to wait for the draft to be created

		this.trackerCardPagePO.insertCard(TrackerBoardData.BOARD_NAME_12, TrackerBoardData.STATUS_ID_25,
				TrackerCardData.CARD_TITLE_26, TrackerCardData.CARD_DUE_DATE_26,
				TrackerCardData.CARD_DESCRIPTION_26 + Keys.TAB);
		Thread.sleep(5000); // sleep used to wait for the draft to be created

		this.commonPagePO.goToBoard(TrackerBoardData.BOARD_ID_1);
		this.trackerBoardPagePO.clickAddCardButton(TrackerBoardData.STATUS_ID_1);

		Assert.assertEquals(this.trackerCardPage.getCardNameField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerCardPage.getCardDueDateField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerCardPage.getCardDescriptionField().getAttribute(CommonData.ATTRIBUTE_VALUE),
				CommonData.EMPTY_VALUE);
	}

}
