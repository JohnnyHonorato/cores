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
import br.edu.ufcg.virtus.automation.database.TrackerBoardDatabase;
import br.edu.ufcg.virtus.automation.database.TrackerCardDatabase;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.TrackerBoardData;
import br.edu.ufcg.virtus.automation.dataset.TrackerCardData;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.page.TrackerBoardPage;
import br.edu.ufcg.virtus.automation.page.TrackerCardPage;
import br.edu.ufcg.virtus.automation.pagePO.CommonPagePO;
import br.edu.ufcg.virtus.automation.pagePO.LoginPagePO;
import br.edu.ufcg.virtus.automation.pagePO.TrackerBoardPagePO;
import br.edu.ufcg.virtus.automation.pagePO.TrackerCardPagePO;

public class TrackerBoardTests {

	private TrackerBoardPagePO trackerBoardPagePO;
	private TrackerBoardPage trackerBoardPage;
	private TrackerCardPagePO trackerCardPagePO;
	private TrackerCardPage trackerCardPage;
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

		TrackerCardDatabase.clearCardsDataOnDatabase();
		TrackerBoardDatabase.clearBoardsDataOnDatabase();
		TrackerBoardDatabase.injectBoardsDataOnDatabase();
		TrackerCardDatabase.injectCardsDataOnDatabase();

		this.seleniumConfig = new SeleniumConfig();
		this.commonPagePO = new CommonPagePO(this.seleniumConfig);
		this.commonPage = new CommonPage(this.seleniumConfig);
		this.trackerCardPagePO = new TrackerCardPagePO(this.seleniumConfig, this.commonPagePO.commonPage);
		this.trackerCardPage = this.trackerCardPagePO.trackerCardPage;
		this.trackerBoardPagePO = new TrackerBoardPagePO(this.seleniumConfig, this.commonPagePO.commonPage);
		this.trackerBoardPage = this.trackerBoardPagePO.trackerBoardPage;
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

	// #26261 - Custom fields created should be displayed on the card - A1
	@Test(priority = 1)
	public void customFieldsDisplayedOnBoard() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.openBoard(TrackerBoardData.BOARD_NAME_1);
		this.trackerBoardPagePO.openCard(TrackerCardData.CARD_ELEMENT_1);

