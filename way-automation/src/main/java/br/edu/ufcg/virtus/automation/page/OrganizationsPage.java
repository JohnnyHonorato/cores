package br.edu.ufcg.virtus.automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;

public class OrganizationsPage {

	public WebDriver driver;
	public SeleniumConfig seleniumConfig;

	private static final String BUSINESS_CARD_MENU = "/html/body/app-root/div/div/div[2]/div[2]/div";
	private static final String ORGANIZATIONS_MENU = "/html/body/business-root/div/div/div[1]/business-sidebar/div/a[3]";
	
	private static final String ADD_ORGANIZATION_BUTTON = "btn-add-server";
	private static final String ORGANIZATIONS_TABLE = "/html/body/business-root/div/div/div[2]/div/business-organization-list/common-data-table/table/tbody";
	private static final String FILTER_FIELD = "/html/body/business-root/div/div/div[2]/div/business-organization-list/common-data-table/div[1]/common-search-list/input";
	private static final String ORGANIZATION_ELEMENT = "//tr[contains(., '%s')]";
	
	private static final String CNPJ_FIELD = "input[formcontrolname='governmentCode']";
	private static final String INVALID_CNPJ_HINT = "/html/body/business-root/div/div/div[2]/div/business-organization-edit/form/div/div[2]/div[1]/common-control-message/label";
	private static final String VERIFY_CNPJ_BUTTON = "btn-verify-cnpj";
	
	private static final String COMPANY_NAME_FIELD = "input[formcontrolname='name']";
	private static final String FANTASY_NAME_FIELD = "input[formcontrolname='fantasyName']";
	private static final String DESCRIPTION_FIELD = "textarea[formcontrolname='description']";
	private static final String MANDATORY_COMPANY_NAME_HINT = "/html/body/business-root/div/div/div[2]/div/business-organization-edit/form/div/div[3]/div[1]/div[1]/common-control-message/label";
	private static final String MANDATORY_FANTASY_NAME_HINT = "/html/body/business-root/div/div/div[2]/div/business-organization-edit/form/div/div[3]/div[1]/div[2]/common-control-message/label";
	private static final String INVALID_CHARACTERS_QUANTITY_FOR_DESCRIPTION = "/html/body/business-root/div/div/div[2]/div/business-organization-edit/form/div/div[3]/div[1]/div[3]/common-control-message/label";
	private static final String EDIT_LOGO_IMAGE_BUTTON = "/html/body/business-root/div/div/div[2]/div/business-organization-edit/form/div/div[3]/div[2]/business-upload-image/div/div/div/div[2]/button[1]";
	private static final String CHOOSE_FILE_INPUT = "input[accept='image/*']";
	private static final String SAVE_IMAGE_LOGO_BUTTON = "/html/body/modal-container/div[2]/div/business-modal-image/div[3]/button[2]";
	private static final String IMG_LOGO_FIELD = "/html/body/business-root/div/div/div[2]/div/business-organization-edit/form/div/div[3]/div[2]/business-upload-image/div/div/div/div[1]/div/img";
	private static final String IMG_LOGO_SELECTED = "/html/body/modal-container/div[2]/div/business-modal-image/div[2]/div/div[2]/image-cropper/div/img";
	private static final String REMOVE_LOGO_IMAGE_BUTTON = "/html/body/business-root/div/div/div[2]/div/business-organization-edit/form/div/div[3]/div[2]/business-upload-image/div/div/div/div[2]/button[2]";
	private static final String CONFIRM_IMAGE_LOGO_REMOVAL_BUTTON = "//*[@id=\"modal-delete\"]/div/div/div[3]/button[2]";
	private static final String DEFAULT_LOGO_IMAGE = "span[class='avatar avatar-xl rounded-circle']";
	private static final String MANDATORY_CNPJ_HINT = "/html/body/business-root/div/div/div[2]/div/business-organization-edit/form/div/div[2]/div[1]/common-control-message/label";
	
	private static final String CONTACT_INFO_TAB = "//*[contains(text(),'Informações para contato ')]";
	private static final String EMAIL_FIELD = "input[formcontrolname='email']";
	private static final String ADD_EMAIL_BUTTON = "//*[@id=\"tab1\"]/form/div/button";
	private static final String MANDATORY_EMAIL_HINT = "//*[@id=\"tab1\"]/form/div/div[1]/common-control-message/label";
	private static final String MANDATORY_EMAIL_TYPE_HINT = "//*[@id=\"tab1\"]/form/div/div[2]/common-control-message/label";
	private static final String EDIT_EMAIL_BUTTON = "//*[@id=\"tab1\"]/div/div/table/tbody/tr[2]/td[3]/button[1]";
	
