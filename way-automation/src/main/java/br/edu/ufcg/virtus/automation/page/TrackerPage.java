package br.edu.ufcg.virtus.automation.page;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.dataset.TrackerData;
import br.edu.ufcg.virtus.automation.utils.Utils;

public class TrackerPage {

// THIS CLASS IS OBSOLETE, PLEASE MOVE ALL NEEDS TO TRACKERBOARDPAGE OR TO TRACKERCARDPAGE

	public WebDriver driver;
	public SeleniumConfig seleniumConfig;

	public static final String BOARD_ITEM = "div[title='%s']";
	public static final String OPEN_FILTER_COMPONENT_BUTTON = "board-view-button";
	public static final String MEMBER_FIELD = "//*[@id=\"paginated-select\"]/div/div/div[2]/input";
	public static final String MEMBER_NAME_FILTERED = "span[ng-reflect-ng-item-label='%s']";
	public static final String START_DATE = "start-date";
	public static final String FILTER_BUTTON = "btn-filter";
	public static final String REMOVE_ONE_FILTER_BUTTON = "clear-filter";
	public static final String MEMBER_FILTER = "i[ng-reflect-message='%s']";
	public static final String MEMBER_NAME = "/html/body/app-root/app-board-view/mat-drawer-container/mat-drawer-content/div/div[2]/app-filter-label/div/label/label[2]";
	public static final String REMOVE_ALL_FILTERS_BUTTON = "btn-clear";
	public static final String CARD = "div[ng-reflect-router-link='%s']";
	public static final String LABEL_FILTER = "/html/body/app-root/app-board-view/mat-drawer-container/mat-drawer-content/div/div[2]/app-filter-label/div/label";
	public static final String LABEL_NAME = "member-filter-label";
	private static final String CARD_ITEM = "div[class='%s']";
	private static final String LABEL_CLASS = "label[class='%s']";
	public static final String BOARD_STATUS_CARD_POSITION = "/html/body/app-root/app-board-view/mat-drawer-container/mat-drawer-content/div/div[3]/div/app-board-status-item[%s]/div/div/div/div[1]/app-tracker[%s]/div/div[2]/div[2]/div[2]";
	public static final String NO_COMMENT_DROPDOWN = "/html/body/app-root/app-board-view/mat-drawer-container/mat-drawer-content/div/div[1]/div[2]/div/app-quick-view-comments/div[1]/div/p";
	public static final String TAG_CLASS = "filter-label-value ng-star-inserted";
	private static final String CARD_EDIT = "i[class='%s']";
	private static final String COLLAPSE_ATTACHMENT = "i[class='%s']";
	public static final String CHOOSE_FILE_INPUT = "fileDropRef";
	private static final String SAVE_BUTTON_CARD = "btn-generate";
	public static final String FILE_DOWNLOAD_BUTTON = "btn-download";
	private static final String FILES_LIST = "files-list";
	private static final String DELETE_BUTTON_ATTACHMENT_CLASS = "material-icons-outlined delete";
	private static final String DELETE_BUTTON_ATTACHMENT = "span[ng-reflect-ng-class='%s']";
	public static final String OPENED_COLLAPSE = "div[class='card-body p-0 collapse in']";
	public static final String CARD_EDIT_CLASS = "fas fa-pen";
	public static final String COLLAPSE_ATTACHMENT_CLASS = "fa fa-chevron-down pr-2";
	public static final String CONFIRM_ATTACHMENT_REMOVAL_BUTTON = "//*[@id=\"modal-delete-attachment\"]/div/div/div/div/div[3]/button[2]";
	public static final String STATUS_ID="collapse-status-%s";
	private static final String CONTAINS = "//*[contains(text(),'%s')]";
	private static final String CARD_TITLE = "card-name-input";
	private static final String ADD_CARD_LABEL = " Adicionar Card ";
	private static final String SAVE_AND_CONTINUE_BUTTON_CARD = "btn-save-continue";
	private static final String CARD_DESCRIPTION = "card-description-text-area";
	private static final String CARD_DUE_DATE = "due-date";
	private static final String CANCEL_BUTTON_CARD = "btn-cancel";
	private static final String YES_BUTTON_RECOVERY_ON_CARD = "btn-yes";
	private static final String NO_BUTTON_RECOVERY_ON_CARD = "btn-no";

