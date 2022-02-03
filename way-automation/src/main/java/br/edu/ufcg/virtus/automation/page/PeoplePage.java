package br.edu.ufcg.virtus.automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;

public class PeoplePage {
	
	public SeleniumConfig seleniumConfig;
	public WebDriver driver;
	
	private static final String MENU_PEOPLE = "/html/body/core-root/div/div/div[1]/core-sidebar/div/a[2]";
	private static final String PEOPLE_TABLE = "/html/body/core-root/div/div/div[2]/div/core-people-list/div/common-data-table/table/tbody";
	private static final String LOADING_TABLE_ELEMENT = "<div _ngcontent-gjh-c87=\"\"></div>";
	private static final String NO_USER_FOUND_LABEL = "/html/body/core-root/div/div/div[2]/div/core-people-list/div/common-data-table/table/tbody/tr/td/strong";
	private static final String REMOVE_PEOPLE_BUTTON = "btn-delete";
	private static final String REMOVE_MODAL_NO_BUTTON = "//*[@id=\"modal-delete\"]/div/div/div[3]/button[1]";
	private static final String REMOVE_MODAL_YES_BUTTON = "//*[@id=\"modal-delete\"]/div/div/div[3]/button[2]";
	private static final String SEARCH_FIELD = "/html/body/core-root/div/div/div[2]/div/core-people-list/div/common-data-table/div[1]/common-search-list/input";
	
	private static final String PEOPLE_NAME_FIELD = "/html/body/core-root/div/div/div[2]/div/core-people-edit/div/div/div/form/div[1]/div[1]/div[1]/input";
	private static final String HOW_TO_BE_CALLED_FIELD = "input[formcontrolname='nickname']";
	private static final String CPF = "input-cpf";
	private static final String PEOPLE_INVALID_NAME_LABEL = "/html/body/core-root/div/div/div[2]/div/core-people-edit/div/div/div/form/div[1]/div[1]/div[1]/common-control-message/label";
	private static final String HOW_TO_BE_CALLED_INVALID_LABEL = "/html/body/core-root/div/div/div[2]/div/core-people-edit/div/div/div/form/div[1]/div[1]/div[2]/common-control-message/label";
	private static final String CPF_INVALID_LABEL = "/html/body/core-root/div/div/div[2]/div/core-people-edit/div/div/div/form/div[1]/div[1]/div[3]/common-control-message/label";

	private static final String CONTACT_INFO_TAB = "//*[contains(text(),'Informações para contato')]";
	
	private static final String EMAIL_TAB = "//*[@id=\"collapseOne\"]/div/common-contact-info/ul/li[1]";
	private static final String EMAIL_FIELD = "input[formcontrolname='email']";
	private static final String EMAIL_TYPE_SELECT = "select-type-email";
	private static final String INVALID_EMAIL_LABEL = "//*[@id=\"tab1\"]/form/div/div[1]/common-control-message/label";
	private static final String ADD_EMAIL_BUTTON = "//*[@id=\"tab1\"]/form/div/button";
	private static final String EDIT_EMAIL_BUTTON = "//*[@id=\"tab1\"]/div/div/table/tbody/tr[2]/td[3]/button[1]";
	
	private static final String PHONE_TAB = "//*[@id=\"collapseOne\"]/div/common-contact-info/ul/li[2]";
	private static final String PHONE_CODE = "input[formcontrolname='phoneCountryCode']";
	private static final String PHONE_NUMBER = "input[formcontrolname='phone']";
	private static final String PHONE_TYPE_SELECT = "select-type-phone";
	private static final String COUNTRY_CODE_INVALID_LABEL = "//*[@id=\"tab2\"]/form/div/div[1]/common-control-message/label";
	private static final String PHONE_NUMBER_INVALID_LABEL = "//*[@id=\"tab2\"]/form/div/div[2]/common-control-message/label";
	private static final String ADD_PHONE_BUTTON = "//*[@id=\"tab2\"]/form/div/button";

