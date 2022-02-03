package br.edu.ufcg.virtus.automation.page;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.TrackerData;
import br.edu.ufcg.virtus.automation.utils.Utils;

public class TrackerBoardPage {

	public WebDriver driver;
	public SeleniumConfig seleniumConfig;

	private static final String BOARD_ITEM = "div[title='%s']";
	private static final String BOARD_EDIT = "update-board-btn";
	private static final String OPEN_FILTER_COMPONENT_BUTTON = "board-view-button";
	private static final String MEMBER_FIELD = "//*[@id=\"paginated-select\"]/div/div/div[2]/input";
	private static final String MEMBER_NAME_FILTERED = "span[ng-reflect-ng-item-label='%s']";
	private static final String START_DATE = "start-date";
	private static final String FILTER_BUTTON = "btn-filter";
	private static final String REMOVE_ONE_FILTER_BUTTON = "clear-filter";
	private static final String MEMBER_FILTER = "i[ng-reflect-message='Filtrar: %s']";
	private static final String MEMBER_NAME = "/html/body/app-root/app-board-view/mat-drawer-container/mat-drawer-content/div/div[2]/app-filter-label/div/label/label[2]";

	private static final String REMOVE_ALL_FILTERS_BUTTON = "btn-clear";
	private static final String CARD = "div[ng-reflect-router-link='%s']";
	private static final String LABEL_FILTER = "/html/body/app-root/app-board-view/mat-drawer-container/mat-drawer-content/div/div[2]/app-filter-label/div/label";
	private static final String LABEL_NAME = "member-filter-label";

	private static final String CARD_ITEM = "div[class='%s']";
	private static final String CARD_LABEL = "//*[contains(text(),'%s')]";
	private static final String LABEL_CLASS = "label[class='%s']";
	private static final String TAG_CLASS = "filter-label-value ng-star-inserted";
	private static final String CARD_EDIT = "i[class='%s']";
	private static final String COLLAPSE_ATTACHMENT = "i[class='%s']";
	private static final String CHOOSE_FILE_INPUT = "fileDropRef";
	private static final String ATTACHED_FILE = "//*[contains(text(),'%s')]";
	private static final String FILE_DOWNLOAD_BUTTON = "btn-download";
	private static final String FILES_LIST = "files-list";
	private static final String CARD_BODY = "card-body";
	private static final String DELETE_BUTTON_ATTACHMENT_CLASS = "material-icons-outlined delete";
	private static final String DELETE_BUTTON_ATTACHMENT = "span[class='%s']";
	private static final String OPENED_COLLAPSE = "div[class='card-body p-0 collapse in']";
	private static final String CARD_EDIT_CLASS = "fas fa-pen";
	private static final String COLLAPSE_ATTACHMENT_CLASS = "fa fa-chevron-down pr-2";

	private static final String BOARD_EDITION_BACK_BUTTON = "back-btn";
	private static final String BOARD_EDITION_NEXT_BUTTON = "next-btn";

	private static final String BOARD_ATT_NAME = "card-name";
	private static final String BOARD_ATT_DESCRIPTION = "desciption-label";
	private static final String BOARD_ATT_DUE_DATE = "due-date-label";

	private static final String STATUS_ID = "//*[@id=\"%s\"]";
	private static final String ADD_CARD_BUTTON = "//*[@id='collapse-status-%s']/div/div[2]";

	private static final String CONFIRM_ATTACHMENT_REMOVAL_BUTTON = "//*[@id=\"modal-delete-attachment\"]/div/div/div/div/div[3]/button[2]";

