package br.edu.ufcg.virtus.automation.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.dataset.CommonData;

public class TrackerCardPage {

	public WebDriver driver;
	public SeleniumConfig seleniumConfig;

	private static final String CARD_NAME = "page-title";
	private static final String CARD_DESCRIPTION = "tracker-view-description";
	private static final String CARD_DUE_DATE = "due-date-label";
	private static final String CARD_TAG = "tag-name-label-%s";
	private static final String CARD_MEMBER = "member-name-%s";
	private static final String CARD_ATTRIBUTE_NAME = "atribute-title-%s";
	private static final String CARD_ATTRIBUTE_VALUE = "integer-atribute-label=%s";

	private static final String CHECKLISTS_LABELS = "check-list-label";
	private static final String CHECKLIST_ITEM_FIELD = "input-add-checklist-item-%s";
	private static final String ADD_CHECKLIST_ITEM_BUTTON = "add-check-list-btn";
	public static final String CHECKLIST_ITEMS_LIST = "span";
	public static final String CHECKLIST_EDITION_BUTTON = "pop-open";
	public static final String CHECKLIST_DELETE_BUTTON = "delete";
	public static final String CHECKLIST_ITEM_EDITION_BUTTON = "edit";

	private static final String CARD_NAME_FIELD = "card-name-input";
	private static final String CARD_DUE_DATE_FIELD = "due-date";
	private static final String CARD_DESCRIPTION_FIELD = "card-description-text-area";
	private static final String CARD_MEMBER_DROPDOWN = "select-tracker-edit";
	private static final String CARD_MEMBER_DROPDOWN_ITEM = "span[ng-reflect-ng-item-label='%s']";
	private static final String CARD_SAVE_BUTTON = "btn-generate";
	private static final String CARD_SAVE_AND_CONTINUE_BUTTON = "btn-save-continue";
	private static final String CARD_ERROR_LABEL = "common-control-message";
	private static final String DELETE_CARD_BUTTON = "btn-clear";
	private static final String CARD_BACK_BUTTON = "btn-cancel";
	private static final String DELETE_CARD_CONFIRM_MODAL_BUTTON = "//*[@id=\"modal-delete-tracker\"]/div/div/div/div/div[3]/button[2]";
	private static final String DELETE_CARD_CANCEL_MODAL_BUTTON = "//*[@id=\"modal-delete-tracker\"]/div/div/div/div/div[3]/button[1]";
	private static final String DELETE_CHECKLIST_CONFIRM_MODAL_BUTTON = "//*[@id=\"modal-delete-checklist\"]/div/div/div/div/div[3]/button[2]";
	private static final String DELETE_CHECKLIST_CANCEL_MODAL_BUTTON = "//*[@id=\"modal-delete-checklist\"]/div/div/div/div/div[3]/button[1]";
	private static final String DELETE_CHECKLIST_ITEM_CONFIRM_MODAL_BUTTON = "//*[@id=\"modal-delete-checklist-item\"]/div/div/div/div/div[3]/button[2]";
	private static final String DELETE_CHECKLIST_ITEM_CANCEL_MODAL_BUTTON = "//*[@id=\"modal-delete-checklist-item\"]/div/div/div/div/div[3]/button[1]";

	private static final String ADD_CHECKLIST_BUTTON = "add-checklist";
	private static final String CHECKLIST_NAME_FIELD = "name-input";
	private static final String CHECKLIST_NAME_FIELD_ERROR = "common-control-message";
	private static final String SAVE_CHECKLIST_BUTTON = "btn-create-checklist";
	
	private static final String YES_BUTTON_RECOVERY_ON_CARD = "btn-yes";
	private static final String NO_BUTTON_RECOVERY_ON_CARD = "btn-no";

	public TrackerCardPage(SeleniumConfig seleniumConfig) {
		this.driver = seleniumConfig.getDriver();
		this.seleniumConfig = seleniumConfig;
	}