	public TrackerPage(SeleniumConfig seleniumConfig) {
		this.driver = seleniumConfig.getDriver();
		this.seleniumConfig = seleniumConfig;
	}

	public WebElement getBoard(String boardName) {
		String cssSelector = String.format(BOARD_ITEM, boardName);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
		return this.driver.findElement(By.cssSelector(cssSelector));
	}

	public WebElement getOpenFilters() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(OPEN_FILTER_COMPONENT_BUTTON)));
		return this.driver.findElement(By.id(OPEN_FILTER_COMPONENT_BUTTON));
	}

	public WebElement getMemberField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(MEMBER_FIELD)));
		return this.driver.findElement(By.xpath(MEMBER_FIELD));
	}

	public WebElement getElementFilteredOnMemberList(String member) {
		String cssSelector = String.format(MEMBER_NAME_FILTERED, member);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
		return this.driver.findElement(By.cssSelector(cssSelector));
	}

	public WebElement getStartDateField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(START_DATE)));
		return this.driver.findElement(By.id(START_DATE));
	}

	public WebElement getElementInputStartDate(String date) {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(START_DATE)));
		return this.driver.findElement(By.id(START_DATE));
	}

	public WebElement getFilterButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(FILTER_BUTTON)));
		return this.driver.findElement(By.id(FILTER_BUTTON));
	}

	public WebElement getMemberFilter(String memberFilterName) {
		String cssSelector = String.format(MEMBER_FILTER, memberFilterName);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
		return this.driver.findElement(By.cssSelector(cssSelector));
	}

	public WebElement getFilterLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(LABEL_FILTER)));
		return this.driver.findElement(By.xpath(LABEL_FILTER));
	}

	public WebElement getLabelName() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(LABEL_NAME)));
		return this.driver.findElement(By.id(LABEL_NAME));
	}

	public WebElement getCard(String cardId) {
		String cssSelector = String.format(CARD, cardId);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
		return this.driver.findElement(By.cssSelector(cssSelector));
	}

	public List<WebElement> getCardsWithMember(String memberName) {
		String cssSelector = String.format(MEMBER_FILTER, memberName);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
		return this.driver.findElements(By.cssSelector(cssSelector));
	}

	public List<WebElement> getCards() {
		String cssSelector = String.format(CARD_ITEM, TrackerData.CARD_CLASS);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
		return this.driver.findElements(By.cssSelector(cssSelector));
	}

	public WebElement getRemoveOneFilterButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(REMOVE_ONE_FILTER_BUTTON)));
		return this.driver.findElement(By.id(REMOVE_ONE_FILTER_BUTTON));
	}

	public WebElement getFilterTag(String cardName, String tagName) {
		String tagXpath = String.format(CONTAINS, tagName);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(tagXpath)));
		return this.getCard(cardName).findElement(By.xpath(tagXpath));
	}
	
	public WebElement getCommentIconTagByBoardPositionAndCardPosition(String boardStatusPosition, String cardPosition) {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(this.formatXpathByStatusPositionAndCardPosition(boardStatusPosition, cardPosition))));
		return this.driver.findElement(By.xpath(this.formatXpathByStatusPositionAndCardPosition(boardStatusPosition, cardPosition)));
	}
	
	public WebElement getQuickDropdownText() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(NO_COMMENT_DROPDOWN)));
		return this.driver.findElement(By.xpath(NO_COMMENT_DROPDOWN));
	}

	public List<WebElement> getFilterApplied(String className) {
		String labelClass = String.format(LABEL_CLASS, className);
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(labelClass)));
		return this.driver.findElements(By.cssSelector(labelClass));
	}

	public List<WebElement> getFilterTagsApplied() {
		return this.getFilterApplied(TAG_CLASS);
	}

	public WebElement getLabel(String tagName) {
		for (WebElement filterTag : this.getFilterTagsApplied()) {
			if (filterTag.getText().equals(tagName)) {
				return filterTag;
			}
		}
		return null;
	}

	public WebElement getFileList() {
		String filesList = String.format(CONTAINS, FILES_LIST);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(filesList)));
		return this.driver.findElement(By.xpath(filesList));
	}

	public List<WebElement> getCardsWithTag(String tagName) {
		String tagXpath = String.format(CONTAINS, tagName);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(tagXpath)));
		return this.driver.findElements(By.xpath(tagXpath));
	}

	public WebElement getRemoveAllFilterSButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(REMOVE_ALL_FILTERS_BUTTON)));
		return this.driver.findElement(By.id(REMOVE_ALL_FILTERS_BUTTON));
	}

	public WebElement getCardEditButton() {
		String cssSelector = String.format(CARD_EDIT, CARD_EDIT_CLASS);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
		return this.driver.findElement(By.cssSelector(cssSelector));
	}

	public WebElement getCollapseAttachment() {
		String cssSelector = String.format(COLLAPSE_ATTACHMENT, COLLAPSE_ATTACHMENT_CLASS);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
		return this.driver.findElement(By.cssSelector(cssSelector));
	}
	
	public WebElement getChooseFileInput() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(CHOOSE_FILE_INPUT)));
		return this.driver.findElement(By.id(CHOOSE_FILE_INPUT));
	}

	public WebElement getAttachedFile(String fileName) {
		String tagXpath = String.format(CONTAINS, fileName);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tagXpath)));
		return this.getFileList().findElement(By.xpath(tagXpath));
	}

	public WebElement getSaveButtonCard() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(SAVE_BUTTON_CARD)));
		return this.driver.findElement(By.id(SAVE_BUTTON_CARD));
	}

	public WebElement getDeleteButtonAttachment(String fileName) {
		String cssSelector = String.format(DELETE_BUTTON_ATTACHMENT, DELETE_BUTTON_ATTACHMENT_CLASS);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
		return this.driver.findElement(By.cssSelector(cssSelector));
	}

	public WebElement getDownloadFileButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(FILE_DOWNLOAD_BUTTON)));
		return this.driver.findElement(By.id(FILE_DOWNLOAD_BUTTON));
	}
	
	public WebElement getConfirmAttachnentRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CONFIRM_ATTACHMENT_REMOVAL_BUTTON)));
		return this.driver.findElement(By.xpath(CONFIRM_ATTACHMENT_REMOVAL_BUTTON));
	}

	public void waitForFileDownload(String filePath) {
		this.seleniumConfig.getWait().until(filepresent(filePath));
	}
	
	public ExpectedCondition<Boolean> filepresent(final String filePath) {

	    return new ExpectedCondition<Boolean>() {
	    	public Boolean apply(WebDriver driver) {
	    		return Utils.isFileExists(filePath);
	        } 
	    };
	}
	
	private String formatXpathByStatusPositionAndCardPosition(String boardStatusPosition, String cardPosition) {
		return String.format(BOARD_STATUS_CARD_POSITION, boardStatusPosition, cardPosition);
	}

	public WebElement getStatus(String StatusID) {
		String statusId = String.format(STATUS_ID, StatusID);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(statusId)));
		return this.driver.findElement(By.id(statusId));
	}
	
	public WebElement getAddCardButton(String boardStatus) {
		String cssSelector = String.format(CONTAINS, ADD_CARD_LABEL);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(cssSelector)));
		return this.getStatus(boardStatus).findElement(By.xpath(cssSelector));
	}

	public WebElement getTheCardTitleField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(CARD_TITLE)));
		return this.driver.findElement(By.id(CARD_TITLE));
	}

	public WebElement getSaveAndContinueButtonCard() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(SAVE_AND_CONTINUE_BUTTON_CARD)));
		return this.driver.findElement(By.id(SAVE_AND_CONTINUE_BUTTON_CARD));
	}

	public WebElement getTheCardDescriptionField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(CARD_DESCRIPTION)));
		return this.driver.findElement(By.id(CARD_DESCRIPTION));
	}

	public WebElement getTheCardDueDateField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(CARD_DUE_DATE)));
		return this.driver.findElement(By.id(CARD_DUE_DATE));
	}

	public WebElement getCancelButtonCard() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(CANCEL_BUTTON_CARD)));
		return this.driver.findElement(By.id(CANCEL_BUTTON_CARD));
	}

	public WebElement getYesButtonRecoveryOnCard() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(YES_BUTTON_RECOVERY_ON_CARD)));
		return this.driver.findElement(By.id(YES_BUTTON_RECOVERY_ON_CARD));
	}

	public WebElement getNoButtonRecoveryOnCard() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(NO_BUTTON_RECOVERY_ON_CARD)));
		return this.driver.findElement(By.id(NO_BUTTON_RECOVERY_ON_CARD));
	}

}