	private static final String TELEPHONE_TAB = "//*[@id=\"collapseOne\"]/div/common-contact-info/ul/li[2]";
	private static final String DDI_CODE_FIELD = "//*[@id=\"tab2\"]/form/div/div[1]/input";
	private static final String PHONE_NUMBER_FIELD = "//*[@id=\"tab2\"]/form/div/div[2]/input";
	private static final String INVALID_CHARACTERS_QUANTITY_FOR_DDI_CODE = "//*[@id=\"tab2\"]/form/div/div[1]/common-control-message/label";
	private static final String INVALID_CHARACTERS_QUANTITY_FOR_PHONE_NUMBER = "//*[@id=\"tab2\"]/form/div/div[2]/common-control-message/label";
	private static final String ADD_PHONE_BUTTON = "//*[@id=\"tab2\"]/form/div/button";
	
	private static final String ADDRESS_TAB = "//*[@id=\"collapseOne\"]/div/common-contact-info/ul/li[3]";
	private static final String CEP_FIELD = "//*[@id=\"tab3\"]/form/div/div[1]/div[2]/input";
	private static final String STREET_FIELD = "//*[@id=\"tab3\"]/form/div/div[1]/div[3]/input";
	private static final String ADDRESS_FIELD = "//*[@id=\"tab3\"]/form/div/div[1]/div[4]/input";
	private static final String COMPLEMENT_FIELD = "//*[@id=\"tab3\"]/form/div/div[1]/div[5]/input";
	private static final String DISTRICT_FIELD = "//*[@id=\"tab3\"]/form/div/div[1]/div[6]/input";
	private static final String CITY_FIELD = "//*[@id=\"tab3\"]/form/div/div[1]/div[7]/input";
	private static final String STATE_FIELD = "//*[@id=\"tab3\"]/form/div/div[1]/div[8]/input";
	private static final String COUNTRY_FIELD = "//*[@id=\"tab3\"]/form/div/div[1]/div[9]/input";
	private static final String INVALID_CHARACTERS_QUANTITY_FOR_STREET = "//*[@id=\"tab3\"]/form/div/div[1]/div[3]/common-control-message/label";
	private static final String INVALID_CHARACTERS_QUANTITY_FOR_ADDRESS_NUMBER = "//*[@id=\"tab3\"]/form/div/div[1]/div[4]/common-control-message/label";
	private static final String INVALID_CHARACTERS_QUANTITY_FOR_ADDRESS_COMPLEMENT = "//*[@id=\"tab3\"]/form/div/div[1]/div[5]/common-control-message/label";
	private static final String INVALID_CHARACTERS_QUANTITY_FOR_DISTRICT = "//*[@id=\"tab3\"]/form/div/div[1]/div[6]/common-control-message/label";
	private static final String INVALID_CHARACTERS_QUANTITY_FOR_CITY = "//*[@id=\"tab3\"]/form/div/div[1]/div[7]/common-control-message/label";
	private static final String INVALID_CHARACTERS_QUANTITY_FOR_STATE = "//*[@id=\"tab3\"]/form/div/div[1]/div[8]/common-control-message/label";
	private static final String INVALID_CHARACTERS_QUANTITY_FOR_COUNTRY = "//*[@id=\"tab3\"]/form/div/div[1]/div[9]/common-control-message/label";
	private static final String ADD_ADDRESS_BUTTON = "//*[@id=\"tab3\"]/form/div/div[2]/button";

