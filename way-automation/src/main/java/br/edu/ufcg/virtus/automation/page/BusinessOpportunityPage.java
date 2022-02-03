package br.edu.ufcg.virtus.automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;

public class BusinessOpportunityPage {

	public WebDriver driver;
	public SeleniumConfig seleniumConfig;

	private static final String BUSINESS_CARD_MENU = "div[ng-reflect-router-link='/business/']";
	private static final String BUSINESS_OPORTUNITY_MENU = "a[ng-reflect-router-link='/business/business-oportunitie']";
	private static final String SEARCH_OPORTUNITY_FIELD = "/html/body/business-root/div/div/div[2]/div/business-opportunity-list/common-data-table/div[1]/common-search-list/input";

	private static final String OPORTUNITY_TITLE_FIELD = "input[formcontrolname='title']";
	private static final String ORGANIZATION_DROPBOX = "select[formcontrolname='organizationId']";
	private static final String DESCRIPTION_FIELD = "textarea[formcontrolname='description']";
	private static final String TITLE_FIELD_ERROR_LABEL = "/html/body/business-root/div/div/div[2]/div/business-opportunity-edit/div/div/form/div/div[2]/div[1]/common-control-message/label";
	private static final String ORGANIZATION_DROPBOX_ERROR_LABEL = "/html/body/business-root/div/div/div[2]/div/business-opportunity-edit/div/div/form/div/div[2]/div[2]/common-control-message/label";
	private static final String DESCRIPTION_ERROR_LABEL = "/html/body/business-root/div/div/div[2]/div/business-opportunity-edit/div/div/form/div/div[3]/div/common-control-message/label";
	private static final String BUSINESS_OPORTUNITY_TABLE = "/html/body/business-root/div/div/div[2]/div/business-opportunity-list/common-data-table/table/tbody";

	private static final String REPRESENTATIVE_DROPBOX = "select[formcontrolname='peopleId']";
	private static final String ADD_REPRESENTATIVE_BUTTON = "btn btn-primary centered-button";

	private static final String CONFIRM_REMOVAL_BUTTON_MODAL = "button[class='btn btn-primary']";
	private static final String CANCEL_REMOVAL_BUTTON_MODAL = "button[class='btn btn-default']";

	public BusinessOpportunityPage(SeleniumConfig seleniumConfig) {
		this.driver = seleniumConfig.getDriver();
		this.seleniumConfig = seleniumConfig;
	}

	public WebElement getBusinessCardMenu() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUSINESS_CARD_MENU)));
		return this.driver.findElement(By.cssSelector(BUSINESS_CARD_MENU));
	}

	public WebElement getBusinessOpportunityMenu() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUSINESS_OPORTUNITY_MENU)));
		return this.driver.findElement(By.cssSelector(BUSINESS_OPORTUNITY_MENU));
	}

	public WebElement getSearchBusinessOpportunityField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_OPORTUNITY_FIELD)));
		return this.driver.findElement(By.xpath(SEARCH_OPORTUNITY_FIELD));
	}

	public WebElement getTitleField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(OPORTUNITY_TITLE_FIELD)));
		return this.driver.findElement(By.cssSelector(OPORTUNITY_TITLE_FIELD));
	}

	public WebElement getOrganizationDropbox() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ORGANIZATION_DROPBOX)));
		return this.driver.findElement(By.cssSelector(ORGANIZATION_DROPBOX));
	}

	public WebElement getDescriptionField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(DESCRIPTION_FIELD)));
		return this.driver.findElement(By.cssSelector(DESCRIPTION_FIELD));
	}

	public WebElement getTitleFieldErrorLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TITLE_FIELD_ERROR_LABEL)));
		return this.driver.findElement(By.xpath(TITLE_FIELD_ERROR_LABEL));
	}

	public WebElement getOrganizationErrorLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ORGANIZATION_DROPBOX_ERROR_LABEL)));
		return this.driver.findElement(By.xpath(ORGANIZATION_DROPBOX_ERROR_LABEL));
	}

	public WebElement getDescriptionErrorLabel() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DESCRIPTION_ERROR_LABEL)));
		return this.driver.findElement(By.xpath(DESCRIPTION_ERROR_LABEL));
	}

	public WebElement getRepresentativeDropbox() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(REPRESENTATIVE_DROPBOX)));
		return this.driver.findElement(By.cssSelector(REPRESENTATIVE_DROPBOX));
	}

	public WebElement getAddRepresentativeButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.className(ADD_REPRESENTATIVE_BUTTON)));
		return this.driver.findElement(By.cssSelector(ADD_REPRESENTATIVE_BUTTON));
	}

	public WebElement getBusinessOpportunityTable() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BUSINESS_OPORTUNITY_TABLE)));
		return this.driver.findElement(By.xpath(BUSINESS_OPORTUNITY_TABLE));
	}

	public WebElement getConfirmRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CONFIRM_REMOVAL_BUTTON_MODAL)));
		return this.driver.findElement(By.cssSelector(CONFIRM_REMOVAL_BUTTON_MODAL));
	}

	public WebElement getCancelRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CANCEL_REMOVAL_BUTTON_MODAL)));
		return this.driver.findElement(By.cssSelector(CANCEL_REMOVAL_BUTTON_MODAL));
	}

}
