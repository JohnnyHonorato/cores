package br.edu.ufcg.virtus.automation.tests;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import br.edu.ufcg.virtus.automation.config.EnvironmentSetup;
import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.database.TrackerDatabase;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.ConfigData;
import br.edu.ufcg.virtus.automation.dataset.TrackerData;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.page.TrackerPage;
import br.edu.ufcg.virtus.automation.pagePO.CommonPagePO;
import br.edu.ufcg.virtus.automation.pagePO.LoginPagePO;
import br.edu.ufcg.virtus.automation.pagePO.TrackerPagePO;
import br.edu.ufcg.virtus.automation.utils.Utils;

public class TrackerTests {

	// THIS CLASS IS OBSOLETE, PLEASE MOVE ALL NEEDS TO TRACKERBOARDTESTS OR TO
	// TRACKERCARDTESTS

	private TrackerPagePO trackerPagePO;
	private TrackerPage trackerPage;
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

		TrackerDatabase.clearTrackersDataOnDatabase();
		this.seleniumConfig = new SeleniumConfig();
		TrackerDatabase.injectTrackersDataOnDatabase();
		this.commonPagePO = new CommonPagePO(this.seleniumConfig);
		this.trackerPagePO = new TrackerPagePO(this.seleniumConfig, this.commonPagePO.commonPage);
		this.loginPagePO = new LoginPagePO(this.seleniumConfig);