	private static final String COLLABORATOR_TAB = "//*[contains(text(),' Colaborador ')]";
	private static final String COLLABORATOR_DROPBOX = "//*[@id=\"role\"]";
	private static final String COLLABORATOR_FIELD = "//*[@id=\"role\"]/div/div/div[2]/input";
	private static final String COLLABORATOR_NAME_FILTERED = "//*[contains(text(),'%s')]";
	private static final String DEPARTAMENT_FIELD = "input[formcontrolname='department']";
	private static final String OFFICE_FIELD = "input[formcontrolname='position']";
	private static final String INVALID_CHARACTERS_QUANTITY_FOR_OFFICE = "//*[@id=\"collapseTwo\"]/div/business-contributors/form/div/div[3]/div/common-control-message/label";
	private static final String INVALID_CHARACTERS_QUANTITY_FOR_DEPARTAMENT = "//*[@id=\"collapseTwo\"]/div/business-contributors/form/div/div[2]/common-control-message/label";
	private static final String ADD_COLLABORATOR_BUTTON = "//*[@id=\"collapseTwo\"]/div/business-contributors/form/div/div[3]/div/span/button";
	private static final String SAVE_EDIT_COLLABORATOR_BUTTON = "//*[@id=\"collapseTwo\"]/div/business-contributors/form/div/div[3]/div/span/button";
	private static final String EDIT_COLLABORATOR_BUTTON = "button[title='Editar Item']";
	
	private static final String CONFIRM_ORGANIZATION_REMOVAL_BUTTON = "//*[@id=\"modal-delete\"]/div/div/div[3]/button[2]";
	private static final String CANCEL_REMOVAL_BUTTON = "//*[@id=\"modal-delete\"]/div/div/div[3]/button[1]";
	private static final String REGISTER_NOT_FOUND_ELEMENT = "/html/body/business-root/div/div/div[2]/div/business-organization-list/common-data-table/table/tbody/tr/td/strong";
	private static final String SAVE_EDIT_EMAIL_BUTTON = "//*[@id=\"tab1\"]/form/div/button";
	private static final String EDIT_TELEPHONE_BUTTON = "//*[@id=\"tab2\"]/div/div/table/tbody/tr[2]/td[4]/button[1]";
	private static final String SAVE_EDIT_TELEPHONE_BUTTON = "//*[@id=\"tab2\"]/form/div/button";
	private static final String EDIT_ADDRESS_BUTTON = "//*[@id=\"tab3\"]/div/div/table/tbody/tr[2]/td[4]/button[1]";
	private static final String SAVE_EDIT_ADDRESS_BUTTON = "//*[@id=\"tab3\"]/form/div/div[2]/button";
	private static final String CLEAR_COLLABORATOR_NAME_BUTTON = "//*[@id=\"role\"]/div/span[1]";
		
	public OrganizationsPage(SeleniumConfig seleniumConfig) {
		this.driver = seleniumConfig.getDriver();
		this.seleniumConfig = seleniumConfig;
	}