	private static final String CREATE_BOARD_BUTTON = "div[class='card-custom align-custom custom-border text-color pointer']";
	private static final String BOARD_NAME_INPUT = "name-input";
	private static final String BOARD_DESCRIPTION_INPUT = "description-text-area";
	private static final String ADD_ATTRIBUTES_BUTTON = "button[class='btn btn-primary']";
	private static final String CANCEL_MODAL_BUTTON = "cancel";
	private static final String SECOND_NEXT_BUTTON = "/html/body/app-root/app-board-create/aw-wizard/div/aw-wizard-step[2]/app-second-step-data/div/div[2]/button[2]";
	private static final String THIRD_NEXT_BUTTON = "/html/body/app-root/app-board-create/aw-wizard/div/aw-wizard-step[3]/app-third-step/div/div[2]/button[2]";
	private static final String ADD_STATUS_BUTTON = "/html/body/app-root/app-board-create/aw-wizard/div/aw-wizard-step[3]/app-third-step/div/div[1]/div[1]/div[2]/div/button";
	private static final String STATUS_NAME_INPUT = "/html/body/app-root/app-board-create/aw-wizard/div/aw-wizard-step[3]/app-third-step/div/app-modal-to-add-status/div/div/div/div/div/div[2]/div/input";
	private static final String SAVE_BUTTON = "save-btn";
	private static final String BOARD_NAME_LABEL = "name-label";
	private static final String INVALID_NAME_LABEL = "/html/body/app-root/app-board-create/aw-wizard/div/aw-wizard-step[1]/app-first-step-board-data/div/div[1]/div[2]/div[1]/common-control-message/label";
	private static final String CARD_DATA_LABEL = "/html/body/app-root/app-board-create/aw-wizard/div/aw-wizard-step[3]/app-fourth-step/div/div[1]/div[1]/div[1]/div/div[1]";
	private static final String ATTRIBUTES_SAVE_BUTTON = "submit-modal";
	private static final String INPUT_COMMOM_CONTROL_LABEL = "common-control-message";
	private static final String INPUT_BOARD_STATUS_LABEL = "//*[@id=\"exampleModal\"]/div/div/div/div/div[2]/div/common-control-message/label";
	private static final String STATUS_ITEM = "th[title='%s']";
	private static final String STATUS_EDIT_BUTTON = "//*[@id=\"edit-btn\"]";
	private static final String STATUS_REMOVE_BUTTON = "//*[@id=\"delete-btn\"]";
	private static final String BUTTON_YES_REMOVE_STATUS = "//*[@id=\"modal-delete-item\"]/div/div/div/div/div[3]/button[2]";
	private static final String DELETE_BOARD_BUTTON = "delete-board-btn";
	private static final String EDIT_BOARD_BUTTON = "update-board-btn";
	private static final String DELETE_ATTRIBUTE_BUTTON = "remove-btn";
	private static final String ATTRIBUTE_ITEM = "//*[contains(text(),'%s')]";
	private static final String CONFIRM_ATTRIBUTE_REMOVAL_BUTTON = "//*[@id=\"modal-delete-attributes\"]/div/div/div/div/div[3]/button[2]";

	public TrackerBoardPage(SeleniumConfig seleniumConfig) {
		this.driver = seleniumConfig.getDriver();
		this.seleniumConfig = seleniumConfig;
	}

	public WebElement getBoard(String boardName) {
		String cssSelector = String.format(BOARD_ITEM, boardName);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
		return this.driver.findElement(By.cssSelector(cssSelector));
	}

