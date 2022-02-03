package br.edu.ufcg.virtus.automation.pagePO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.TrackerBoardData;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.page.TrackerBoardPage;

public class TrackerBoardPagePO {

	public TrackerBoardPage trackerBoardPage;

	public CommonPage commonPage;
	private CommonPagePO commonPagePO;

	public TrackerBoardPagePO(SeleniumConfig seleniumConfig, CommonPage commonPage) {
		this.trackerBoardPage = new TrackerBoardPage(seleniumConfig);
		this.commonPage = commonPage;
		this.commonPagePO = new CommonPagePO(seleniumConfig);
	}

	public void openBoard(String boardName) {
		this.trackerBoardPage.getBoard(boardName).click();
	}

	public void openFilterComponentButton() {
		this.trackerBoardPage.getOpenFilters().click();
	}

	public void selectMemberField(String member) {
		this.commonPagePO.scrollDownPage();
		WebElement memberField = this.trackerBoardPage.getMemberField();
		memberField.sendKeys(member);
		this.trackerBoardPage.getElementFilteredOnMemberList(member).click();
	}

	public void insertStartDate(String date) {
		WebElement startDateField = this.trackerBoardPage.getStartDateField();
		startDateField.sendKeys(date);
		this.trackerBoardPage.getElementInputStartDate(date).click();
	}

	public void clickFilterButton() {
		this.trackerBoardPage.getFilterButton().click();
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
		this.trackerBoardPage.getMemberFilter(memberFilterName).click();
	}

	public int getNumberOfCards() {
		return this.trackerBoardPage.getCards().size();
	}