	public WebElement getBusinessCardMenu() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BUSINESS_CARD_MENU)));
		return this.driver.findElement(By.xpath(BUSINESS_CARD_MENU));
	}

	public WebElement getOrganizationsMenu() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ORGANIZATIONS_MENU)));
		return this.driver.findElement(By.xpath(ORGANIZATIONS_MENU));
	}

	public WebElement getAddButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.id(ADD_ORGANIZATION_BUTTON)));
		return this.driver.findElement(By.id(ADD_ORGANIZATION_BUTTON));
	}

	public WebElement getCNPJField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CNPJ_FIELD)));
		return this.driver.findElement(By.cssSelector(CNPJ_FIELD));
	}

	public WebElement getVerifyButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(VERIFY_CNPJ_BUTTON)));
		return this.driver.findElement(By.id(VERIFY_CNPJ_BUTTON));
	}

	public WebElement getCompanyNameField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(COMPANY_NAME_FIELD)));
		return this.driver.findElement(By.cssSelector(COMPANY_NAME_FIELD));
	}

	public WebElement getFantasyNameField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(FANTASY_NAME_FIELD)));
		return this.driver.findElement(By.cssSelector(FANTASY_NAME_FIELD));
	}

	public WebElement getOrganizationsTable() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ORGANIZATIONS_TABLE)));
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CommonPage.TABLE_LOADING)));
		return this.driver.findElement(By.xpath(ORGANIZATIONS_TABLE));
	}

	public WebElement getContactInfoTab() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CONTACT_INFO_TAB)));
		return this.driver.findElement(By.xpath(CONTACT_INFO_TAB));
	}

	public WebElement getEmailField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(EMAIL_FIELD)));
		return this.driver.findElement(By.cssSelector(EMAIL_FIELD));
	}

	public WebElement getAddEmailButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADD_EMAIL_BUTTON)));
		return this.driver.findElement(By.xpath(ADD_EMAIL_BUTTON));
	}

	public WebElement getMandatoryCompanyNameHint() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MANDATORY_COMPANY_NAME_HINT)));
		return this.driver.findElement(By.xpath(MANDATORY_COMPANY_NAME_HINT));
	}

	public WebElement getMandatoryFantasyNameHint() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MANDATORY_FANTASY_NAME_HINT)));
		return this.driver.findElement(By.xpath(MANDATORY_FANTASY_NAME_HINT));
	}

	public WebElement getMandatoryEmailHint() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MANDATORY_EMAIL_HINT)));
		return this.driver.findElement(By.xpath(MANDATORY_EMAIL_HINT));
	}

	public WebElement getMandatoryEmailTypeHint() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MANDATORY_EMAIL_TYPE_HINT)));
		return this.driver.findElement(By.xpath(MANDATORY_EMAIL_TYPE_HINT));
	}

	public WebElement getEditEmailButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EDIT_EMAIL_BUTTON)));
		return this.driver.findElement(By.xpath(EDIT_EMAIL_BUTTON));
	}

	public WebElement getDescriptionField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(DESCRIPTION_FIELD)));
		return this.driver.findElement(By.cssSelector(DESCRIPTION_FIELD));
	}

	public WebElement getCollaboratorTab() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COLLABORATOR_TAB)));
		return this.driver.findElement(By.xpath(COLLABORATOR_TAB));
	}

	public WebElement getCollaboratorDropbox() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COLLABORATOR_DROPBOX)));
		return this.driver.findElement(By.xpath(COLLABORATOR_DROPBOX));
	}
	
	public WebElement getCollaboratorField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COLLABORATOR_FIELD)));
		return this.driver.findElement(By.xpath(COLLABORATOR_FIELD));
	}
	
	public WebElement getElementFilteredOnCollaboratorList(String collaboratorName) {
		String cssSelector = String.format(COLLABORATOR_NAME_FILTERED, collaboratorName);
		this.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath(cssSelector)));
		return this.driver.findElement(By.xpath(cssSelector));
	}

	public WebElement getDepartamentField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(DEPARTAMENT_FIELD)));
		return this.driver.findElement(By.cssSelector(DEPARTAMENT_FIELD));
	}

	public WebElement getOfficeField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(OFFICE_FIELD)));
		return this.driver.findElement(By.cssSelector(OFFICE_FIELD));
	}

	public WebElement getAddCollaboratorButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADD_COLLABORATOR_BUTTON)));
		return this.driver.findElement(By.xpath(ADD_COLLABORATOR_BUTTON));
	}

	public WebElement getTelephoneTab() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TELEPHONE_TAB)));
		return this.driver.findElement(By.xpath(TELEPHONE_TAB));
	}

	public WebElement getDDICodeField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DDI_CODE_FIELD)));
		return this.driver.findElement(By.xpath(DDI_CODE_FIELD));
	}

	public WebElement getPhoneNumberField() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PHONE_NUMBER_FIELD)));
		return this.driver.findElement(By.xpath(PHONE_NUMBER_FIELD));
	}

	public WebElement getAddPhoneButton() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADD_PHONE_BUTTON)));
		return this.driver.findElement(By.xpath(ADD_PHONE_BUTTON));
	}

	public WebElement getAddressTab() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADDRESS_TAB)));
		return this.driver.findElement(By.xpath(ADDRESS_TAB));
	}

	public WebElement getCepField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CEP_FIELD)));
		return this.driver.findElement(By.xpath(CEP_FIELD));
	}

	public WebElement getStreetField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(STREET_FIELD)));
		return this.driver.findElement(By.xpath(STREET_FIELD));
	}

	public WebElement getAddressNumber() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADDRESS_FIELD)));
		return this.driver.findElement(By.xpath(ADDRESS_FIELD));
	}

	public WebElement getComplementField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COMPLEMENT_FIELD)));
		return this.driver.findElement(By.xpath(COMPLEMENT_FIELD));
	}

	public WebElement getDistrictField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DISTRICT_FIELD)));
		return this.driver.findElement(By.xpath(DISTRICT_FIELD));
	}

	public WebElement getCityField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CITY_FIELD)));
		return this.driver.findElement(By.xpath(CITY_FIELD));
	}

	public WebElement getStateField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(STATE_FIELD)));
		return this.driver.findElement(By.xpath(STATE_FIELD));
	}

	public WebElement getCountryField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COUNTRY_FIELD)));
		return this.driver.findElement(By.xpath(COUNTRY_FIELD));
	}

	public WebElement getAddAddressButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADD_ADDRESS_BUTTON)));
		return this.driver.findElement(By.xpath(ADD_ADDRESS_BUTTON));
	}

	public WebElement getFilterField() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FILTER_FIELD)));
		return this.driver.findElement(By.xpath(FILTER_FIELD));
	}

	public WebElement getConfirmOrganizationRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CONFIRM_ORGANIZATION_REMOVAL_BUTTON)));
		return this.driver.findElement(By.xpath(CONFIRM_ORGANIZATION_REMOVAL_BUTTON));
	}

	public WebElement getRegisterNotFoundElement() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(REGISTER_NOT_FOUND_ELEMENT)));
		return this.driver.findElement(By.xpath(REGISTER_NOT_FOUND_ELEMENT));
	}

	public WebElement getCancelRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CANCEL_REMOVAL_BUTTON)));
		return this.driver.findElement(By.xpath(CANCEL_REMOVAL_BUTTON));
	}

	public WebElement getSaveEditEmailButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SAVE_EDIT_EMAIL_BUTTON)));
		return this.driver.findElement(By.xpath(SAVE_EDIT_EMAIL_BUTTON));
	}

	public WebElement getEditTelephoneButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EDIT_TELEPHONE_BUTTON)));
		return this.driver.findElement(By.xpath(EDIT_TELEPHONE_BUTTON));
	}

	public WebElement getSaveEditTelephoneButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SAVE_EDIT_TELEPHONE_BUTTON)));
		return this.driver.findElement(By.xpath(SAVE_EDIT_TELEPHONE_BUTTON));
	}

	public WebElement getEditAddressButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EDIT_ADDRESS_BUTTON)));
		return this.driver.findElement(By.xpath(EDIT_ADDRESS_BUTTON));
	}

	public WebElement getSaveEditAddressButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SAVE_EDIT_ADDRESS_BUTTON)));
		return this.driver.findElement(By.xpath(SAVE_EDIT_ADDRESS_BUTTON));
	}

	public WebElement getEditCollaboratorButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(EDIT_COLLABORATOR_BUTTON)));
		return this.driver.findElement(By.cssSelector(EDIT_COLLABORATOR_BUTTON));
	}

	public WebElement getSaveEditCollaboratorButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SAVE_EDIT_COLLABORATOR_BUTTON)));
		return this.driver.findElement(By.xpath(SAVE_EDIT_COLLABORATOR_BUTTON));
	}

	public WebElement getClearCollaboratorNameButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CLEAR_COLLABORATOR_NAME_BUTTON)));
		return this.driver.findElement(By.xpath(CLEAR_COLLABORATOR_NAME_BUTTON));
	}

	public WebElement getInvalidCNPJHint() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CNPJ_HINT)));
		return this.driver.findElement(By.xpath(INVALID_CNPJ_HINT));
	}

	public WebElement getEditImageLogoButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EDIT_LOGO_IMAGE_BUTTON)));
		return this.driver.findElement(By.xpath(EDIT_LOGO_IMAGE_BUTTON));
	}

	public WebElement getChooseFileInput() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CHOOSE_FILE_INPUT)));
		return this.driver.findElement(By.cssSelector(CHOOSE_FILE_INPUT));
	}

	public WebElement getSaveImageLogoButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SAVE_IMAGE_LOGO_BUTTON)));
		return this.driver.findElement(By.xpath(SAVE_IMAGE_LOGO_BUTTON));
	}

	public WebElement getImageUploaded() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated((By.xpath(IMG_LOGO_FIELD))));
		return this.driver.findElement(By.xpath(IMG_LOGO_FIELD));
	}

	public WebElement getRemoveImageLogoButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(REMOVE_LOGO_IMAGE_BUTTON)));

		return this.driver.findElement(By.xpath(REMOVE_LOGO_IMAGE_BUTTON));
	}

	public WebElement getConfirmImageLogoRemovalButton() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CONFIRM_IMAGE_LOGO_REMOVAL_BUTTON)));
		return this.driver.findElement(By.xpath(CONFIRM_IMAGE_LOGO_REMOVAL_BUTTON));
	}

	public WebElement getDefaultLogoImage() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(DEFAULT_LOGO_IMAGE)));
		return this.driver.findElement(By.cssSelector(DEFAULT_LOGO_IMAGE));
	}

	public WebElement getMandatoryCNPJHint() {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MANDATORY_CNPJ_HINT)));
		return this.driver.findElement(By.xpath(MANDATORY_CNPJ_HINT));
	}

	public void waitForOrganizationDisplayedOnTable(String fantasyName) {

		String organizationElementXpath = String.format(ORGANIZATION_ELEMENT, fantasyName);

		this.seleniumConfig.getWait()
				.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CommonPage.TABLE_LOADING)));

		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(organizationElementXpath)));

		this.seleniumConfig.getWait().until(
				ExpectedConditions.textToBePresentInElementLocated(By.xpath(organizationElementXpath), fantasyName));
	}

	public WebElement getEditOrganizationButton(String fantasyName) {
		this.waitForOrganizationDisplayedOnTable(fantasyName);

		String organizationElementXpath = String.format(ORGANIZATION_ELEMENT, fantasyName);
		WebElement organizationElement = this.driver.findElement(By.xpath(organizationElementXpath));

		this.seleniumConfig.getWait()
				.until(ExpectedConditions.visibilityOf(organizationElement.findElement(By.id(CommonPage.EDIT_BUTTON))));
		this.seleniumConfig.getWait().until(ExpectedConditions
				.elementToBeClickable(organizationElement.findElement(By.id(CommonPage.EDIT_BUTTON))));
		return organizationElement.findElement(By.id(CommonPage.EDIT_BUTTON));
	}

	public WebElement getInvalidCharacterQuantityHintForDDICode() {
		this.seleniumConfig.getWait()
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_DDI_CODE)));
		return this.driver.findElement(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_DDI_CODE));
	}

	public WebElement getInvalidCharacterQuantityHintForPhoneNumber() {
		this.seleniumConfig.getWait()
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_PHONE_NUMBER)));
		return this.driver.findElement(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_PHONE_NUMBER));
	}

	public WebElement getInvalidCharacterQuantityHintForStreet() {
		this.seleniumConfig.getWait()
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_STREET)));
		return this.driver.findElement(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_STREET));
	}

	public WebElement getInvalidCharacterQuantityHintForAddressNumber() {
		this.seleniumConfig.getWait()
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_ADDRESS_NUMBER)));
		return this.driver.findElement(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_ADDRESS_NUMBER));
	}

	public WebElement getInvalidCharacterQuantityHintForAddressComplement() {
		this.seleniumConfig.getWait()
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_ADDRESS_COMPLEMENT)));
		return this.driver.findElement(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_ADDRESS_COMPLEMENT));
	}

	public WebElement getInvalidCharacterQuantityHintForDistrict() {
		this.seleniumConfig.getWait()
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_DISTRICT)));
		return this.driver.findElement(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_DISTRICT));
	}

	public WebElement getInvalidCharacterQuantityHintForCity() {
		this.seleniumConfig.getWait()
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_CITY)));
		return this.driver.findElement(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_CITY));
	}

	public WebElement getInvalidCharacterQuantityHintForState() {
		this.seleniumConfig.getWait()
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_STATE)));
		return this.driver.findElement(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_STATE));
	}

	public WebElement getInvalidCharacterQuantityHintForCountry() {
		this.seleniumConfig.getWait()
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_COUNTRY)));
		return this.driver.findElement(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_COUNTRY));
	}
	
	public WebElement getImageLogoSelected() {
		this.seleniumConfig.getWait().until(ExpectedConditions.visibilityOfElementLocated((By.xpath(IMG_LOGO_SELECTED))));
		return this.driver.findElement(By.xpath(IMG_LOGO_SELECTED));
	}

	public WebElement getInvalidCharacterQuantityHintForDescription() {
		this.seleniumConfig.getWait()
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_DESCRIPTION)));
		return this.driver.findElement(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_DESCRIPTION));
	}

	public WebElement getInvalidCharacterQuantityHintForOffice() {
		this.seleniumConfig.getWait()
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_OFFICE)));
		return this.driver.findElement(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_OFFICE));
	}

	public WebElement getInvalidCharacterQuantityHintForDepartament() {
		this.seleniumConfig.getWait()
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_DEPARTAMENT)));
		return this.driver.findElement(By.xpath(INVALID_CHARACTERS_QUANTITY_FOR_DEPARTAMENT));
	}
}