		this.trackerPage = this.trackerPagePO.trackerPage;
		this.commonPage = this.commonPagePO.commonPage;
		this.loginPagePO.loginSuccessfully();
	}

	@AfterClass(alwaysRun = true)
	public void setupAfterTest() throws SQLException, IOException {

		TrackerDatabase.clearTrackersDataOnDatabase();
		TrackerDatabase.clearFilesFromDownloadsDirectory();
		TrackerDatabase.clearFilesFromWayFilesDirectory();
		TrackerDatabase.closeTrackersDatabaseConnection();
		this.seleniumConfig.getDriver().quit();
	}

	// #29377 - Filter by responsible in all status - A1
	@Test(priority = 0)

	public void applyMemberFilterSuccessfully() {

		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_NAME_1);
		this.trackerPagePO.filterByMember(TrackerData.MEMBER_ITEM);
		Assert.assertEquals(this.trackerPage.getLabelName().getText(), TrackerData.MEMBER_ITEM);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCardsDisplayedByMember(TrackerData.MEMBER_ITEM_ICON),
				TrackerData.TOTAL_SELECTED_MEMBER_CARDS);
	}

	// #29780 - Implement test case to Clear filter - A1
	@Test(priority = 1)

	public void removeOneAppliedMemberFilterSuccessfully() {
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_NAME_1);
		this.trackerPagePO.filterByMember(TrackerData.MEMBER_ITEM);
		this.trackerPagePO.clickToRemoveOneFilter();
		Assert.assertTrue(this.trackerPagePO.getNumberOfCards() > TrackerData.TOTAL_SELECTED_MEMBER_CARDS);
		Assert.assertEquals(this.trackerPagePO.getMemberInputValue(), CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCards(), TrackerData.TOTAL_BOARD_CARDS);
	}

	// #29779 - Implement test case to Clear all filters - A1
	@Test(priority = 2)

	public void removeAllAppliedFiltersSuccessfully() {
		final String date = this.trackerPagePO.getStringDate();
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_NAME_1);
		this.trackerPagePO.filterByMemberAndDate(TrackerData.MEMBER_ITEM, date);
		this.trackerPagePO.clickToRemoveAllFilters();
		Assert.assertTrue(this.trackerPagePO.getNumberOfCards() > TrackerData.TOTAL_SELECTED_MEMBER_CARDS);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCards(), TrackerData.TOTAL_BOARD_CARDS);
		Assert.assertEquals(this.trackerPagePO.getMemberInputValue(), CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerPagePO.getStartDateInputValue(), CommonData.EMPTY_VALUE);
	}

	// #124523 - Automated test for quick filtering of cards for multiple members
	@Test(priority = 3)
	public void checkQuickFilterAppliedToMultipleMembers() {
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_1);

		this.trackerPagePO.clickOnQuickMemberFilter(TrackerData.MEMBER_FILTER_NAME_1);
		Assert.assertEquals(this.trackerPage.getLabelName().getText(), TrackerData.MEMBER_NAME_1);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCardsDisplayedByMember(TrackerData.MEMBER_FILTER_NAME_1),
				TrackerData.MEMBER1_NUMBER_OF_CARDS);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCards(), TrackerData.MEMBER1_NUMBER_OF_CARDS);

		this.trackerPagePO.clickOnQuickMemberFilter(TrackerData.MEMBER_ITEM_ICON);
		Assert.assertEquals(this.trackerPage.getLabelName().getText(), TrackerData.MEMBER_ITEM);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCardsDisplayedByMember(TrackerData.MEMBER_ITEM_ICON),
				TrackerData.TOTAL_SELECTED_MEMBER_CARDS);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCards(), TrackerData.TOTAL_SELECTED_MEMBER_CARDS);

		this.trackerPagePO.clickOnQuickMemberFilter(TrackerData.MEMBER_FILTER_NAME_3);
		Assert.assertEquals(this.trackerPage.getLabelName().getText(), TrackerData.MEMBER_NAME_3);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCardsDisplayedByMember(TrackerData.MEMBER_FILTER_NAME_3),
				TrackerData.MEMBER3_NUMBER_OF_CARDS);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCards(), TrackerData.MEMBER3_NUMBER_OF_CARDS);
	}

	// #124344 - Automated test for quick filtering of cards for a member
	@Test(priority = 4)
	public void checkQuickFilterAppliedToOneMember() {
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_1);

		this.trackerPagePO.clickOnQuickMemberFilter(TrackerData.MEMBER_FILTER_NAME_1);
		Assert.assertEquals(this.trackerPage.getLabelName().getText(), TrackerData.MEMBER_NAME_1);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCardsDisplayedByMember(TrackerData.MEMBER_FILTER_NAME_1),
				TrackerData.MEMBER1_NUMBER_OF_CARDS);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCards(), TrackerData.MEMBER1_NUMBER_OF_CARDS);
	}

	// #124525 - Automated test for quick filtering of cards for multiple tags
	@Test(priority = 5)
	public void checkQuickFilterAppliedToMultipleTags() {
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_1);
		this.trackerPagePO.clickOnQuickTagFilter(TrackerData.CARD_ID1, TrackerData.TAG_2);
		Assert.assertEquals(this.trackerPagePO.getTextLabel(TrackerData.TAG_NAME_2), TrackerData.TAG_NAME_2);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCardsDisplayedByTag(TrackerData.TAG_2),
				TrackerData.TAG2_NUMBER_OF_CARDS);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCards(), TrackerData.TAG2_NUMBER_OF_CARDS);

		this.trackerPagePO.clickOnQuickTagFilter(TrackerData.CARD_ID1, TrackerData.TAG_1);
		Assert.assertEquals(this.trackerPagePO.getTextLabel(TrackerData.TAG_NAME_1), TrackerData.TAG_NAME_1);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCardsDisplayedByTag(TrackerData.TAG_1),
				TrackerData.TAG1_NUMBER_OF_CARDS);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCards(), TrackerData.TAG1_NUMBER_OF_CARDS);
	}

	// #124345 - Automated test for quick filtering of cards for multiple tags
	@Test(priority = 6)
	public void checkQuickFilterAppliedToOneTag() {
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_1);

		this.trackerPagePO.clickOnQuickTagFilter(TrackerData.CARD_ID1, TrackerData.TAG_1);
		Assert.assertEquals(this.trackerPagePO.getTextLabel(TrackerData.TAG_NAME_1), TrackerData.TAG_NAME_1);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCardsDisplayedByTag(TrackerData.TAG_1),
				TrackerData.TAG1_NUMBER_OF_CARDS);
		Assert.assertEquals(this.trackerPagePO.getNumberOfCards(), TrackerData.TAG1_NUMBER_OF_CARDS);
	}

	@Test(priority = 7)
	public void checkQuickViewCommentList() throws InterruptedException {
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_1);
		this.trackerPagePO.openCardQuickCommentsByBoardStatusPositionAndCardPosition(TrackerData.BOARD_STATUS1,
				TrackerData.CARD_POSITION1);
		Assert.assertEquals(this.trackerPagePO.getQuickDropdownCommentText(), TrackerData.NO_COMMENTS_DROP_DOWN_TEXT);
		Assert.assertEquals(this.trackerPagePO.getCommentIconTextByBoardStatusPositionAndCardPosition(
				TrackerData.BOARD_STATUS1, TrackerData.CARD_POSITION1), TrackerData.NO_COMMENTS_ICON_TEXT);
	}

	// #124536 - Check attachment upload on card
	@Test(priority = 8)
	public void cardAttachmentUploadSuccessfully() throws IOException {
		final String filePath = Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY;
		final int quant_files = Utils.countFiles(filePath);
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_2);
		this.trackerPagePO.openCard(TrackerData.CARD_ID4);
		this.trackerPagePO.openCardEdit();
		this.trackerPagePO.openCollapseAttachment();
		this.trackerPagePO.uploadFile(Utils.getProjectPath() + TrackerData.FILE_PATH1);
		this.trackerPagePO.clickSaveButtonCard();
		this.trackerPage.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.id(CommonPage.TOAST)));
		Assert.assertEquals(Utils.countFiles(filePath), quant_files + 1);
		this.trackerPagePO.openCardEdit();
		this.trackerPagePO.openCollapseAttachment();
		Assert.assertTrue(this.trackerPage.getAttachedFile(TrackerData.FILE_NAME1).isDisplayed());

	}

	// #125777 - Check attachment list on card view
	@Test(priority = 9)
	public void cardAttachmentListSuccessfully() throws IOException {
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_5);
		this.trackerPagePO.openCard(TrackerData.CARD_ID7);
		this.trackerPagePO.openCollapseAttachment();
		Assert.assertTrue(this.trackerPage.getAttachedFile(TrackerData.FILE_NAME1).isDisplayed());
	}

	// #124537 - Check attachment download on card
	@Test(priority = 10)
	public void cardAttachmentDownloadSuccessfully() throws IOException {
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_3);
		this.trackerPagePO.openCard(TrackerData.CARD_ID5);
		this.trackerPagePO.openCardEdit();
		this.trackerPagePO.openCollapseAttachment();
		this.trackerPagePO.clickToDownloadFile();
		final String filePath = Utils.getEnvironmentUserPath() + ConfigData.DOWNLOADS_DIRECTORY
				+ TrackerData.FILE_NAME4;
		this.trackerPage.waitForFileDownload(filePath);
		Assert.assertTrue(Utils.isFileExists(filePath));
	}

	// #125776 - Check attachment download on card view
	@Test(priority = 11)
	public void cardAttachmentDownloadOnCardViewSuccessfully() throws IOException {
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_5);
		this.trackerPagePO.openCard(TrackerData.CARD_ID7);
		this.trackerPagePO.openCollapseAttachment();
		this.trackerPagePO.clickToDownloadFile();
		final String filePath = Utils.getEnvironmentUserPath() + ConfigData.DOWNLOADS_DIRECTORY
				+ TrackerData.FILE_NAME1;
		this.trackerPage.waitForFileDownload(filePath);
		Assert.assertTrue(Utils.isFileExists(filePath));
	}

	// #124540 - Check card attachment deletion
	@Test(priority = 12)
	public void cardAttachmentDeletionSuccessfully() throws IOException {
		final String filePath = Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY;
		final int initialNumberOfFiles = Utils.countFiles(filePath);
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_1);
		this.trackerPagePO.openCard(TrackerData.CARD_ID2);
		this.trackerPagePO.openCardEdit();
		this.trackerPagePO.openCollapseAttachment();
		this.trackerPagePO.clickDeleteButtonAttachment(TrackerData.FILE_NAME2);
		this.trackerPagePO.clickOnYesToConfirmAttachmentRemoval();
		this.trackerPagePO.clickSaveButtonCard();
		this.trackerPage.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.id(CommonPage.TOAST)));
		this.trackerPagePO.openCardEdit();
		this.trackerPagePO.openCollapseAttachment();
		Assert.assertTrue(initialNumberOfFiles > Utils.countFiles(filePath));
		Assert.assertFalse(this.trackerPagePO.isAttachmentDisplayedInList(TrackerData.FILE_NAME2));

	}

	// #36952 - Create and continue with valid card data
	@Test(priority = 13)
	public void cardSavedBySaveAndContinueButtonSuccessfully() {
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_5);
		this.trackerPagePO.clickAddCardButton(TrackerDatabase.STATUS13_ID);
		this.trackerPagePO.fillCardTitleField(TrackerData.CARD1_TITLE);
		this.trackerPagePO.clickSaveAndContinueButtonCard();
		Assert.assertTrue(this.commonPage.getToast().isDisplayed());
		Assert.assertTrue(this.trackerPage.getTheCardTitleField().isDisplayed());

	}

	// #36956 - Cancel recovering draft - A2
	@Test(priority = 14)
	public void cardDiscardDraftDataSuccessfully() throws InterruptedException {
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_5);
		this.trackerPagePO.clickAddCardButton(TrackerDatabase.STATUS13_ID);
		this.trackerPagePO.fillInStandardCardFields(TrackerData.CARD3_TITLE, TrackerData.START_DATE,
				TrackerData.CARD3_DESCRIPTION);
		Thread.sleep(5000); // sleep used to wait for the draft to be created
		this.trackerPagePO.clickCancelButtonCard();
		this.trackerPagePO.clickAddCardButton(TrackerDatabase.STATUS13_ID);
		this.trackerPagePO.clickNoButtonToConfirmDataRecoveryOnCard();
		Assert.assertEquals(this.trackerPagePO.getTextFromCardTitleField(), CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerPagePO.getTextFromCardDueDateField(), CommonData.EMPTY_VALUE);
		Assert.assertEquals(this.trackerPagePO.getTextFromCardDescriptionField(), CommonData.EMPTY_VALUE);
	}

	// #36956 - Recover draft with success - A1
	@Test(priority = 15)
	public void cardRetrieveDraftDataSuccessfully() throws InterruptedException {
		this.commonPagePO.goToTrackerPage();
		this.trackerPagePO.openBoard(TrackerData.BOARD_5);
		this.trackerPagePO.clickAddCardButton(TrackerDatabase.STATUS13_ID);
		this.trackerPagePO.fillInStandardCardFields(TrackerData.CARD2_TITLE, TrackerData.START_DATE,
				TrackerData.CARD2_DESCRIPTION);
		Thread.sleep(5000); // sleep used to wait for the draft to be created
		this.trackerPagePO.clickCancelButtonCard();
		this.trackerPagePO.clickAddCardButton(TrackerDatabase.STATUS13_ID);
		this.trackerPagePO.clickYesButtonToConfirmDataRecoveryOnCard();
		Assert.assertEquals(this.trackerPagePO.getTextFromCardTitleField(), TrackerData.CARD2_TITLE);
		Assert.assertEquals(this.trackerPagePO.getTextFromCardDueDateField(), TrackerData.START_DATE);
		Assert.assertEquals(this.trackerPagePO.getTextFromCardDescriptionField(), TrackerData.CARD2_DESCRIPTION);
	}

}
