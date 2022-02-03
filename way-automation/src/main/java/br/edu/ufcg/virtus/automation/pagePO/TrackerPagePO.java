package br.edu.ufcg.virtus.automation.pagePO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.page.TrackerPage;

public class TrackerPagePO {

// THIS CLASS IS OBSOLETE, PLEASE MOVE ALL NEEDS TO TRACKERBOARDPAGEPO OR TO TRACKERCARDPAGEPO

	public TrackerPage trackerPage;

	public CommonPage commonPage;

	public TrackerPagePO(SeleniumConfig seleniumConfig, CommonPage commonPage) {
		this.trackerPage = new TrackerPage(seleniumConfig);
		this.commonPage = commonPage;
	}

	public void openBoard(String boardName) {
		this.trackerPage.getBoard(boardName).click();
	}

	public void openFilterComponentButton() {
		this.trackerPage.getOpenFilters().click();
	}

	public void scrollDownPage() {
		JavascriptExecutor js = (JavascriptExecutor) this.trackerPage.driver;
		js.executeScript("window.scrollBy(0,1000)");
	}

	public void selectMemberField(String member) {
		this.scrollDownPage();
		WebElement memberField = this.trackerPage.getMemberField();
		memberField.sendKeys(member);
		this.trackerPage.getElementFilteredOnMemberList(member).click();
	}

	public void insertStartDate(String date) {
		WebElement startDateField = this.trackerPage.getStartDateField();
		startDateField.sendKeys(date);
		this.trackerPage.getElementInputStartDate(date).click();
	}

	public void clickFilterButton() {
		this.trackerPage.getFilterButton().click();
	}

	public void filterByMember(String memberName) {
		this.openFilterComponentButton();
		this.selectMemberField(memberName);
		this.clickFilterButton();
	}

	public void filterByMemberAndDate(String memberName, String date) {
		this.openFilterComponentButton();
		this.selectMemberField(memberName);
		this.insertStartDate(date);
		this.clickFilterButton();
	}

	public void clickOnQuickMemberFilter(String memberFilterName) {
		this.trackerPage.getMemberFilter(memberFilterName).click();
	}

	public int getNumberOfCardsDisplayedByMember(String memberName) {
		return this.trackerPage.getCardsWithMember(memberName).size();
	}

	public int getNumberOfCards() {
		return this.trackerPage.getCards().size();
	}

	public void clickOnQuickTagFilter(String cardName, String tagName) {
		this.trackerPage.getFilterTag(cardName, tagName).click();
	}

	public void openCardQuickCommentsByBoardStatusPositionAndCardPosition(String boardPosition, String cardPosition) {
		this.trackerPage.getCommentIconTagByBoardPositionAndCardPosition(boardPosition, cardPosition).click();
	}
	
	public String getCommentIconTextByBoardStatusPositionAndCardPosition(String boardPosition, String cardPosition) {
		return this.trackerPage.getCommentIconTagByBoardPositionAndCardPosition(boardPosition, cardPosition).getText();
	}
	
	public String getQuickDropdownCommentText() {
		return this.trackerPage.getQuickDropdownText().getText();
	}

	public String getTextLabel(String tagName) {
		return this.trackerPage.getLabel(tagName).getText();
	}

	public int getNumberOfCardsDisplayedByTag(String tagName) {
		return this.trackerPage.getCardsWithTag(tagName).size();
	}

	public void clickToRemoveOneFilter() {
		this.trackerPage.getRemoveOneFilterButton().click();
	}

	public void clickToRemoveAllFilters() {
		this.trackerPage.getRemoveAllFilterSButton().click();
	}

	public String getStringDate() {
		Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(CommonData.STRING_DATE_FORMAT);
        String strDate = dateFormat.format(date);
        return strDate;
	}

	public String getMemberInputValue() {
		return this.trackerPage.getMemberField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getStartDateInputValue() {
		return this.trackerPage.getStartDateField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public void openCard(String cardId) {
		this.trackerPage.getCard(cardId).click();
	}

	public void openCardEdit() {
		this.trackerPage.getCardEditButton().click();
	}

	public void openCollapseAttachment() {
		this.trackerPage.getCollapseAttachment().click();
	}

	public void uploadFile(String filePath) {
		this.trackerPage.getChooseFileInput().sendKeys(filePath);
	}

	public void clickSaveButtonCard() {
		this.trackerPage.getSaveButtonCard().click();
	}

	public void clickDeleteButtonAttachment(String fileName) {
		this.trackerPage.getDeleteButtonAttachment(fileName).click();
	}

	public void clickToDownloadFile() {
		this.trackerPage.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.id(TrackerPage.FILE_DOWNLOAD_BUTTON)));
		this.trackerPage.getDownloadFileButton().click();
	}
	
	public void clickOnYesToConfirmAttachmentRemoval() {
		this.trackerPage.getConfirmAttachnentRemovalButton().click();;
	}

	public boolean isAttachmentDisplayedInList(String fileName) {
		boolean isDisplayed;
		try {
			isDisplayed = trackerPage.getAttachedFile(fileName).isDisplayed();
		} catch (Exception e) {
			isDisplayed = false;
		}
		return isDisplayed;
	}

	public void clickAddCardButton(String boardStatus) {
		this.trackerPage.getAddCardButton(boardStatus).click();
	}

	public void fillCardTitleField(String cardTitle) {
		WebElement titleField = this.trackerPage.getTheCardTitleField();
		titleField.clear();
		titleField.sendKeys(cardTitle);
	}

	public void clickSaveAndContinueButtonCard() {
		this.trackerPage.getSaveAndContinueButtonCard().click();
	}

	public void fillInStandardCardFields(String cardTitle, String startDate, String cardDescription) {
		this.fillCardTitleField(cardTitle);
		this.fillCardDueDateField(startDate);
		this.fillCardDescriptionField(cardDescription);
	}

	private void fillCardDescriptionField(String cardDescription) {
		WebElement descriptionField = this.trackerPage.getTheCardDescriptionField();
		descriptionField.clear();
		descriptionField.sendKeys(cardDescription);
	}

	private void fillCardDueDateField(String startDate) {
		WebElement dueDateField = this.trackerPage.getTheCardDueDateField();
		dueDateField.clear();
		dueDateField.sendKeys(startDate);
	}

	public void clickCancelButtonCard() {
		this.trackerPage.getCancelButtonCard().click();
	}

	public String getTextFromCardTitleField() {
		return this.trackerPage.getTheCardTitleField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getTextFromCardDueDateField() {
		return this.trackerPage.getTheCardDueDateField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getTextFromCardDescriptionField() {
		return this.trackerPage.getTheCardDescriptionField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public void clickYesButtonToConfirmDataRecoveryOnCard() {
		this.trackerPage.getYesButtonRecoveryOnCard().click();
		
	}
	
	public void clickNoButtonToConfirmDataRecoveryOnCard() {
		this.trackerPage.getNoButtonRecoveryOnCard().click();
	}

}
