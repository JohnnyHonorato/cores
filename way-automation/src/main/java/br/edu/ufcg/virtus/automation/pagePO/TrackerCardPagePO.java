package br.edu.ufcg.virtus.automation.pagePO;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.dataset.ConfigData;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.page.TrackerCardPage;

public class TrackerCardPagePO {

	public TrackerCardPage trackerCardPage;

	public CommonPage commonPage;
	private CommonPagePO commonPagePO;
	private TrackerBoardPagePO trackerBoardPagePO;

	public TrackerCardPagePO(SeleniumConfig seleniumConfig, CommonPage commonPage) {
		this.trackerCardPage = new TrackerCardPage(seleniumConfig);
		this.commonPage = commonPage;
		this.commonPagePO = new CommonPagePO(seleniumConfig);
		this.trackerBoardPagePO = new TrackerBoardPagePO(seleniumConfig, this.commonPagePO.commonPage);
	}

	public void fillCardName(String name) {
		WebElement nameField = this.trackerCardPage.getCardNameField();
		this.trackerCardPage.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(nameField));
		this.trackerCardPage.getCardNameField().clear();
		this.trackerCardPage.getCardNameField().sendKeys(name);
	}

	public void fillCardDueDate(String dueDate) {
		WebElement dueDateField = this.trackerCardPage.getCardDueDateField();
		this.trackerCardPage.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(dueDateField));
		this.trackerCardPage.getCardDueDateField().clear();
		this.trackerCardPage.getCardDueDateField().sendKeys(dueDate);
	}

	public void fillCardDescription(String description) {
		WebElement descriptionField = this.trackerCardPage.getCardDescriptionField();
		this.trackerCardPage.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(descriptionField));
		this.trackerCardPage.getCardDescriptionField().clear();
		this.trackerCardPage.getCardDescriptionField().sendKeys(description);
	}

	public void fillCardMemberDropbox(String member) {
		WebElement memberDropbox = this.trackerCardPage.getCardMemberDropdown();
		this.trackerCardPage.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(memberDropbox));
		memberDropbox.click();
		this.trackerCardPage.getCardMemberDropdownItem(member).click();
	}

	public void fillChecklistName(String name) {
		this.trackerCardPage.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOf(this.trackerCardPage.getChecklistNameField()));
		this.trackerCardPage.getChecklistNameField().clear();
		this.trackerCardPage.getChecklistNameField().sendKeys(name);
		this.trackerCardPage.getChecklistNameField().sendKeys(Keys.TAB);
	}

	public void fillChecklistItemName(String name, String checklistFrontId) {
		this.trackerCardPage.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOf(this.trackerCardPage.getChecklistItemField(checklistFrontId)));
		this.trackerCardPage.getChecklistItemField(checklistFrontId).clear();
		this.trackerCardPage.getChecklistItemField(checklistFrontId).sendKeys(name);
		this.trackerCardPage.getChecklistItemField(checklistFrontId).sendKeys(Keys.TAB);
	}

	public void insertCard(String boardName, String statusId, String cardName, String dueDate, String description) {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.openBoard(boardName);
		this.trackerBoardPagePO.clickAddCardButton(statusId);
		this.fillCardName(cardName);
		this.fillCardDueDate(dueDate);
		this.fillCardDescription(description);
	}

	public void insertCard(String boardName, String statusId, String cardName, String dueDate, String description,
			List<String> members) {
		this.commonPagePO.goToTrackerPage();
		this.trackerBoardPagePO.openBoard(boardName);
		this.trackerBoardPagePO.clickAddCardButton(statusId);
		this.fillCardName(cardName);
		this.fillCardDueDate(dueDate);
		this.fillCardDescription(description);
		for (String member : members) {
			this.fillCardMemberDropbox(member);
		}
	}

	public void insertChecklist(String name) {
		this.trackerCardPage.getAddCheckListButton().click();
		this.trackerCardPage.getChecklistNameField().sendKeys(name);

	}

	public void editChecklist(String oldName, String newName) {
		List<WebElement> checkLists = this.trackerCardPage.getChecklistsInEdition();
		for (WebElement checklist : checkLists) {
			if (checklist.getText().equals(oldName)) {
				checklist.findElement(By.xpath("./../..")).findElement(By.id(TrackerCardPage.CHECKLIST_EDITION_BUTTON))
						.click();
			}
		}

		this.fillChecklistName(newName);
	}

	public void editChecklistItem(String checklist, String oldItem, String newItem) {
		List<WebElement> checkLists = this.trackerCardPage.getChecklistsInEdition();
		List<WebElement> checkListItems = new ArrayList<WebElement>();
		Integer contadorChecklist = -1;
		Integer checklistFrontId = null;
		for (WebElement cList : checkLists) {
			contadorChecklist++;
			if (cList.getText().equals(checklist)) {
				checklistFrontId = contadorChecklist;
				checkListItems.add(cList.findElement(By.xpath("./../../.."))
						.findElement(By.id(TrackerCardPage.CHECKLIST_ITEMS_LIST)));
			}
		}

		for (WebElement item : checkListItems) {
			if (item.getText().equals(oldItem)) {
				item.findElement(By.xpath("./../../.."))
						.findElement(By.id(TrackerCardPage.CHECKLIST_ITEM_EDITION_BUTTON)).click();
			}
		}
		this.fillChecklistItemName(newItem, checklistFrontId.toString());
	}

	public void deleteChecklist(String name) {
		List<WebElement> checkLists = this.trackerCardPage.getChecklistsInEdition();
		for (WebElement checklist : checkLists) {
			if (checklist.getText().equals(name)) {
				checklist.findElement(By.xpath("./../..")).findElement(By.id(TrackerCardPage.CHECKLIST_DELETE_BUTTON))
						.click();
			}
		}
	}

	public void deleteChecklistItem(String checklist, String itemName) {
		List<WebElement> checkLists = this.trackerCardPage.getChecklistsInEdition();
		List<WebElement> checkListItems = new ArrayList<WebElement>();

		for (WebElement cList : checkLists) {
			if (cList.getText().equals(checklist)) {
				checkListItems.add(cList.findElement(By.xpath("./../../.."))
						.findElement(By.id(TrackerCardPage.CHECKLIST_ITEMS_LIST)));
			}
		}

		for (WebElement item : checkListItems) {
			if (item.getText().equals(itemName)) {
				item.findElement(By.xpath("./../../..")).findElement(By.id(TrackerCardPage.CHECKLIST_DELETE_BUTTON))
						.click();
			}
		}
	}

	public void goToCardEdition(String boardId, String cardId) {
		this.commonPage.driver.get(ConfigData.BUSINESS_APPLICATION_URL + "tracker/" + boardId + "/card/edit/" + cardId);
		this.trackerCardPage.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOf(this.trackerCardPage.getCardNameField()));
	}

	public Boolean isItemDisplayedOnChecklisTable(String checkListFrontId, String item) {
		List<WebElement> itemsList = this.trackerCardPage.getChecklistItems(checkListFrontId);
		for (WebElement o : itemsList) {
			String insertedItemName = o.getText();
			if (insertedItemName.equals(item))
				return true;
		}
		return false;
	}

	public Boolean isChecklistDisplayedInEdition(String checkListName) {
		List<WebElement> itemsList = this.trackerCardPage.getChecklistsInEdition();
		for (WebElement o : itemsList) {
			if (o.getText().equals(checkListName))
				return true;
		}
		return false;
	}
}
