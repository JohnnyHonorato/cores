package br.edu.ufcg.virtus.automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;

public class ModulesPage {

	public WebDriver driver;
	public SeleniumConfig seleniumConfig;

	private static final String CORE_PAGE_CARD = "/html/body/app-root/div/div/div[2]/div[1]/div";
	private final static String MODULES_MENU = "/html/body/core-root/div/div/div[1]/core-sidebar/div/a[3]";
	private final static String ADD_MODULES_BUTTON = "btn-add-server";
	private final static String MODULES_TABLE = "/html/body/core-root/div/div/div[2]/div/core-module-list/div/common-data-table/table/tbody";
	private final static String SEARCH_INPUT = "/html/body/core-root/div/div/div[2]/div/core-module-list/div/common-data-table/div[1]/common-search-list/input";
	private final static String NO_MODULE_FOUND_LABEL = "/html/body/core-root/div/div/div[2]/div/core-module-list/div/common-data-table/table/tbody/tr/td/strong";
	private final static String MODULE_NAME_FIELD = "input-module-name";
	private final static String MODULE_LINK_FIELD = "input-module-link";
	private final static String MODULE_ICON_FIELD = "input-module-icon";
	private final static String MODULE_OPEN_MODE_SELECT = "select-module-open-mode";
	private final static String MODULE_MODE_SPA_PATH = "input-module-path-name";
	private final static String PUBLIC_MODE_TOOGLE = "span";
	private final static String PERMISSIONS_ADD_BUTTON = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[4]/button";
	private final static String REMOVE_PERMISSIONS_BUTTON = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[5]/div/button";
	private final static String PERMISSIONS_NAME_FIELD = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[5]/div/div/div[1]/input";
	private final static String PERMISSIONS_LABEL_FIELD = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[5]/div/div/div[2]/input";
	private final static String PERMISSIONS_DESCRIPTION_FIELD = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[5]/div/div/div[3]/input";
	private final static String INVALID_NAME_LABEL = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[2]/div[1]/common-control-message/label";
	private final static String INVALID_LINK_LABEL = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[2]/div[2]/common-control-message/label";
	private final static String INVALID_ICON_LABEL = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[3]/div[1]/common-control-message/label";
	private final static String INVALID_OPEN_MODE_LABEL = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[3]/div[2]/common-control-message/label";
	private final static String INVALID_PATH_LABEL = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[3]/div[3]/common-control-message/label";
	private final static String INVALID_PERMISSION_NAME_LABEL = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[5]/div/div/div[1]/common-control-message/label";
	private final static String INVALID_PERMISSION_LABEL_LABEL = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[5]/div/div/div[2]/common-control-message/label";
	private final static String INVALID_PERMISSION_DESCRIPTION_LABEL = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[5]/div/div/div[3]/common-control-message/label";
	private final static String LOADING_TABLE_ELEMENT = "//*[@id=\"page-wrapper\"]/core-module-list/common-data-table/div/div[2]/table/tbody/tr/td/common-loading/div/div/div[1]";
	private final static String YES_BUTTON_DELETE = "//*[@id=\"modal-delete\"]/div/div/div[3]/button[2]";
	private final static String NO_BUTTON_DELETE = "//*[@id=\"modal-delete\"]/div/div/div[3]/button[1]";
	private final static String PERMISSION_DELETE_BUTTON = "/html/body/core-root/div/div/div[2]/div/core-module-edit/form/div/div[5]/div[1]/button";

	public ModulesPage(SeleniumConfig seleniumConfig) {
		this.driver = seleniumConfig.getDriver();
		this.seleniumConfig = seleniumConfig;
	}