	public WebElement getBoardEdit(int id) {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(BOARD_EDIT)));
		return this.driver.findElements(By.id(BOARD_EDIT)).get(id);
	}

	public List<WebElement> getBoardEditionNextButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(BOARD_EDITION_NEXT_BUTTON)));
		return this.driver.findElements(By.id(BOARD_EDITION_NEXT_BUTTON));
	}

	public WebElement getBoardEditionBackButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(By.id(BOARD_EDITION_BACK_BUTTON)));
		return this.driver.findElement(By.id(BOARD_EDITION_BACK_BUTTON));
	}

	public WebElement getBoardAttLabelName() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(BOARD_ATT_NAME)));
		return this.driver.findElement(By.id(BOARD_ATT_NAME));
	}

	public WebElement getBoardAttLabelDescription() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.id(BOARD_ATT_DESCRIPTION)));
		return this.driver.findElement(By.id(BOARD_ATT_DESCRIPTION));
	}

	public WebElement getBoardAttLabelDueDate() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(BOARD_ATT_DUE_DATE)));
		return this.driver.findElement(By.id(BOARD_ATT_DUE_DATE));
	}

	public WebElement getOpenFilters() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.presenceOfElementLocated(By.id(OPEN_FILTER_COMPONENT_BUTTON)));
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
		List<WebElement> cards = new ArrayList<WebElement>();
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
		List<WebElement> aux = this.driver.findElements(By.cssSelector(cssSelector));
		for (WebElement e : aux) {
			cards.add(e.findElement(By.xpath("./../../../../..")).findElement(By.className(CARD_BODY)));
		}
		return cards;
	}

	public List<WebElement> getCards() {
		String cssSelector = String.format(CARD_ITEM, TrackerData.CARD_CLASS);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
		return this.driver.findElements(By.cssSelector(cssSelector));
	}

	public WebElement getRemoveOneFilterButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.presenceOfElementLocated(By.id(REMOVE_ONE_FILTER_BUTTON)));
		return this.driver.findElement(By.id(REMOVE_ONE_FILTER_BUTTON));
	}

	public WebElement getFilterTag(String cardName, String tagName) {
		String tagXpath = String.format(CARD_LABEL, tagName);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(tagXpath)));
		return this.getCard(cardName).findElement(By.xpath(tagXpath));
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
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.className(FILES_LIST)));
		return this.driver.findElement(By.className(FILES_LIST));
	}

	public List<WebElement> getCardsWithTag(String tagName) {
		String tagXpath = String.format(CARD_LABEL, tagName);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(tagXpath)));
		return this.driver.findElements(By.xpath(tagXpath));
	}

	public WebElement getRemoveAllFilterSButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.presenceOfElementLocated(By.id(REMOVE_ALL_FILTERS_BUTTON)));
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
		String tagXpath = String.format(ATTACHED_FILE, fileName);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tagXpath)));
		return this.getFileList().findElement(By.xpath(tagXpath));
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

	public WebElement getAddCardButton(String boardStatus) {
		String cssSelector = String.format(ADD_CARD_BUTTON, boardStatus);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(cssSelector)));
		return this.getStatusOnBoard(boardStatus).findElement(By.xpath(cssSelector));
	}

	public WebElement getCreateBoardButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CREATE_BOARD_BUTTON)));
		return this.driver.findElement(By.cssSelector(CREATE_BOARD_BUTTON));
	}

	public WebElement getBoardNameInput() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(BOARD_NAME_INPUT)));
		return this.driver.findElement(By.id(BOARD_NAME_INPUT));
	}

	public WebElement getBoardDescriptionInput() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.id(BOARD_DESCRIPTION_INPUT)));
		return this.driver.findElement(By.id(BOARD_DESCRIPTION_INPUT));
	}

	public WebElement getAddAttributesButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ADD_ATTRIBUTES_BUTTON)));
		return this.driver.findElement(By.cssSelector(ADD_ATTRIBUTES_BUTTON));
	}

	public WebElement getCancelModalButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(CANCEL_MODAL_BUTTON)));
		return this.driver.findElement(By.id(CANCEL_MODAL_BUTTON));
	}

	public WebElement getBoardSecondNextButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(SECOND_NEXT_BUTTON)));
		return this.driver.findElement(By.xpath(SECOND_NEXT_BUTTON));
	}

	public WebElement getAddStatusButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(ADD_STATUS_BUTTON)));
		return this.driver.findElement(By.xpath(ADD_STATUS_BUTTON));
	}

	public WebElement getStatusNameInput() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(STATUS_NAME_INPUT)));
		return this.driver.findElement(By.xpath(STATUS_NAME_INPUT));
	}

	public WebElement getSaveButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(SAVE_BUTTON)));
		return this.driver.findElement(By.id(SAVE_BUTTON));
	}

	public WebElement getBoardThirdNextButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(THIRD_NEXT_BUTTON)));
		return this.driver.findElement(By.xpath(THIRD_NEXT_BUTTON));
	}

	public WebElement getBoardName() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(BOARD_NAME_LABEL)));
		return this.driver.findElement(By.id(BOARD_NAME_LABEL));
	}

	public WebElement getInvalidNameLabelDisplayed() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(INVALID_NAME_LABEL)));
		return this.driver.findElement(By.xpath(INVALID_NAME_LABEL));
	}

	public WebElement getCardDataLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(CARD_DATA_LABEL)));
		return this.driver.findElement(By.xpath(CARD_DATA_LABEL));
	}

	public WebElement getAttributesSaveButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(ATTRIBUTES_SAVE_BUTTON)));
		return this.driver.findElement(By.id(ATTRIBUTES_SAVE_BUTTON));
	}

	public WebElement getOpenedCollapse() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(OPENED_COLLAPSE)));
		return this.driver.findElement(By.cssSelector(OPENED_COLLAPSE));
	}

	public WebElement getMemberName() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(MEMBER_NAME)));
		return this.driver.findElement(By.xpath(MEMBER_NAME));
	}

	public WebElement getInputBoardNameLabel() {
		WebElement element = this.getBoardNameInput().findElement(By.xpath(CommonData.PARENT_ELEMENT))
				.findElement(By.tagName(INPUT_COMMOM_CONTROL_LABEL));
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOf(this.getBoardNameInput()));
		return element;
	}

	public WebElement getInputBoardDescriptionLabel() {
		WebElement element = this.getBoardDescriptionInput().findElement(By.xpath(CommonData.PARENT_ELEMENT))
				.findElement(By.tagName(INPUT_COMMOM_CONTROL_LABEL));
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOf(this.getBoardNameInput()));
		return element;
	}

	public WebElement getInputBoardStatusLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(INPUT_BOARD_STATUS_LABEL)));
		return this.driver.findElement(By.xpath(INPUT_BOARD_STATUS_LABEL));
	}

	public WebElement getStatus(String status) {
		String cssSelector = String.format(STATUS_ITEM, status);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
		return this.driver.findElement(By.cssSelector(cssSelector));
	}

	public WebElement getStatusOnBoard(String status) {
		String cssSelector = String.format(STATUS_ID, status);
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(cssSelector)));
		return this.driver.findElement(By.xpath(cssSelector)).findElement(By.xpath(CommonData.PARENT_ELEMENT));
	}

	public WebElement getYesRemoveButtonStatusModal() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BUTTON_YES_REMOVE_STATUS)));
		return this.driver.findElement(By.xpath(BUTTON_YES_REMOVE_STATUS));
	}

	public WebElement getStatusEditButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(STATUS_EDIT_BUTTON)));
		return this.driver.findElement(By.xpath(STATUS_EDIT_BUTTON));
	}

	public WebElement getStatusRemoveButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(STATUS_REMOVE_BUTTON)));
		return this.driver.findElement(By.xpath(STATUS_REMOVE_BUTTON));
	}

	public WebElement getDeleteBoardButton(String boardName) {
		return this.getBoard(boardName).findElement(By.xpath(CommonData.PARENT_ELEMENT))
				.findElement(By.xpath(CommonData.PARENT_ELEMENT)).findElement(By.id(DELETE_BOARD_BUTTON));
	}

	public WebElement getEditBoardButton(String boardName) {
		return this.getBoard(boardName).findElement(By.xpath(CommonData.PARENT_ELEMENT))
				.findElement(By.xpath(CommonData.PARENT_ELEMENT)).findElement(By.id(EDIT_BOARD_BUTTON));
	}

	public WebElement getDeleteCustomAttributeButton(String attributeName) {
		return this.getAttribute(attributeName).findElement(By.xpath(CommonData.PARENT_ELEMENT))
				.findElement(By.xpath(CommonData.PARENT_ELEMENT)).findElement(By.id(DELETE_ATTRIBUTE_BUTTON));
	}

	public WebElement getAttribute(String attributeName) {
		String xpath = String.format(ATTRIBUTE_ITEM, attributeName);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		return this.driver.findElement(By.xpath(xpath));
	}

	public WebElement getYesButtonToConfirmAttributeRemoval() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(CONFIRM_ATTRIBUTE_REMOVAL_BUTTON)));
		return this.driver.findElement(By.xpath(CONFIRM_ATTRIBUTE_REMOVAL_BUTTON));
	}

	public WebElement getEditButtonStatus(String status) {
		return this.getStatus(status).findElement(By.xpath(STATUS_EDIT_BUTTON));
	}

	public WebElement getRemoveButtonStatus(String status) {
		return this.getStatus(status).findElement(By.xpath(STATUS_REMOVE_BUTTON));
	}
}