	private static final String ADDRESS_TAB = "//*[@id=\"collapseOne\"]/div/common-contact-info/ul/li[3]";
	private static final String ADDRESS_TYPE_SELECT = "select-type-domain";
	private static final String ADDRESS_CEP = "input[formcontrolname='addressZipCode']";
	private static final String ADDRESS_STREET = "input[formcontrolname='addressStreet']";
	private static final String ADDRESS_NUMBER = "input[formcontrolname='addressNumber']";
	private static final String ADDRESS_COMPLEMENT = "input[formcontrolname='addressComplement']";
	private static final String ADDRESS_NEIGHBORHOOD = "input[formcontrolname='addressNeighborhood']";
	private static final String ADDRESS_CITY = "input[formcontrolname='addressCity']";
	private static final String ADDRESS_STATE = "input[formcontrolname='addressState']";
	private static final String ADDRESS_COUNTRY = "input[formcontrolname='addressCountry']";
	private static final String CEP_INVALID_LABEL = "//*[@id=\"tab3\"]/form/div/div[1]/div[2]/common-control-message/label";
	private static final String STREET_INVALID_LABEL = "//*[@id=\"tab3\"]/form/div/div[1]/div[3]/common-control-message/label";
	private static final String NUMBER_INVALID_LABEL = "//*[@id=\"tab3\"]/form/div/div[1]/div[4]/common-control-message/label";
	private static final String COMPLEMENT_INVALID_LABEL = "//*[@id=\"tab3\"]/form/div/div[1]/div[5]/common-control-message/label";
	private static final String NEIGHBORHOOD_INVALID_LABEL = "//*[@id=\"tab3\"]/form/div/div[1]/div[6]/common-control-message/label";
	private static final String CITY_INVALID_LABEL = "//*[@id=\"tab3\"]/form/div/div[1]/div[7]/common-control-message/label";
	private static final String STATE_INVALID_LABEL = "//*[@id=\"tab3\"]/form/div/div[1]/div[8]/common-control-message/label";
	private static final String COUNTRY_INVALID_LABEL = "//*[@id=\"tab3\"]/form/div/div[1]/div[9]/common-control-message/label";
	private static final String ADD_ADDRESS_BUTTON = "//*[@id=\"tab3\"]/form/div/div[2]/button";
	
	private static final String SAVE_BUTTON = "btn-save";
	private static final String EDIT_BUTTON = "btn-edit";
	
	private static final String INVALID_USER_LABEL = "/html/body/core-root/div/div/div[2]/div/core-people-edit/div/div/div/form/div[2]/div[2]/div/div[1]/common-control-message/label";
	private static final String ACCESS_DATA_TAB = "/html/body/core-root/div/div/div[2]/div/core-people-edit/div/ul/li[2]";
	private static final String USER_LOGIN_FIELD = "user-username";
	private static final String USER_ROLES_SELECT = "//*[@id=\"select-approvers-payroll\"]/div/div/div[2]/input";
	private static final String USER_ACCESS_TOOGLE = "checkbox-agreement";
	
	public PeoplePage(SeleniumConfig seleniumConfig) {
		this.driver = seleniumConfig.getDriver();
	    this.seleniumConfig = seleniumConfig;
	}
	