	public WebElement getModulesMenu() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MODULES_MENU)));
		return this.driver.findElement(By.xpath(MODULES_MENU));
	}

	public WebElement getAddModulesButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(ADD_MODULES_BUTTON)));
		return this.driver.findElement(By.id(ADD_MODULES_BUTTON));
	}

	public WebElement getModulesTable() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MODULES_TABLE)));
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOADING_TABLE_ELEMENT)));
		return this.driver.findElement(By.xpath(MODULES_TABLE));
	}

	public WebElement getSearchInput() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_INPUT)));
		return this.driver.findElement(By.xpath(SEARCH_INPUT));
	}

	public WebElement getNoModuleFoundLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(NO_MODULE_FOUND_LABEL)));
		return this.driver.findElement(By.xpath(NO_MODULE_FOUND_LABEL));
	}

	public WebElement getModuleNameField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(MODULE_NAME_FIELD)));
		return this.driver.findElement(By.id(MODULE_NAME_FIELD));
	}

	public WebElement getModuleLinkField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(MODULE_LINK_FIELD)));
		return this.driver.findElement(By.id(MODULE_LINK_FIELD));
	}

	public WebElement getModuleIconField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(MODULE_ICON_FIELD)));
		return this.driver.findElement(By.id(MODULE_ICON_FIELD));
	}

	public WebElement getModuleOpenModeSelect() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.id(MODULE_OPEN_MODE_SELECT)));
		return this.driver.findElement(By.id(MODULE_OPEN_MODE_SELECT));
	}

	public WebElement getModuleModeSPAPath() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(MODULE_MODE_SPA_PATH)));
		return this.driver.findElement(By.id(MODULE_MODE_SPA_PATH));
	}

	public WebElement getPublicModeToogle() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(PUBLIC_MODE_TOOGLE)));
		return this.driver.findElement(By.tagName(PUBLIC_MODE_TOOGLE));
	}

	public WebElement getPermissionsAddButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PERMISSIONS_ADD_BUTTON)));
		return this.driver.findElement(By.xpath(PERMISSIONS_ADD_BUTTON));
	}

	public WebElement getRemovePermissionsButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(REMOVE_PERMISSIONS_BUTTON)));
		return this.driver.findElement(By.xpath(REMOVE_PERMISSIONS_BUTTON));
	}

	public WebElement getPermissionsNameField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PERMISSIONS_NAME_FIELD)));
		return this.driver.findElement(By.xpath(PERMISSIONS_NAME_FIELD));
	}

	public WebElement getPermissionsLabelField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PERMISSIONS_LABEL_FIELD)));
		return this.driver.findElement(By.xpath(PERMISSIONS_LABEL_FIELD));
	}

	public WebElement getPermissionsDescriptionField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PERMISSIONS_DESCRIPTION_FIELD)));
		return this.driver.findElement(By.xpath(PERMISSIONS_DESCRIPTION_FIELD));
	}

	public WebElement getInvalidNameLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_NAME_LABEL)));
		return this.driver.findElement(By.xpath(INVALID_NAME_LABEL));
	}

	public WebElement getInvalidLinkLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_LINK_LABEL)));
		return this.driver.findElement(By.xpath(INVALID_LINK_LABEL));
	}

	public WebElement getInvalidIconLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_ICON_LABEL)));
		return this.driver.findElement(By.xpath(INVALID_ICON_LABEL));
	}
	
	public WebElement getInvalidOpenModeLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_OPEN_MODE_LABEL)));
		return this.driver.findElement(By.xpath(INVALID_OPEN_MODE_LABEL));
	}

	public WebElement getInvalidPathLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_PATH_LABEL)));
		return this.driver.findElement(By.xpath(INVALID_PATH_LABEL));
	}

	public WebElement getInvalidPermissionNameLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_PERMISSION_NAME_LABEL)));
		return this.driver.findElement(By.xpath(INVALID_PERMISSION_NAME_LABEL));
	}

	public WebElement getInvalidPermissionLabelLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_PERMISSION_LABEL_LABEL)));
		return this.driver.findElement(By.xpath(INVALID_PERMISSION_LABEL_LABEL));
	}

	public WebElement getInvalidPermissionDescriptionLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_PERMISSION_DESCRIPTION_LABEL)));
		return this.driver.findElement(By.xpath(INVALID_PERMISSION_DESCRIPTION_LABEL));
	}

	public WebElement getCorePageCard() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CORE_PAGE_CARD)));
		return this.driver.findElement(By.xpath(CORE_PAGE_CARD));
	}

	public WebElement getYesOnDeleteModal() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(YES_BUTTON_DELETE)));
		return this.driver.findElement(By.xpath(YES_BUTTON_DELETE));
	}

	public WebElement getNoOnDeleteModal() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(NO_BUTTON_DELETE)));
		return this.driver.findElement(By.xpath(NO_BUTTON_DELETE));
	}

	public WebElement getPermissionDeleteButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PERMISSION_DELETE_BUTTON)));
		return this.driver.findElement(By.xpath(PERMISSION_DELETE_BUTTON));
	}

}