		Assert.assertEquals(
				this.trackerCardPage.getCardAttributeName(TrackerBoardData.ATT_INTEGER_FRONT_ID_1).getText(),
				TrackerBoardData.ATT_INTEGER_TITLE_1);
		Assert.assertEquals(
				this.trackerCardPage.getCardAttributeValue(TrackerBoardData.ATT_INTEGER_FRONT_ID_1).getText(),
				TrackerCardData.ATTRIBUTE_VALUE_1);
	}

	// #26275 - Display list of standard fields - A1
	@Test(priority = 2)
	public void displayBoardStandardFields() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPage.getBoardEdit(TrackerBoardData.BOARD_POSITION_1).click();
		this.trackerBoardPagePO.clickOnStepOneNextButton();

		Assert.assertTrue(this.trackerBoardPage.getBoardAttLabelName().isDisplayed());
		Assert.assertTrue(this.trackerBoardPage.getBoardAttLabelDescription().isDisplayed());
		Assert.assertTrue(this.trackerBoardPage.getBoardAttLabelDueDate().isDisplayed());
	}

	// #30609 - Empty non-mandatory custom field view - A1
	@Test(priority = 3)
	public void emptyCustomFieldView() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.openBoard(TrackerBoardData.BOARD_NAME_1);
		this.trackerBoardPagePO.openCard(TrackerCardData.CARD_ELEMENT_2);

		Assert.assertEquals(
				this.trackerCardPage.getCardAttributeName(TrackerBoardData.ATT_INTEGER_FRONT_ID_1).getText(),
				TrackerBoardData.ATT_INTEGER_TITLE_1);
		Assert.assertEquals(
				this.trackerCardPage.getCardAttributeEmpty(TrackerBoardData.ATT_INTEGER_FRONT_ID_1).getText(),
				TrackerCardData.ATTRIBUTE_VALUE_2);
	}

	// #26268 - Create a board with valid data - A1
	@Test(priority = 4)
	public void createBoardWithValidData() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickOnCreateBoardButton();
		this.trackerBoardPagePO.fillBoardNameInput(TrackerBoardData.BOARD_NAME_2);
		this.trackerBoardPagePO.fillBoardDescriptionInput(TrackerBoardData.BOARD_DESCRIPTION_2);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		this.trackerBoardPagePO.clickOnAddStatusButton();
		this.trackerBoardPagePO.fillStatusNameInput(TrackerBoardData.TODO);
		this.trackerBoardPagePO.clickOnStatusModalSaveButton();
		this.trackerBoardPagePO.clickOnAddStatusButton();
		this.trackerBoardPagePO.fillStatusNameInput(TrackerBoardData.ON_GOING);
		this.trackerBoardPagePO.clickOnStatusModalSaveButton();
		this.trackerBoardPagePO.clickOnStepThreeNextButton();
		this.trackerBoardPagePO.clickOnBoardSaveButton();

		Assert.assertEquals(this.commonPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_ADDED);
		Assert.assertTrue(this.trackerBoardPagePO.isBoardNameLabelDisplayed());
	}

	// #26269 - Create a board with invalid data - A1
	@Test(priority = 5)
	public void createBoardWithBlankName() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickOnCreateBoardButton();
		this.trackerBoardPagePO.fillBoardNameInput(TrackerBoardData.BLANK_INPUT);
		this.trackerBoardPagePO.fillBoardDescriptionInput(TrackerBoardData.BOARD_DESCRIPTION_2);

		Assert.assertFalse(
				this.trackerBoardPagePO.isBoardEditionNextButtonClickable(TrackerBoardData.FIRST_NEXT_BUTTON));
		Assert.assertTrue(this.trackerBoardPagePO.isInvalidNameLabelDisplayed());
		Assert.assertEquals(this.trackerBoardPage.getInvalidNameLabelDisplayed().getText(),
				CommonData.INVALID_FIELD_MESSAGE_ERROR);
	}

	// #26305 - Can't create board with only one status - A1
	@Test(priority = 6)
	public void createBoardWithOnlyOneStatus() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickOnCreateBoardButton();
		this.trackerBoardPagePO.fillBoardNameInput(TrackerBoardData.BOARD_NAME_2);
		this.trackerBoardPagePO.fillBoardDescriptionInput(TrackerBoardData.BOARD_DESCRIPTION_2);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		this.trackerBoardPagePO.clickOnAddStatusButton();
		this.trackerBoardPagePO.fillStatusNameInput(TrackerBoardData.TODO);
		this.trackerBoardPagePO.clickOnStatusModalSaveButton();

		Assert.assertFalse(this.trackerBoardPagePO.isBoardThirdNextButtonClickable());
		Assert.assertFalse(this.trackerBoardPagePO.isCardDataLabelDisplayed());
	}

	// #26273 - Create more than one board with the same name - A1
	@Test(priority = 7)
	public void createBoardWithSameName() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickOnCreateBoardButton();
		this.trackerBoardPagePO.fillBoardNameInput(TrackerBoardData.BOARD_NAME_1);
		this.trackerBoardPagePO.fillBoardDescriptionInput(TrackerBoardData.BOARD_DESCRIPTION_1);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		this.trackerBoardPagePO.clickOnAddStatusButton();
		this.trackerBoardPagePO.fillStatusNameInput(TrackerBoardData.TODO);
		this.trackerBoardPagePO.clickOnStatusModalSaveButton();
		this.trackerBoardPagePO.clickOnAddStatusButton();
		this.trackerBoardPagePO.fillStatusNameInput(TrackerBoardData.ON_GOING);
		this.trackerBoardPagePO.clickOnStatusModalSaveButton();
		this.trackerBoardPagePO.clickOnStepThreeNextButton();
		this.trackerBoardPagePO.clickOnBoardSaveButton();

		Assert.assertEquals(this.commonPagePO.getToastMsg(), TrackerBoardData.SAME_NAME_MESSAGE);
		Assert.assertTrue(this.trackerBoardPagePO.isCardDataLabelDisplayed());
	}

	// #37147 - Character limit of fields - A1
	@Test(priority = 8)
	public void boardNameValidSize() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickOnCreateBoardButton();
		this.trackerBoardPagePO.fillBoardNameInput(TrackerBoardData.BIG_INPUT_255_CHARACTERS);
		this.trackerBoardPagePO.fillBoardDescriptionInput(TrackerBoardData.BOARD_DESCRIPTION_1);

		Assert.assertFalse(this.trackerBoardPagePO.isInputBoardNameLabelDisplayed());
		Assert.assertEquals(this.trackerBoardPagePO.getInputBoardNameFieldValue(),
				TrackerBoardData.BIG_INPUT_255_CHARACTERS);
		Assert.assertTrue(
				this.trackerBoardPagePO.isBoardEditionNextButtonClickable(TrackerBoardData.FIRST_NEXT_BUTTON));
	}

	// #37147 - Character limit of fields - A2
	@Test(priority = 9)
	public void boardNameInvalidSize() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickOnCreateBoardButton();
		this.trackerBoardPagePO.fillBoardNameInput(TrackerBoardData.BIG_INPUT_256_CHARACTERS);
		this.trackerBoardPagePO.fillBoardDescriptionInput(TrackerBoardData.BOARD_DESCRIPTION_1);

		Assert.assertTrue(this.trackerBoardPagePO.isInputBoardNameLabelDisplayed());
		Assert.assertEquals(this.trackerBoardPagePO.getInputBoardNameFieldValue(),
				TrackerBoardData.BIG_INPUT_256_CHARACTERS);
		Assert.assertFalse(
				this.trackerBoardPagePO.isBoardEditionNextButtonClickable(TrackerBoardData.FIRST_NEXT_BUTTON));
	}

	// #37147 - Character limit of fields - A3
	@Test(priority = 10)
	public void boardDescriptionValidSize() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickOnCreateBoardButton();
		this.trackerBoardPagePO.fillBoardDescriptionInput(TrackerBoardData.BIG_INPUT_5000_CHARACTERS);
		this.trackerBoardPagePO.fillBoardNameInput(TrackerBoardData.BOARD_NAME_1);

		Assert.assertFalse(this.trackerBoardPagePO.isInputBoardDescriptionLabelDisplayed());
		Assert.assertEquals(this.trackerBoardPagePO.getInputBoardDescriptionFieldValue(),
				TrackerBoardData.BIG_INPUT_5000_CHARACTERS);
		Assert.assertTrue(
				this.trackerBoardPagePO.isBoardEditionNextButtonClickable(TrackerBoardData.FIRST_NEXT_BUTTON));
	}

	// #37147 - Character limit of fields - A4
	@Test(priority = 11)
	public void boardDescriptionInvalidSize() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickOnCreateBoardButton();
		this.trackerBoardPagePO.fillBoardDescriptionInput(TrackerBoardData.BIG_INPUT_5001_CHARACTERS);
		this.trackerBoardPagePO.fillBoardNameInput(TrackerBoardData.BOARD_NAME_1);

		Assert.assertTrue(this.trackerBoardPagePO.isInputBoardDescriptionLabelDisplayed());
		Assert.assertEquals(this.trackerBoardPagePO.getInputBoardDescriptionFieldValue(),
				TrackerBoardData.BIG_INPUT_5001_CHARACTERS);
		Assert.assertFalse(
				this.trackerBoardPagePO.isBoardEditionNextButtonClickable(TrackerBoardData.FIRST_NEXT_BUTTON));
	}

	// #37147 - Character limit of fields - A5
	@Test(priority = 12)
	public void boardStatusValidSize() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickOnCreateBoardButton();
		this.trackerBoardPagePO.fillBoardNameInput(TrackerBoardData.BOARD_NAME_1);
		this.trackerBoardPagePO.fillBoardDescriptionInput(TrackerBoardData.BOARD_DESCRIPTION_1);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		this.trackerBoardPagePO.clickOnAddStatusButton();
		this.trackerBoardPagePO.fillStatusNameInput(TrackerBoardData.BIG_INPUT_255_CHARACTERS);

		Assert.assertFalse(this.trackerBoardPagePO.isInputBoardStatusLabelDisplayed());
		Assert.assertEquals(this.trackerBoardPagePO.getInputBoardStatusFieldValue(),
				TrackerBoardData.BIG_INPUT_255_CHARACTERS);
		Assert.assertTrue(this.trackerBoardPagePO.isStatusSaveButtonClickable());
	}

	// #37147 - Character limit of fields - A5
	@Test(priority = 13)
	public void boardStatusInvalidSize() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickOnCreateBoardButton();
		this.trackerBoardPagePO.fillBoardNameInput(TrackerBoardData.BOARD_NAME_1);
		this.trackerBoardPagePO.fillBoardDescriptionInput(TrackerBoardData.BOARD_DESCRIPTION_1);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		this.trackerBoardPagePO.clickOnAddStatusButton();
		this.trackerBoardPagePO.fillStatusNameInput(TrackerBoardData.BIG_INPUT_256_CHARACTERS);
		this.trackerBoardPagePO.fillStatusNameInput(TrackerBoardData.TAB);

		Assert.assertTrue(this.trackerBoardPagePO.isInputBoardStatusLabelDisplayed());
		Assert.assertEquals(this.trackerBoardPage.getInputBoardStatusLabel().getText(),
				CommonData.ERROR_MSG_BIGGER_THAN_255);
		Assert.assertFalse(this.trackerBoardPagePO.isStatusSaveButtonClickable());
	}

	// #26270 - Edit a board with valid data - A1
	@Test(priority = 14)
	public void editBoardWithValidName() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickToEditBoard(TrackerBoardData.BOARD_NAME_11);
		this.trackerBoardPage.getBoardNameInput().clear();
		this.trackerBoardPagePO.fillBoardNameInput(TrackerBoardData.BOARD_NAME_4);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		this.trackerBoardPagePO.clickOnStepThreeNextButton();
		this.trackerBoardPagePO.clickOnBoardSaveButton();

		Assert.assertEquals(this.commonPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		Assert.assertTrue(this.trackerBoardPagePO.isBoardCreated(TrackerBoardData.BOARD_NAME_4));
	}

	// #26270 - Edit a board with valid data - A2
	@Test(priority = 15)
	public void editBoardWithValidDescription() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickToEditBoard(TrackerBoardData.BOARD_NAME_5);
		this.trackerBoardPagePO.fillBoardDescriptionInput(TrackerBoardData.BOARD_DESCRIPTION_2);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		this.trackerBoardPagePO.clickOnStepThreeNextButton();
		this.trackerBoardPagePO.clickOnBoardSaveButton();

		Assert.assertEquals(this.commonPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		this.trackerBoardPagePO.clickToEditBoard(TrackerBoardData.BOARD_NAME_5);
		Assert.assertEquals(this.trackerBoardPagePO.getInputBoardDescriptionFieldValue(),
				TrackerBoardData.BOARD_DESCRIPTION_2);
	}

	// #26270 - Edit a board with valid data - A3
	@Test(priority = 16)
	public void editBoardWithValidStatus() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickToEditBoard(TrackerBoardData.BOARD_NAME_6);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		this.trackerBoardPagePO.clickOnEditStatus(TrackerBoardData.TODO);
		this.trackerBoardPagePO.fillStatusNameInput(TrackerBoardData.REVIEW);
		this.trackerBoardPagePO.clickOnStatusModalSaveButton();
		this.trackerBoardPagePO.clickOnStepThreeNextButton();
		this.trackerBoardPagePO.clickOnBoardSaveButton();

		Assert.assertEquals(this.commonPagePO.getToastMsg(), CommonData.MSG_SUCCESSFULLY_UPDATED);
		this.trackerBoardPagePO.clickToEditBoard(TrackerBoardData.BOARD_NAME_6);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		Assert.assertTrue(this.trackerBoardPagePO.isStatusDisplayed(TrackerBoardData.REVIEW));
	}

	// #26271- Edit a Board with invalid data - A1
	@Test(priority = 17)
	public void editBoardWithBlankName() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickToEditBoard(TrackerBoardData.BOARD_NAME_7);
		this.trackerBoardPagePO.fillBoardNameInput(CommonData.INVALID_FIELD);
		this.trackerBoardPagePO.fillBoardDescriptionInput(TrackerBoardData.BOARD_DESCRIPTION_2);

		Assert.assertFalse(this.trackerBoardPagePO.isFirstNextButtonClickable());
		Assert.assertEquals(this.trackerBoardPage.getInputBoardNameLabel().getText(),
				CommonData.INVALID_FIELD_MESSAGE_ERROR);
	}

	// #26271- Edit a Board with invalid data - A2
	@Test(priority = 18)
	public void editBoardWithBlankStatus() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickToEditBoard(TrackerBoardData.BOARD_NAME_8);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		this.trackerBoardPagePO.clickOnEditStatus(TrackerBoardData.TODO);
		this.trackerBoardPagePO.fillStatusNameInput(CommonData.EMPTY_ATTRIBUTE);
		this.trackerBoardPagePO.fillStatusNameInput(TrackerBoardData.TAB);

		Assert.assertFalse(this.trackerBoardPagePO.isStatusSaveButtonClickable());
		Assert.assertTrue(this.trackerBoardPagePO.isInputBoardStatusLabelDisplayed());
	}

	// #37318- Edit board with the same name - A1
	@Test(priority = 19)
	public void editBoardWithTheSameName() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickToEditBoard(TrackerBoardData.BOARD_NAME_9);
		this.trackerBoardPage.getBoardNameInput().clear();
		this.trackerBoardPagePO.fillBoardNameInput(TrackerBoardData.BOARD_NAME_1);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		this.trackerBoardPagePO.clickOnStepThreeNextButton();
		this.trackerBoardPagePO.clickOnBoardSaveButton();

		Assert.assertEquals(this.commonPagePO.getToastMsg(), CommonData.INVALID_BOARD_SAME_NAME_MSG);
		this.commonPagePO.goToTrackerPage();
		Assert.assertTrue(this.trackerBoardPagePO.isBoardCreated(TrackerBoardData.BOARD_NAME_9));
	}

	// #37319 - Edit a board to have only one status - A1
	@Test(priority = 20)
	public void editBoardWithOnlyOneStatus() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickToEditBoard(TrackerBoardData.BOARD_NAME_10);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		this.trackerBoardPagePO.clickOnRemoveStatus(TrackerBoardData.TODO);
		this.trackerBoardPagePO.clickOnYesButtonRemoveStatusModal();

		Assert.assertFalse(this.trackerBoardPagePO.isBoardThirdNextButtonClickable());
		Assert.assertFalse(this.trackerBoardPagePO.isCardDataLabelDisplayed());
	}

	// #26267 - Remove a custom field successfully - A1
	@Test(priority = 21)
	public void removeCustomFieldSuccessfully() {

		this.commonPagePO.goToTrackerPage();

		this.trackerBoardPagePO.clickToEditBoard(TrackerBoardData.BOARD_NAME_3);
		this.trackerBoardPagePO.clickOnStepOneNextButton();
		this.trackerBoardPagePO.clickToDeleteCustomAttribute(TrackerBoardData.ATT_INTEGER_TITLE_2);
		this.trackerBoardPagePO.clickOnYesToConfirmAttributeRemoval();
		this.trackerBoardPagePO.clickOnStepTwoNextButton();
		this.trackerBoardPagePO.clickOnStepThreeNextButton();
		this.trackerBoardPagePO.clickOnBoardSaveButton();

		Assert.assertEquals(this.commonPage.getToast().getText(), CommonData.MSG_SUCCESSFULLY_UPDATED);

		this.trackerBoardPagePO.clickToEditBoard(TrackerBoardData.BOARD_NAME_3);
		this.trackerBoardPagePO.clickOnStepOneNextButton();

		Assert.assertFalse(this.trackerBoardPagePO.isCustomAttributeDisplayed(TrackerBoardData.ATT_INTEGER_TITLE_2));
	}

	// #26272 - Remove a Board successfully - A1
	@Test(priority = 22)
	public void removeBoardSuccessfully() {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.clickToDeleteBoard(TrackerBoardData.BOARD_NAME_3);
		this.commonPagePO.clickOnYesToConfirmRemoval();

		Assert.assertEquals(this.commonPage.getToast().getText(), TrackerBoardData.MSG_SUCCESSFULLY_REMOVAL);
		Assert.assertTrue(this.commonPage.getToast().isDisplayed());
		Assert.assertFalse(this.trackerBoardPagePO.isBoardDisplayed(TrackerBoardData.BOARD_NAME_3));
	}

}
