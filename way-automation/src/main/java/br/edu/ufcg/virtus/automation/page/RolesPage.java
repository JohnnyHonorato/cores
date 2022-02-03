package br.edu.ufcg.virtus.automation.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;

public class RolesPage {

	public WebDriver driver;
	public SeleniumConfig seleniumConfig;

	private static final String ROLES_MENU = "/html/body/core-root/div/div/div[1]/core-sidebar/div/a[4]";
	private static final String SEARCH_ROLE_FIELD = "search-system-role";
	private static final String SEARCH_PERMISSION_FIELD = "/html/body/core-root/div/div/div[2]/div/core-role-edit/div/div[2]/div/div[2]/div/div[1]/input";
	private static final String ROLES_TABLE = "/html/body/core-root/div/div/div[2]/div/core-role-edit/div/div[2]/div/div[1]/div/div[2]/ul";
	private static final String PERMISSIONS_TABLE = "/html/body/core-root/div/div/div[2]/div/core-role-edit/div/div[2]/div/div[2]/div/div[2]/div";
	private static final String MODULES_TABLE = "accordion-item";
	private static final String ALL_PERMISSIONS_CHECKBOX = "flexCheckIndeterminate";
	private static final String UPDATE_BUTTON = "bt-update-role";

	private static final String ROLE_NAME_FIELD = "name-role";
	private static final String REQUIRED_NAME_LABEL = "/html/body/core-root/div/div/div[2]/div/core-role-edit/div/div/div/form/div[1]/div/common-control-message/label";

	private static final String CONFIRM_REMOVAL_BUTTON_MODAL = "//*[@id=\"modal-delete-role\"]/div/div/div[3]/button[2]";
	private static final String CANCEL_REMOVAL_BUTTON_MODAL = "//*[@id=\"modal-delete-role\"]/div/div/div[3]/button[1]";

	public RolesPage(SeleniumConfig seleniumConfig) {
		this.driver = seleniumConfig.getDriver();
		this.seleniumConfig = seleniumConfig;
	}

//GETTERS
	public WebElement getRolesMenu() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ROLES_MENU)));
		return this.driver.findElement(By.xpath(ROLES_MENU));
	}

	public WebElement getSearchRoleField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(SEARCH_ROLE_FIELD)));
		return this.driver.findElement(By.id(SEARCH_ROLE_FIELD));
	}

	public WebElement getSearchPermissionField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_PERMISSION_FIELD)));
		return this.driver.findElement(By.xpath(SEARCH_PERMISSION_FIELD));
	}

	public WebElement getRolesTable() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(RolesPage.ROLES_TABLE)));
		return this.driver.findElement(By.xpath(RolesPage.ROLES_TABLE));
	}

	public WebElement getPermissionsTable() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(RolesPage.PERMISSIONS_TABLE)));
		return this.driver.findElement(By.xpath(RolesPage.PERMISSIONS_TABLE));
	}

	public List<WebElement> getModulesTable() {
		this.seleniumConfig.getWait().until(ExpectedConditions
				.visibilityOfAllElements(this.getPermissionsTable().findElements(By.className(RolesPage.MODULES_TABLE))));
		return this.getPermissionsTable().findElements(By.className(RolesPage.MODULES_TABLE));
	}

	public WebElement getUpdateButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(UPDATE_BUTTON)));
		return this.driver.findElement(By.id(UPDATE_BUTTON));
	}

	public List<WebElement> getAllPermissionsCheckbox() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.id(RolesPage.ALL_PERMISSIONS_CHECKBOX)));
		return this.driver.findElements(By.id(RolesPage.ALL_PERMISSIONS_CHECKBOX));
	}

	public WebElement getRoleNameField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(ROLE_NAME_FIELD)));
		return this.driver.findElement(By.id(ROLE_NAME_FIELD));
	}

	public WebElement getRequiredNameLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(REQUIRED_NAME_LABEL)));
		return this.driver.findElement(By.xpath(REQUIRED_NAME_LABEL));
	}

	public WebElement getConfirmRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CONFIRM_REMOVAL_BUTTON_MODAL)));
		return this.driver.findElement(By.xpath(CONFIRM_REMOVAL_BUTTON_MODAL));
	}

	public WebElement getCancelRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CANCEL_REMOVAL_BUTTON_MODAL)));
		return this.driver.findElement(By.xpath(CANCEL_REMOVAL_BUTTON_MODAL));
	}

}