	public WebElement getCardName() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CARD_NAME)));
		return this.driver.findElement(By.id(CARD_NAME));
	}

	public WebElement getCardDescription() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CARD_DESCRIPTION)));
		return this.driver.findElement(By.id(CARD_DESCRIPTION));
	}

	public WebElement getCardDueDate() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CARD_DUE_DATE)));
		return this.driver.findElement(By.id(CARD_DUE_DATE));
	}

	public WebElement getCardTag(String id) {
		String tagId = String.format(CARD_TAG, id);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(tagId)));
		return this.driver.findElement(By.id(tagId));
	}

	public WebElement getCardMember(String id) {
		String tagId = String.format(CARD_MEMBER, id);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(tagId)));
		return this.driver.findElement(By.id(tagId));
	}

	public WebElement getCardAttributeName(String id) {
		String tagId = String.format(CARD_ATTRIBUTE_NAME, id);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(tagId)));
		return this.driver.findElement(By.id(tagId));
	}

	public WebElement getCardAttributeValue(String id) {
		String tagId = String.format(CARD_ATTRIBUTE_VALUE, id);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(tagId)));
		return this.driver.findElement(By.id(tagId));
	}

	public WebElement getCardAttributeEmpty(String id) {
		WebElement brother = this.getCardAttributeName(id);
		return brother.findElement(By.xpath(CommonData.PARENT_ELEMENT)).findElement(By.tagName(CommonData.TAG_P));
	}

	public List<WebElement> getChecklistsLabels() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfAllElements(this.driver.findElements(By.id(CHECKLISTS_LABELS))));
		return this.driver.findElements(By.id(CHECKLISTS_LABELS));
	}

	public List<WebElement> getChecklistsInEdition() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOf(this.getAddCheckListButton()));
		return this.driver.findElements(By.id(CHECKLISTS_LABELS));
	}

	public WebElement getChecklist(String frontId) {
		Integer frontIdInt = Integer.parseInt(frontId);
		WebElement checklist = this.getChecklistsLabels().get(frontIdInt).findElement(By.xpath("./../../.."));
		return checklist;
	}

	public List<WebElement> getChecklistItems(String checklistFrontId) {
		WebElement checklist = this.getChecklist(checklistFrontId);
		List<WebElement> itemsList = checklist.findElements(By.id(CHECKLIST_ITEMS_LIST));
		this.seleniumConfig.getWait().until(
				ExpectedConditions.visibilityOf(checklist));
		return itemsList;
	}

	public WebElement getChecklistItemField(String id) {
		String itemId = String.format(CHECKLIST_ITEM_FIELD, id);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(itemId)));
		return this.driver.findElement(By.id(itemId));
	}

	public WebElement getChecklistNameError(String id) {
		String itemId = String.format(CHECKLIST_ITEM_FIELD, id);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(itemId)));
		return this.driver.findElement(By.id(itemId)).findElement(By.xpath(CommonData.PARENT_ELEMENT))
				.findElement(By.tagName(CARD_ERROR_LABEL));
	}

	public WebElement getChecklistItemNameError(String id) {
		String itemId = String.format(CHECKLIST_ITEM_FIELD, id);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(itemId)));
		return this.driver.findElement(By.id(itemId)).findElement(By.xpath("./../../.."))
				.findElement(By.tagName(CARD_ERROR_LABEL));
	}

	public WebElement getAddCheckListItemButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id(ADD_CHECKLIST_ITEM_BUTTON))));
		return this.driver.findElement(By.id(ADD_CHECKLIST_ITEM_BUTTON));
	}

	public WebElement getCardNameField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CARD_NAME_FIELD)));
		return this.driver.findElement(By.id(CARD_NAME_FIELD));
	}

	public WebElement getCardNameErrorLabel() {
		WebElement element = this.driver.findElements(By.tagName(CARD_ERROR_LABEL)).get(0);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOf(element));
		return element;
	}

	public WebElement getCardDueDateField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CARD_DUE_DATE_FIELD)));
		return this.driver.findElement(By.id(CARD_DUE_DATE_FIELD));
	}

	public WebElement getCardDescriptionField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.id(CARD_DESCRIPTION_FIELD)));
		return this.driver.findElement(By.id(CARD_DESCRIPTION_FIELD));
	}

	public WebElement getCardMemberDropdown() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CARD_MEMBER_DROPDOWN)));
		return this.driver.findElement(By.id(CARD_MEMBER_DROPDOWN));
	}

	public WebElement getCardMemberDropdownItem(String member) {
		String dropItem = String.format(CARD_MEMBER_DROPDOWN_ITEM, member);
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(dropItem)));
		return this.driver.findElement(By.cssSelector(dropItem));
	}

	public WebElement getCardSaveButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CARD_SAVE_BUTTON)));
		return this.driver.findElement(By.id(CARD_SAVE_BUTTON));
	}
	
	public WebElement getCardBackButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CARD_BACK_BUTTON)));
		return this.driver.findElement(By.id(CARD_BACK_BUTTON));
	}

	public WebElement getCardSaveAndContinueButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.id(CARD_SAVE_AND_CONTINUE_BUTTON)));
		return this.driver.findElement(By.id(CARD_SAVE_AND_CONTINUE_BUTTON));
	}

	public WebElement getAddCheckListButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(ADD_CHECKLIST_BUTTON)));
		return this.driver.findElement(By.id(ADD_CHECKLIST_BUTTON)).findElement(By.tagName(CommonData.TAG_BUTTON));
	}

	public WebElement getChecklistNameField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CHECKLIST_NAME_FIELD)));
		return this.driver.findElement(By.id(CHECKLIST_NAME_FIELD));
	}

	public WebElement getChecklistNameFieldError() {
		WebElement checklistError = this.getChecklistNameField().findElement(By.xpath(CommonData.PARENT_ELEMENT))
				.findElement(By.tagName(CHECKLIST_NAME_FIELD_ERROR));
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOf(checklistError));
		return checklistError;
	}

	public WebElement getSaveCheckListButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id(SAVE_CHECKLIST_BUTTON))));
		return this.driver.findElement(By.id(SAVE_CHECKLIST_BUTTON));
	}

	public WebElement getDeleteCardButton() {
		WebElement deleteBtn = this.driver.findElement(By.id(DELETE_CARD_BUTTON))
				.findElement(By.tagName(CommonData.TAG_BUTTON));
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOf(deleteBtn));
		return deleteBtn;
	}
	
	public WebElement getCardConfirmRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DELETE_CARD_CONFIRM_MODAL_BUTTON)));
		return this.driver.findElement(By.xpath(DELETE_CARD_CONFIRM_MODAL_BUTTON));
	}
	
	public WebElement getCardCancelRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DELETE_CARD_CANCEL_MODAL_BUTTON)));
		return this.driver.findElement(By.xpath(DELETE_CARD_CANCEL_MODAL_BUTTON));
	}
	
	public WebElement getChecklistConfirmRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DELETE_CHECKLIST_CONFIRM_MODAL_BUTTON)));
		return this.driver.findElement(By.xpath(DELETE_CHECKLIST_CONFIRM_MODAL_BUTTON));
	}
	
	public WebElement getChecklistCancelRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DELETE_CHECKLIST_CANCEL_MODAL_BUTTON)));
		return this.driver.findElement(By.xpath(DELETE_CHECKLIST_CANCEL_MODAL_BUTTON));
	}
	
	public WebElement getChecklistItemConfirmRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DELETE_CHECKLIST_ITEM_CONFIRM_MODAL_BUTTON)));
		return this.driver.findElement(By.xpath(DELETE_CHECKLIST_ITEM_CONFIRM_MODAL_BUTTON));
	}
	
	public WebElement getChecklistItemCancelRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DELETE_CHECKLIST_ITEM_CANCEL_MODAL_BUTTON)));
		return this.driver.findElement(By.xpath(DELETE_CHECKLIST_ITEM_CANCEL_MODAL_BUTTON));
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
