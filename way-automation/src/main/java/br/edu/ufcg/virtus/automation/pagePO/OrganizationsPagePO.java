package br.edu.ufcg.virtus.automation.pagePO;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.entity.Organization;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.page.OrganizationsPage;

public class OrganizationsPagePO {

	public OrganizationsPage organizationsPage;
	public CommonPage commonPage;
	public CommonPagePO commonPagePO;

	public OrganizationsPagePO(SeleniumConfig seleniumConfig, CommonPage commonPage, CommonPagePO commonPagePO) {
		this.organizationsPage = new OrganizationsPage(seleniumConfig);
		this.commonPage = commonPage;
		this.commonPagePO = commonPagePO;
	}

	public void openBusinessPage() {
		this.organizationsPage.getBusinessCardMenu().click();
	}

	public void openOrganizationsPage() {
		this.organizationsPage.getOrganizationsMenu().click();
	}

	public void clickOnAddButton() {
		WebElement addButton = this.organizationsPage.getAddButton();
		this.organizationsPage.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(addButton));
		addButton.click();
	}

	public void fillCNPJField(String cnpj) {
		WebElement cnpjField = this.organizationsPage.getCNPJField();
		cnpjField.clear();
		cnpjField.sendKeys(cnpj, Keys.TAB);
	}

	public void clickOnVerifyButton() {
		this.organizationsPage.getVerifyButton().click();
	}

	public void fillCompanyName(String companyName) {
		WebElement companyNameField = this.organizationsPage.getCompanyNameField();
		companyNameField.clear();
		companyNameField.sendKeys(companyName);

	}

	public void fillFantasyName(String fantasyName) {
		WebElement fantasyNameField = this.organizationsPage.getFantasyNameField();
		fantasyNameField.clear();
		fantasyNameField.sendKeys(fantasyName);
	}

	public void clickOnSaveButton() {

		this.commonPagePO.scrollDownPage();

		WebElement saveButton = this.commonPage.getSaveButton();
		this.organizationsPage.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(saveButton));
		saveButton.click();
	}

	public List<Organization> readOrganizationTable() {

		List<Organization> organizations = new ArrayList<Organization>();
		WebElement table = this.organizationsPage.getOrganizationsTable();
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (int i = 1; i < rows.size(); i++) {
			List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
			organizations.add(new Organization(cells.get(0).getText(), cells.get(1).getText()));
		}
		return organizations;
	}

	public Boolean isOrganizationDisplayedOnTable(String fantasyName) {
		List<Organization> organizations = this.readOrganizationTable();

		for (Organization o : organizations) {
			if (o.getFantasyName().equals(fantasyName))
				return true;
		}
		return false;
	}

	public void clickOnContactInfoTab() {
		this.organizationsPage.getContactInfoTab().click();
	}

	public void fillEmailField(String email) {
		WebElement emailField = this.organizationsPage.getEmailField();
		emailField.clear();
		emailField.sendKeys(email);
	}

	public void selectEmailType(String emailType) {
		Select emailTypeSelect = new Select(this.commonPage.getEmailTypeField());
		emailTypeSelect.selectByVisibleText(emailType);
	}

	public void clickOnAddEmailButton() {
		WebElement addEmailButton = this.organizationsPage.getAddEmailButton();
		this.organizationsPage.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(addEmailButton));
		addEmailButton.click();
	}

	public void clickOnEmailTypeField() {
		this.commonPagePO.scrollDownPage();
		this.commonPage.getEmailTypeField().click();
	}

	public String getMandatoryCompanyNameErrorMessage() {
		return this.organizationsPage.getMandatoryCompanyNameHint().getText();
	}

	public String getMandatoryFantasyNameErrorMessage() {
		return this.organizationsPage.getMandatoryFantasyNameHint().getText();
	}

	public String getMandatoryEmailErrorMessage() {
		return this.organizationsPage.getMandatoryEmailHint().getText();
	}

	public String getMandatoryEmailTypeErrorMessage() {
		return this.organizationsPage.getMandatoryEmailTypeHint().getText();
	}

	public int getCountOfOrganizationsDisplayedOnTable(String fantasyname) {
		int countOccurrences = 0;
		List<Organization> organizations = this.readOrganizationTable();
		for (Organization o : organizations) {
			if (o.getFantasyName().equals(fantasyname))
				countOccurrences += 1;
		}
		return countOccurrences;
	}

	public String getCompanyNameValue() {
		return this.organizationsPage.getCompanyNameField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getFantasyNameValue() {
		return this.organizationsPage.getFantasyNameField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getEmailValue() {
		return this.organizationsPage.getEmailField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getEmailTypeValue() {
		return this.commonPage.getEmailTypeField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public void clickOnEditEmailButton() {
		JavascriptExecutor js = (JavascriptExecutor) this.organizationsPage.driver;
		js.executeScript(CommonData.ELEMENT_CLICK, this.organizationsPage.getEditEmailButton());
	}

	public void fillDescriptionField(String description) {
		WebElement descriptionField = this.organizationsPage.getDescriptionField();
		descriptionField.clear();
		descriptionField.sendKeys(description);
	}

	public void clickOnCollaboratorTab() {
		this.commonPagePO.scrollDownPage();
		this.organizationsPage.getCollaboratorTab().click();
	}

	public void fillCollaboratorField(String collaboratorName) {

		WebElement collaboratorDropbox = this.organizationsPage.getCollaboratorDropbox();
		this.organizationsPage.seleniumConfig.getWait()
				.until(ExpectedConditions.elementToBeClickable(collaboratorDropbox));
		collaboratorDropbox.click();
		this.organizationsPage.getCollaboratorField().sendKeys(collaboratorName);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.organizationsPage.getElementFilteredOnCollaboratorList(collaboratorName).click();
	}

	public void fillDepartamentField(String departament) {
		WebElement departamentField = this.organizationsPage.getDepartamentField();
		departamentField.clear();
		departamentField.sendKeys(departament);
	}

	public void fillOfficeField(String office) {
		WebElement officeField = this.organizationsPage.getOfficeField();
		officeField.clear();
		officeField.sendKeys(office);
	}

	public void clickOnAddCollaboratorButton() {
		this.commonPagePO.scrollDownPage();
		this.organizationsPage.getAddCollaboratorButton().click();
	}

	public void clickOnTelephoneTab() {
		this.organizationsPage.getTelephoneTab().click();
	}

	public void fillDDICodeField(String DDICode) {
		WebElement ddiCodeField = this.organizationsPage.getDDICodeField();
		ddiCodeField.clear();
		ddiCodeField.sendKeys(DDICode);
	}

	public void fillPhoneNumber(String phoneNumber) {
		WebElement phoneNumberField = this.organizationsPage.getPhoneNumberField();
		phoneNumberField.clear();
		phoneNumberField.sendKeys(phoneNumber);
	}

	public void selectPhoneType(String phoneType) {
		Select phoneTypeSelect = new Select(this.commonPage.getPhoneTypeField());
		phoneTypeSelect.selectByVisibleText(phoneType);
	}

	public void clickOnAddPhoneButton() {
		this.organizationsPage.getAddPhoneButton().click();
	}

	public void clickOnAddressTab() {
		this.organizationsPage.getAddressTab().click();
	}

	public void selectDomain(String domain) {
		Select domainSelect = new Select(this.commonPage.getDomainField());
		domainSelect.selectByVisibleText(domain);
	}

	public void fillCepField(String cep) {
		WebElement cepField = this.organizationsPage.getCepField();
		cepField.clear();
		cepField.sendKeys(cep);
	}

	public void fillStreetField(String street) {
		WebElement streetField = this.organizationsPage.getStreetField();
		streetField.clear();
		streetField.sendKeys(street);
	}

	public void fillAddressNumber(String addressNumber) {
		WebElement addressNumberField = this.organizationsPage.getAddressNumber();
		addressNumberField.clear();
		addressNumberField.sendKeys(addressNumber);
	}

	public void fillComplementField(String complement) {
		WebElement complementField = this.organizationsPage.getComplementField();
		complementField.clear();
		complementField.sendKeys(complement);
	}

	public void fillDistrictField(String district) {
		WebElement districtField = this.organizationsPage.getDistrictField();
		districtField.clear();
		districtField.sendKeys(district);
	}

	public void fillCityField(String city) {
		WebElement cityField = this.organizationsPage.getCityField();
		cityField.clear();
		cityField.sendKeys(city);
	}

	public void fillStateField(String state) {
		WebElement stateField = this.organizationsPage.getStateField();
		stateField.clear();
		stateField.sendKeys(state);
	}

	public void fillCountryField(String country) {
		WebElement countryField = this.organizationsPage.getCountryField();
		countryField.clear();
		countryField.sendKeys(country);
	}

	public void clickOnAddAddressButton() {
		this.organizationsPage.getAddAddressButton().click();
	}

	public void filterByFantasyName(String fantasyName) {
		WebElement filterField = this.organizationsPage.getFilterField();
		filterField.clear();
		filterField.sendKeys(fantasyName);
		this.organizationsPage.seleniumConfig.getWait()
				.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CommonPage.TABLE_LOADING)));
	}

	public void clickToRemove() {
		try {
			this.commonPage.getDeleteButton().click();
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			this.commonPage.getDeleteButton().click();
		}
	}

	public void clickOnYesToConfirmOrganizationRemoval() {
		this.organizationsPage.getConfirmOrganizationRemovalButton().click();
	}

	public String getToastMsg() {
		return this.commonPage.getToast().getText();
	}

	public String getMsgRegisterNotFound() {
		return this.organizationsPage.getRegisterNotFoundElement().getText();
	}

	public void clickOnBackButton() {
		this.commonPagePO.scrollDownPage();
		this.organizationsPage.seleniumConfig.getWait()
				.until(ExpectedConditions.elementToBeClickable(this.commonPage.getBackButton()));
		this.commonPage.getBackButton().click();
	}

	public void clickOnNoToCancelRemoval() {
		this.organizationsPage.getCancelRemovalButton().click();
	}

	public void clickOnXToCancelRemoval() {
		this.commonPage.getCloseModalRemovalButton().click();
	}

	public void clickToEdit(String fantasyName) {
		boolean staleElement = true;
		while (staleElement) {
			try {
				this.organizationsPage.getEditOrganizationButton(fantasyName).click();
				staleElement = false;
			} catch (StaleElementReferenceException e) {
				staleElement = true;
			}
		}
	}

	public String getCNPJValue() {
		return this.organizationsPage.getCNPJField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getDescriptionValue() {
		return this.organizationsPage.getDescriptionField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public void clickOnSaveEditEmailButton() {
		this.organizationsPage.getSaveEditEmailButton().click();
	}

	public void clickOnEditTelephoneButton() {
		this.commonPagePO.scrollDownPage();
		this.organizationsPage.getEditTelephoneButton().click();
	}

	public void clickOnSaveEditTelephoneButton() {
		this.organizationsPage.getSaveEditTelephoneButton().click();
	}

	public String getPhoneNumberValue() {
		return this.organizationsPage.getPhoneNumberField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getPhoneDDICodeValue() {
		return this.organizationsPage.getDDICodeField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getPhoneTypeValue() {
		return this.commonPage.getPhoneTypeField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public void clickOnEditAddressButton() {
		this.commonPagePO.scrollDownPage();
		this.organizationsPage.getEditAddressButton().click();
	}

	public void clickOnSaveEditAddressButton() {
		this.organizationsPage.getSaveEditAddressButton().click();
	}

	public String getDomainValue() {
		return this.commonPage.getDomainField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getCepValue() {
		return this.organizationsPage.getCepField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getStreetValue() {
		return this.organizationsPage.getStreetField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getAddressNumberValue() {
		return this.organizationsPage.getAddressNumber().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getAddressComplementValue() {
		return this.organizationsPage.getComplementField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getDistrictValue() {
		return this.organizationsPage.getDistrictField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getCityValue() {
		return this.organizationsPage.getCityField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getStateValue() {
		return this.organizationsPage.getStateField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getCountryValue() {
		return this.organizationsPage.getCountryField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public void clickOnEditCollaboratorButton() {
		WebElement editCollaboratorButton = this.organizationsPage.getEditCollaboratorButton();
		this.commonPagePO.scrollDownPage();
		this.organizationsPage.seleniumConfig.getWait()
				.until(ExpectedConditions.elementToBeClickable(editCollaboratorButton));

		editCollaboratorButton.click();
	}

	public void clickOnSaveEditCollaboratorButton() {
		this.organizationsPage.getSaveEditCollaboratorButton().click();
	}

	public String getCollaboratorNameValue() {
		return this.organizationsPage.getCollaboratorDropbox().getText();
	}

	public void clickToClearCollaboratorName() {
		this.organizationsPage.getClearCollaboratorNameButton().click();
	}

	public String getDepartamentValue() {
		return this.organizationsPage.getDepartamentField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getOfficeValue() {
		return this.organizationsPage.getOfficeField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getInvalidCNPJMessage() {
		return this.organizationsPage.getInvalidCNPJHint().getText();
	}

	public void clickToEditImageLogo() {
		this.organizationsPage.getEditImageLogoButton().click();
	}

	public void uploadImageLogo(String imagePath) {
		this.organizationsPage.getChooseFileInput().sendKeys(imagePath);
	}

	public void clickToSaveImageUpload() {
		this.organizationsPage.getSaveImageLogoButton().click();
	}

	public String getSrcFromImageUploaded() {
		return this.organizationsPage.getImageUploaded().getAttribute(CommonData.ATTRIBUTE_SRC);
	}

	public void addOrganizationWithMandatoryFields(String cnpj, String companyName, String fantasyName, String email,
			String emailType) {

		this.clickOnAddButton();
		this.fillCNPJField(cnpj);
		this.clickOnVerifyButton();

		this.fillCompanyName(companyName);
		this.fillFantasyName(fantasyName);

		this.fillEmailField(email);
		this.selectEmailType(emailType);
		this.clickOnAddEmailButton();

		this.clickOnSaveButton();
	}

	public void addOrganizationWithAllFields(String cnpj, String companyName, String fantasyName, String description,
			String imagePath, String email, String emailType, String ddiCode, String phoneNumber, String phoneType,
			String domain, String cep, String street, String addressNumber, String complement, String district,
			String city, String state, String country, String collaborator, String departament, String office) {

		this.clickOnAddButton();
		this.fillCNPJField(cnpj);
		this.clickOnVerifyButton();

		// Company data
		this.fillCompanyName(companyName);
		this.fillFantasyName(fantasyName);
		this.fillDescriptionField(description);

		// Image logo
		this.clickToEditImageLogo();
		this.uploadImageLogo(imagePath);
		this.clickToSaveImageUpload();

		// Fill email tab data
		this.fillEmailField(email);
		this.selectEmailType(emailType);
		this.clickOnAddEmailButton();

		// Fill phone tab data
		this.clickOnTelephoneTab();
		this.fillDDICodeField(ddiCode);
		this.fillPhoneNumber(phoneNumber);
		this.selectPhoneType(phoneType);
		this.clickOnAddPhoneButton();

		// Fill address tab data
		this.clickOnAddressTab();
		this.selectDomain(domain);
		this.fillCepField(cep);
		this.fillStreetField(street);
		this.fillAddressNumber(addressNumber);
		this.fillComplementField(complement);
		this.fillDistrictField(district);
		this.fillCityField(city);
		this.fillStateField(state);
		this.fillCountryField(country);
		this.clickOnAddAddressButton();

		// Fill collaborator Tab
		this.fillCollaboratorField(collaborator);
		this.fillDepartamentField(departament);
		this.fillOfficeField(office);
		this.clickOnAddCollaboratorButton();

		this.clickOnSaveButton();
	}

	public void removeOrganization(String fantasyName) {
		this.filterByFantasyName(fantasyName);
		this.clickToRemove();
		this.clickOnYesToConfirmOrganizationRemoval();
	}

	public void clickToDeleteImageLogo() {
		WebElement removeImageLogoButton = this.organizationsPage.getRemoveImageLogoButton();
		this.organizationsPage.seleniumConfig.getWait()
				.until(ExpectedConditions.elementToBeClickable(removeImageLogoButton));
		removeImageLogoButton.click();
	}

	public void clickOnYesToConfirmImageLogoRemoval() {
		this.organizationsPage.getConfirmImageLogoRemovalButton().click();
	}

	public void clearField(WebElement element) {
		element.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
	}

	public String getMandatoryCNPJErrorMessage() {
		return this.organizationsPage.getMandatoryCNPJHint().getText();
	}

	public void clickOnFantasyNameField() {
		this.organizationsPage.getFantasyNameField().click();
	}

	public void clickOnCompanyNameField() {
		this.organizationsPage.getCompanyNameField().click();
	}

	public String getFilterValue() {
		return this.organizationsPage.getFilterField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public void fillCNPJAndClickOnVerify(String cnpj) {
		this.clickOnAddButton();
		this.fillCNPJField(cnpj);
		this.clickOnVerifyButton();
	}

	public boolean isInvalidCharacterQuantityMsgForDDICodeDisplayed() {
		try {
			return this.organizationsPage.getInvalidCharacterQuantityHintForDDICode().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInvalidCharacterQuantityMsgForDDICode() {
		return this.organizationsPage.getInvalidCharacterQuantityHintForDDICode().getText();
	}

	public boolean isInvalidCharacterQuantityMsgForPhoneNumberDisplayed() {
		try {
			return this.organizationsPage.getInvalidCharacterQuantityHintForPhoneNumber().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInvalidCharacterQuantityMsgForPhoneNumber() {
		return this.organizationsPage.getInvalidCharacterQuantityHintForPhoneNumber().getText();
	}

	public boolean isInvalidCharacterQuantityMsgForStreetDisplayed() {
		try {
			return this.organizationsPage.getInvalidCharacterQuantityHintForStreet().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInvalidCharacterQuantityMsgForStreet() {
		return this.organizationsPage.getInvalidCharacterQuantityHintForStreet().getText();
	}

	public boolean isInvalidCharacterQuantityMsgForAddressNumberDisplayed() {
		try {
			return this.organizationsPage.getInvalidCharacterQuantityHintForAddressNumber().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInvalidCharacterQuantityMsgForAddressNumber() {
		return this.organizationsPage.getInvalidCharacterQuantityHintForAddressNumber().getText();
	}

	public boolean isInvalidCharacterQuantityMsgForAddressComplementDisplayed() {
		try {
			return this.organizationsPage.getInvalidCharacterQuantityHintForAddressComplement().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInvalidCharacterQuantityMsgForAddressComplement() {
		return this.organizationsPage.getInvalidCharacterQuantityHintForAddressComplement().getText();
	}

	public boolean isInvalidCharacterQuantityMsgForDistrictDisplayed() {
		try {
			return this.organizationsPage.getInvalidCharacterQuantityHintForDistrict().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInvalidCharacterQuantityMsgForDistrict() {
		return this.organizationsPage.getInvalidCharacterQuantityHintForDistrict().getText();
	}

	public boolean isInvalidCharacterQuantityMsgForCityDisplayed() {
		try {
			return this.organizationsPage.getInvalidCharacterQuantityHintForCity().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInvalidCharacterQuantityMsgForCity() {
		return this.organizationsPage.getInvalidCharacterQuantityHintForCity().getText();
	}

	public boolean isInvalidCharacterQuantityMsgForStateDisplayed() {
		try {
			return this.organizationsPage.getInvalidCharacterQuantityHintForState().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInvalidCharacterQuantityMsgForState() {
		return this.organizationsPage.getInvalidCharacterQuantityHintForState().getText();
	}

	public boolean isInvalidCharacterQuantityMsgForCountryDisplayed() {
		try {
			return this.organizationsPage.getInvalidCharacterQuantityHintForCountry().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInvalidCharacterQuantityMsgForCountry() {
		return this.organizationsPage.getInvalidCharacterQuantityHintForCountry().getText();
	}

	public boolean isSaveImageLogoButtonDisplayed() {
		try {
			return this.organizationsPage.getSaveImageLogoButton().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isImageLogoDisplayed() {
		try {
			return this.organizationsPage.getImageLogoSelected().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isInvalidCharacterQuantityMsgForDescriptionDisplayed() {
		try {
			return this.organizationsPage.getInvalidCharacterQuantityHintForDescription().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInvalidCharacterQuantityMsgForDescription() {
		return this.organizationsPage.getInvalidCharacterQuantityHintForDescription().getText();
	}

	public boolean isInvalidCharacterQuantityMsgForOfficeDisplayed() {
		try {
			return this.organizationsPage.getInvalidCharacterQuantityHintForOffice().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInvalidCharacterQuantityMsgForOffice() {
		return this.organizationsPage.getInvalidCharacterQuantityHintForOffice().getText();
	}

	public boolean isInvalidCharacterQuantityMsgForDepartamentDisplayed() {
		try {
			return this.organizationsPage.getInvalidCharacterQuantityHintForDepartament().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getInvalidCharacterQuantityMsgForDepartament() {
		return this.organizationsPage.getInvalidCharacterQuantityHintForDepartament().getText();
	}
}
