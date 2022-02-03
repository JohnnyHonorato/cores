package br.edu.ufcg.virtus.automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;

public class CommonPage {

	public WebDriver driver;
	public SeleniumConfig seleniumConfig;

	public static final String ADD_BUTTON = "btn-add-server";
	public static final String BACK_BUTTON = "btn-back";
	public static final String SAVE_BUTTON = "btn-save";
	public static final String DELETE_BUTTON = "btn-delete";
	public static final String EDIT_BUTTON = "btn-edit";
	public static final String TOAST = "toast-container";
	public static final String CLOSE_REMOVAL_BUTTON = "btn-close";
	public static final String TABLE_LOADING = "div[class='lds-ripple loading-middle']";
	private static final String CONFIRM__REMOVAL_BUTTON = "//*[@id=\"modal-delete\"]/div/div/div/div/div[3]/button[2]";

	// Contact Info Tab
	public static final String EMAIL_TYPE_FIELD = "select-type-email";
	public static final String DOMAIN_FIELD = "select-type-domain";
	public static final String PHONE_TYPE_FIELD = "select-type-phone";

	public CommonPage(SeleniumConfig seleniumConfig) {
		this.driver = seleniumConfig.getDriver();
		this.seleniumConfig = seleniumConfig;
	}

	public WebElement getAddButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(By.id(ADD_BUTTON)));
		return this.driver.findElement(By.id(ADD_BUTTON));
	}

	public WebElement getBackButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(By.id(BACK_BUTTON)));
		return this.driver.findElement(By.id(BACK_BUTTON));
	}

	public WebElement getSaveButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(SAVE_BUTTON)));
		return this.driver.findElement(By.id(SAVE_BUTTON));
	}

	public WebElement getDeleteButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(By.id(DELETE_BUTTON)));
		return this.driver.findElement(By.id(DELETE_BUTTON));
	}

	public WebElement getEditButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(By.id(EDIT_BUTTON)));
		return this.driver.findElement(By.id(EDIT_BUTTON));
	}

	public WebElement getToast() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(TOAST)));
		return this.driver.findElement(By.id(TOAST));
	}

	public WebElement getCloseModalRemovalButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CLOSE_REMOVAL_BUTTON)));
		return this.driver.findElement(By.id(CLOSE_REMOVAL_BUTTON));
	}

	public WebElement getEmailTypeField() {
		return this.driver.findElement(By.id(EMAIL_TYPE_FIELD));
	}

	public WebElement getDomainField() {
		return this.driver.findElement(By.id(DOMAIN_FIELD));
	}

	public WebElement getPhoneTypeField() {
		return this.driver.findElement(By.id(PHONE_TYPE_FIELD));
	}

	public WebElement getConfirmRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CONFIRM__REMOVAL_BUTTON)));
		return this.driver.findElement(By.xpath(CONFIRM__REMOVAL_BUTTON));
	}
}