	public WebElement getMenuPeople() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MENU_PEOPLE)));
		return this.driver.findElement(By.xpath(MENU_PEOPLE));
	}
	
	public WebElement getAddButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CommonPage.ADD_BUTTON)));
		return this.driver.findElement(By.id(CommonPage.ADD_BUTTON));
	}
	
	public WebElement getContactInfoCard() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CONTACT_INFO_TAB)));
		return this.driver.findElement(By.xpath(CONTACT_INFO_TAB));
	}
	
	public WebElement getPeopleNameField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PEOPLE_NAME_FIELD)));
		return this.driver.findElement(By.xpath(PEOPLE_NAME_FIELD));
	}
	
	public WebElement getEmailField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(EMAIL_FIELD)));
		return this.driver.findElement(By.cssSelector(EMAIL_FIELD));
	}

	public WebElement getEmailTypeField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(EMAIL_TYPE_SELECT)));
		return this.driver.findElement(By.id(EMAIL_TYPE_SELECT));
	}
	
	public WebElement getEmailAddButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADD_EMAIL_BUTTON)));
		return this.driver.findElement(By.xpath(ADD_EMAIL_BUTTON));
	}
	
	public WebElement getPhoneAddButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADD_PHONE_BUTTON)));
		return this.driver.findElement(By.xpath(ADD_PHONE_BUTTON));
	}
		
	public WebElement getSaveButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(SAVE_BUTTON)));
		return this.driver.findElement(By.id(SAVE_BUTTON));
	}

	public WebElement getPeopleTable() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PEOPLE_TABLE)));
		this.seleniumConfig.getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOADING_TABLE_ELEMENT)));
		return this.driver.findElement(By.xpath(PEOPLE_TABLE));
	}
	
	public WebElement getInvalidEmailLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_EMAIL_LABEL)));
		return this.driver.findElement(By.xpath(INVALID_EMAIL_LABEL));
	}
	
	public WebElement getSearchBarField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_FIELD)));
		return this.driver.findElement(By.xpath(SEARCH_FIELD));
	}
	
	public WebElement getNoUserFoundLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(NO_USER_FOUND_LABEL)));
		return this.driver.findElement(By.xpath(NO_USER_FOUND_LABEL));
	}

	public WebElement getRemoveButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(REMOVE_PEOPLE_BUTTON)));
		return this.driver.findElement(By.id(REMOVE_PEOPLE_BUTTON));
	}
	
	public WebElement getRemoveModalNoButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(REMOVE_MODAL_NO_BUTTON)));
		return this.driver.findElement(By.xpath(REMOVE_MODAL_NO_BUTTON));
	}
	
	public WebElement getRemoveModalYesButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(REMOVE_MODAL_YES_BUTTON)));
		return this.driver.findElement(By.xpath(REMOVE_MODAL_YES_BUTTON));
	}
	
	public WebElement getHowToBeCalledField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(HOW_TO_BE_CALLED_FIELD)));
		return this.driver.findElement(By.cssSelector(HOW_TO_BE_CALLED_FIELD));
	}
	
	public WebElement getCpfField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CPF)));
		return this.driver.findElement(By.id(CPF));
	}
	
	public WebElement getPhoneTab() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PHONE_TAB)));
		return this.driver.findElement(By.xpath(PHONE_TAB));
	}
	
	public WebElement getEmailTab() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EMAIL_TAB)));
		return this.driver.findElement(By.xpath(EMAIL_TAB));
	}
	
	public WebElement getAdressTab() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADDRESS_TAB)));
		return this.driver.findElement(By.xpath(ADDRESS_TAB));
	}
	
	public WebElement getPhoneCode() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PHONE_CODE)));
		return this.driver.findElement(By.cssSelector(PHONE_CODE));
	}
	
	public WebElement getPhoneNumber() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PHONE_NUMBER)));
		return this.driver.findElement(By.cssSelector(PHONE_NUMBER));
	}
	
	public WebElement getPhoneTypeSelect() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(PHONE_TYPE_SELECT)));
		return this.driver.findElement(By.id(PHONE_TYPE_SELECT));
	}
	
	public WebElement getAdressTypeSelect() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(ADDRESS_TYPE_SELECT)));
		return this.driver.findElement(By.id(ADDRESS_TYPE_SELECT));
	}
	
	public WebElement getAdressCep() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ADDRESS_CEP)));
		return this.driver.findElement(By.cssSelector(ADDRESS_CEP));
	}
	
	public WebElement getAdressStreet() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ADDRESS_STREET)));
		return this.driver.findElement(By.cssSelector(ADDRESS_STREET));
	}
	
	public WebElement getAdressNumber() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ADDRESS_NUMBER)));
		return this.driver.findElement(By.cssSelector(ADDRESS_NUMBER));
	}
	
	public WebElement getAdressComplement() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ADDRESS_COMPLEMENT)));
		return this.driver.findElement(By.cssSelector(ADDRESS_COMPLEMENT));
	}
	
	public WebElement getAdressNeighborhood() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ADDRESS_NEIGHBORHOOD)));
		return this.driver.findElement(By.cssSelector(ADDRESS_NEIGHBORHOOD));
	}
	
	public WebElement getAdressCity() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ADDRESS_CITY)));
		return this.driver.findElement(By.cssSelector(ADDRESS_CITY));
	}
	
	public WebElement getAdressState() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ADDRESS_STATE)));
		return this.driver.findElement(By.cssSelector(ADDRESS_STATE));
	}
	
	public WebElement getAdressCountry() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ADDRESS_COUNTRY)));
		return this.driver.findElement(By.cssSelector(ADDRESS_COUNTRY));
	}
	
	public WebElement getAddAdressButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADD_ADDRESS_BUTTON)));
		return this.driver.findElement(By.xpath(ADD_ADDRESS_BUTTON));
	}
	
	public WebElement getLoadingTableElement() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOADING_TABLE_ELEMENT)));
		return this.driver.findElement(By.xpath(LOADING_TABLE_ELEMENT));
	}

	public WebElement getEditButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(EDIT_BUTTON)));
		return this.driver.findElement(By.id(EDIT_BUTTON));
	}
	
	public WebElement getEditEmailButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EDIT_EMAIL_BUTTON)));
		return this.driver.findElement(By.xpath(EDIT_EMAIL_BUTTON));
	}
	
	public WebElement getPeopleInvalidNameLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PEOPLE_INVALID_NAME_LABEL)));
		return this.driver.findElement(By.xpath(PEOPLE_INVALID_NAME_LABEL));
	}
	
	public WebElement getAccessDataTab() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ACCESS_DATA_TAB)));
		return this.driver.findElement(By.xpath(ACCESS_DATA_TAB));
	}
	
	public WebElement getUserLoginField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(USER_LOGIN_FIELD)));
		return this.driver.findElement(By.id(USER_LOGIN_FIELD));
	}
	
	public WebElement getUserRolesSelect() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(USER_ROLES_SELECT)));
		return this.driver.findElement(By.xpath(USER_ROLES_SELECT));
	}
	
	public WebElement getUserAccessToogle() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(USER_ACCESS_TOOGLE)));
		return this.driver.findElement(By.id(USER_ACCESS_TOOGLE));
	}

	public WebElement getInvalidUserLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_USER_LABEL)));
		return this.driver.findElement(By.xpath(INVALID_USER_LABEL));
	}

	public WebElement getHowTobeCalledLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(HOW_TO_BE_CALLED_INVALID_LABEL)));
		return this.driver.findElement(By.xpath(HOW_TO_BE_CALLED_INVALID_LABEL));	
	}
	
	public WebElement getCpfLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CPF_INVALID_LABEL)));
		return this.driver.findElement(By.xpath(CPF_INVALID_LABEL));	
	}
	
	public WebElement getCountryCodeLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COUNTRY_CODE_INVALID_LABEL)));
		return this.driver.findElement(By.xpath(COUNTRY_CODE_INVALID_LABEL));	
	}
	
	public WebElement getPhoneNumberLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PHONE_NUMBER_INVALID_LABEL)));
		return this.driver.findElement(By.xpath(PHONE_NUMBER_INVALID_LABEL));	
	}
	
	public WebElement getCepLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CEP_INVALID_LABEL)));
		return this.driver.findElement(By.xpath(CEP_INVALID_LABEL));	
	}
	
	public WebElement getStreetLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(STREET_INVALID_LABEL)));
		return this.driver.findElement(By.xpath(STREET_INVALID_LABEL));	
	}
	
	public WebElement getNumberLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(NUMBER_INVALID_LABEL)));
		return this.driver.findElement(By.xpath(NUMBER_INVALID_LABEL));	
	}
	
	public WebElement getComplementLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COMPLEMENT_INVALID_LABEL)));
		return this.driver.findElement(By.xpath(COMPLEMENT_INVALID_LABEL));	
	}
	
	public WebElement getNeighborhoodLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(NEIGHBORHOOD_INVALID_LABEL)));
		return this.driver.findElement(By.xpath(NEIGHBORHOOD_INVALID_LABEL));	
	}
	
	public WebElement getCityLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CITY_INVALID_LABEL)));
		return this.driver.findElement(By.xpath(CITY_INVALID_LABEL));	
	}
	
	public WebElement getStateLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(STATE_INVALID_LABEL)));
		return this.driver.findElement(By.xpath(STATE_INVALID_LABEL));	
	}
	
	public WebElement getCountryLabel() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COUNTRY_INVALID_LABEL)));
		return this.driver.findElement(By.xpath(COUNTRY_INVALID_LABEL));	
	}

}