	public Boolean isCardDisplayedOnTable(String cardName) {
		this.commonPage.seleniumConfig.getWait()
				.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CommonPage.TABLE_LOADING)));
		
		List<WebElement> cardsList = this.trackerBoardPage.getCards();
		for (WebElement o : cardsList) {
			String insertedCardName = o.findElement(By.className("card-body")).findElement(By.id("title-header"))
					.getText();
			if (insertedCardName.equals(cardName))
				return true;
		}
		return false;
	}

	public Boolean isCardDisplayedByMember(String cardName, String member) {
		this.commonPage.seleniumConfig.getWait()
				.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CommonPage.TABLE_LOADING)));

		List<WebElement> cardsList = this.trackerBoardPage.getCardsWithMember(member);
		for (WebElement o : cardsList) {
			String insertedCardName = o.findElement(By.id("title-header")).getText();
			if (insertedCardName.equals(cardName))
				return true;
		}
		return false;
	}

	public void clickOnQuickTagFilter(String cardName, String tagName) {
		this.trackerBoardPage.getFilterTag(cardName, tagName).click();
	}

	public String getTextLabel(String tagName) {
		return this.trackerBoardPage.getLabel(tagName).getText();
	}

	public int getNumberOfCardsDisplayedByTag(String tagName) {
		return this.trackerBoardPage.getCardsWithTag(tagName).size();
	}

	public void clickToRemoveOneFilter() {
		this.trackerBoardPage.getRemoveOneFilterButton().click();
	}

	public void clickToRemoveAllFilters() {
		this.trackerBoardPage.getRemoveAllFilterSButton().click();
	}

	public String getStringDate() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat(CommonData.STRING_DATE_FORMAT);
		String strDate = dateFormat.format(date);
		return strDate;
	}

	public String getMemberInputValue() {
		return this.trackerBoardPage.getMemberField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getStartDateInputValue() {
		return this.trackerBoardPage.getStartDateField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public void openCard(String cardId) {
		this.trackerBoardPage.getCard(cardId).click();
	}

	public void openCardEdit() {
		this.trackerBoardPage.getCardEditButton().click();
	}

	public void openCollapseAttachment() {
		this.trackerBoardPage.getCollapseAttachment().click();
	}

	public void uploadFile(String filePath) {
		this.trackerBoardPage.getChooseFileInput().sendKeys(filePath);
	}

	public void clickDeleteButtonAttachment(String fileName) {
		this.trackerBoardPage.getDeleteButtonAttachment(fileName).click();
	}

	public void clickToDownloadFile() {
		this.trackerBoardPage.getOpenedCollapse();
		this.trackerBoardPage.getDownloadFileButton().click();
	}

	public void clickOnYesToConfirmAttachmentRemoval() {
		this.trackerBoardPage.getConfirmAttachnentRemovalButton().click();
	}

	public boolean isAttachmentDisplayedInList(String fileName) {
		boolean isDisplayed;
		try {
			isDisplayed = trackerBoardPage.getAttachedFile(fileName).isDisplayed();
		} catch (Exception e) {
			isDisplayed = false;
		}
		return isDisplayed;
	}

	public void clickAddCardButton(String boardStatus) {
		this.trackerBoardPage.getAddCardButton(boardStatus).click();
	}

	public void clickOnCreateBoardButton() {
		this.trackerBoardPage.getCreateBoardButton().click();
	}

	public void fillBoardNameInput(String boardName) {
		this.trackerBoardPage.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOf(this.trackerBoardPage.getBoardNameInput()));
		this.trackerBoardPage.getBoardNameInput().clear();
		this.trackerBoardPage.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOf(this.trackerBoardPage.getBoardNameInput()));
		this.trackerBoardPage.getBoardNameInput().sendKeys(boardName);
		this.trackerBoardPage.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOf(this.trackerBoardPage.getBoardNameInput()));
	}

	public void fillBoardNameInput(Keys boardName) {
		this.trackerBoardPage.getBoardNameInput().clear();
		this.trackerBoardPage.getBoardNameInput().sendKeys(boardName);
	}

	public void fillBoardDescriptionInput(String boardDescription) {
		this.trackerBoardPage.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOf(this.trackerBoardPage.getBoardDescriptionInput()));
		this.trackerBoardPage.getBoardDescriptionInput().clear();
		this.trackerBoardPage.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOf(this.trackerBoardPage.getBoardDescriptionInput()));
		this.trackerBoardPage.getBoardDescriptionInput().sendKeys(boardDescription);
	}

	public void fillBoardDescriptionInput(Keys boardDescription) {
		this.trackerBoardPage.getBoardDescriptionInput().sendKeys(boardDescription);
	}

	public void clickOnStepOneNextButton() {
		WebElement stepOneNextButton = this.trackerBoardPage.getBoardEditionNextButton()
				.get(TrackerBoardData.FIRST_NEXT_BUTTON);
		this.trackerBoardPage.seleniumConfig.getWait()
				.until(ExpectedConditions.elementToBeClickable(stepOneNextButton));
		stepOneNextButton.click();
	}

	public void clickOnStepTwoNextButton() {
		WebElement stepTwoNextButton = this.trackerBoardPage.getBoardEditionNextButton()
				.get(TrackerBoardData.SECOND_NEXT_BUTTON);
		this.trackerBoardPage.seleniumConfig.getWait()
				.until(ExpectedConditions.elementToBeClickable(stepTwoNextButton));
		stepTwoNextButton.click();
	}

	public void clickOnStepThreeNextButton() {
		WebElement stepThreeNextButton = this.trackerBoardPage.getBoardEditionNextButton()
				.get(TrackerBoardData.THIRD_NEXT_BUTTON);
		this.trackerBoardPage.seleniumConfig.getWait()
				.until(ExpectedConditions.elementToBeClickable(stepThreeNextButton));
		stepThreeNextButton.click();
	}

	public void clickOnModalButton() {
		this.trackerBoardPage.getAddAttributesButton().click();
	}

	public void clickOnCancelModalButton() {
		this.trackerBoardPage.getCancelModalButton().click();
	}

	public void clickOnAddStatusButton() {
		this.trackerBoardPage.getAddStatusButton().click();
	}

	public void fillStatusNameInput(String statusName) {
		this.trackerBoardPage.getStatusNameInput().clear();
		this.trackerBoardPage.getStatusNameInput().sendKeys(statusName);
	}

	public void fillStatusNameInput(Keys statusName) {
		this.trackerBoardPage.getStatusNameInput().clear();
		this.trackerBoardPage.getStatusNameInput().sendKeys(statusName);
	}

	public void clickOnAttributesModalSaveButton() {
		this.trackerBoardPage.getAttributesSaveButton().click();
	}

	public void clickOnStatusModalSaveButton() {
		this.trackerBoardPage.getSaveButton().click();
	}

	public Boolean isBoardNameLabelDisplayed() {
		try {
			this.trackerBoardPage.getBoardName();
			return this.trackerBoardPage.getBoardName().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean isBoardEditionNextButtonClickable(int button) {
		try {
			this.trackerBoardPage.getBoardEditionNextButton();
			return this.trackerBoardPage.getBoardEditionNextButton().get(button).isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean isInvalidNameLabelDisplayed() {
		try {
			this.trackerBoardPage.getInvalidNameLabelDisplayed();
			return this.trackerBoardPage.getInvalidNameLabelDisplayed().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isBoardThirdNextButtonClickable() {
		try {
			this.trackerBoardPage.getBoardThirdNextButton();
			return this.trackerBoardPage.getBoardThirdNextButton().isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isCardDataLabelDisplayed() {
		try {
			this.trackerBoardPage.getCardDataLabel();
			return this.trackerBoardPage.getCardDataLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickOnBoardSaveButton() {
		this.trackerBoardPage.getSaveButton().click();
	}

	public boolean isInputBoardNameLabelDisplayed() {
		return this.trackerBoardPage.getInputBoardNameLabel().isDisplayed();
	}

	public boolean isInputBoardDescriptionLabelDisplayed() {
		return this.trackerBoardPage.getInputBoardDescriptionLabel().isDisplayed();
	}

	public String getInputBoardNameFieldValue() {
		return this.trackerBoardPage.getBoardNameInput().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getInputBoardDescriptionFieldValue() {
		WebElement descriptionField = this.trackerBoardPage.getBoardDescriptionInput();
		return descriptionField.getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public boolean isInputBoardStatusLabelDisplayed() {
		try {
			this.trackerBoardPage.getInputBoardStatusLabel();
			return this.trackerBoardPage.getInputBoardStatusLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInputBoardStatusFieldValue() {
		return this.trackerBoardPage.getStatusNameInput().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public Boolean isStatusSaveButtonClickable() {
		try {
			this.trackerBoardPage.getSaveButton();
			return this.trackerBoardPage.getSaveButton().isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean isBoardCreated(String boardname) {
		String board = this.trackerBoardPage.getBoard(boardname).getAttribute(CommonData.ATTRIBUTE_TITLE);
		boolean result = board.equals(boardname) ? true : false;
		return result;
	}

	public void clickOnEditStatus(String status) {
		this.trackerBoardPage.getEditButtonStatus(status).click();
	}

	public boolean isStatusDisplayed(String status) {
		String statusTitle = this.trackerBoardPage.getStatus(status).getAttribute(CommonData.ATTRIBUTE_TITLE);
		Boolean result = statusTitle.equals(status) ? true : false;
		return result;
	}

	public Boolean isFirstNextButtonClickable() {
		return this.trackerBoardPage.getBoardEditionNextButton().get(TrackerBoardData.FIRST_NEXT_BUTTON).isEnabled();
	}

	public void clickOnRemoveStatus(String status) {
		this.trackerBoardPage.getRemoveButtonStatus(status).click();
	}

	public void clickOnYesButtonRemoveStatusModal() {
		this.trackerBoardPage.getYesRemoveButtonStatusModal().click();
	}

	public void clickToDeleteBoard(String boardName) {
		this.trackerBoardPage.getDeleteBoardButton(boardName).click();
	}

	public boolean isBoardDisplayed(String boardName) {
		try {
			return this.trackerBoardPage.getBoard(boardName).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickToEditBoard(String boardName) {
		this.trackerBoardPage.getEditBoardButton(boardName).click();
	}

	public void clickToDeleteCustomAttribute(String attributeName) {
		this.trackerBoardPage.getDeleteCustomAttributeButton(attributeName).click();
	}

	public void clickOnYesToConfirmAttributeRemoval() {
		WebElement confirmAttributeRemovalButton = this.trackerBoardPage.getYesButtonToConfirmAttributeRemoval();
		this.trackerBoardPage.seleniumConfig.getWait()
				.until(ExpectedConditions.elementToBeClickable(confirmAttributeRemovalButton));
		confirmAttributeRemovalButton.click();
	}

	public boolean isCustomAttributeDisplayed(String attributeName) {

		try {
			return this.trackerBoardPage.getDeleteCustomAttributeButton(attributeName).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

}
