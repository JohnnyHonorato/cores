package br.edu.ufcg.virtus.automation.pagePO;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.entity.People;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.page.PeoplePage;

public class PeoplePagePO {

	public PeoplePage peoplePage;
	public CommonPage commonPage;

	public PeoplePagePO(SeleniumConfig seleniumConfig, CommonPage commonPage) {
		this.peoplePage = new PeoplePage(seleniumConfig);
		this.commonPage = commonPage;
	}

	public void clickOnMenuPeople() {
		this.peoplePage.getMenuPeople().click();
	}

	public void clickOnAddButton() {
		this.peoplePage.getAddButton().click();
	}

	public void clickOnContactInfoCard() {
		this.peoplePage.getContactInfoCard().click();
	}

	public void fillPeopleNameField(String name) {
		this.peoplePage.getPeopleNameField().sendKeys(name);
	}

	public void fillEmailField(String email) {
		this.peoplePage.getEmailField().sendKeys(email);
	}

	public void selectEmailType(String emailType) {
		Select emailTypeSelect = new Select(this.peoplePage.getEmailTypeField());
		emailTypeSelect.selectByVisibleText(emailType);
	}

	public void clickOnEmailAddButton() {
		this.peoplePage.getEmailAddButton().click();
	}

	public void clickOnSaveButton() {
		this.peoplePage.getSaveButton().click();
	}

	public List<People> readPeopleTable() {

		List<People> peopleList = new ArrayList<People>();

		WebElement table = this.peoplePage.getPeopleTable();
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (int i = 1; i < rows.size(); i++) {
			List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
			peopleList.add(new People(cells.get(0).getText(), cells.get(1).getText()));
		}
		return peopleList;
	}

	public Boolean isPeopleDisplayedOnTable(String name) {
		List<People> peopleList = this.readPeopleTable();

		for (People p : peopleList) {
			if (p.getName().equals(name))
				return true;
		}
		return false;
	}

	public Boolean isInvalidEmailLabelDisplayed() {
		try {
			this.peoplePage.getInvalidEmailLabel();
			return this.peoplePage.getInvalidEmailLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean isSaveButtonClickable() {
		return this.peoplePage.getSaveButton().isEnabled();
	}

	public Boolean isAddEmailButtonClickable() {
		return this.peoplePage.getEmailAddButton().isEnabled();
	}

	public void fillSearchBarField(String data) {
		this.peoplePage.getSearchBarField().sendKeys(data);
		this.peoplePage.getPeopleTable();
	}

	public Boolean isNoUserFoundLabelDisplayed() {
		try {
			this.peoplePage.getNoUserFoundLabel();
			return this.peoplePage.getNoUserFoundLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickOnRemoveButton() {
		this.peoplePage.getRemoveButton().click();
	}

	public void clickOnNoRemoveModalButton() {
		this.peoplePage.getRemoveModalNoButton().click();
	}

	public void clickOnYesRemoveModalButton() {
		this.peoplePage.getRemoveModalYesButton().click();
	}

	public void fillHowToBeCalledField(String data) {
		this.peoplePage.getHowToBeCalledField().sendKeys(data);
	}

	public void fillCpfField(String cpf) {
		this.peoplePage.getCpfField().sendKeys(cpf);
	}

	public void clickOnPhoneTab() {
		this.peoplePage.getPhoneTab().click();
	}

	public void clickOnEmailTab() {
		this.peoplePage.getEmailTab().click();
	}

	public void clickOnAdressTab() {
		this.peoplePage.getAdressTab().click();
	}

	public void fillPhoneCodeField(String phoneCode) {
		this.peoplePage.getPhoneCode().sendKeys(phoneCode);
	}

	public void fillPhoneNumberField(String phoneNumber) {
		this.peoplePage.getPhoneNumber().sendKeys(phoneNumber);
	}

	public void selectPhoneType(String phoneType) {
		Select emailTypeSelect = new Select(this.peoplePage.getPhoneTypeSelect());
		emailTypeSelect.selectByVisibleText(phoneType);
	}

	public void selectAdressType(String adressType) {
		Select emailTypeSelect = new Select(this.peoplePage.getAdressTypeSelect());
		emailTypeSelect.selectByVisibleText(adressType);
	}

	public void fillAdressCEPField(String adressCEP) {
		this.peoplePage.getAdressCep().sendKeys(adressCEP);
	}

	public void fillAdressStreetField(String adressStreet) {
		this.peoplePage.getAdressStreet().sendKeys(adressStreet);
	}

	public void fillAdressNumberField(String adressNumber) {
		this.peoplePage.getAdressNumber().sendKeys(adressNumber);
	}

	public void fillAdressComplementField(String adressComplement) {
		this.peoplePage.getAdressComplement().sendKeys(adressComplement);
	}

	public void fillAdressNeighborhoodField(String adressNeighborhood) {
		this.peoplePage.getAdressNeighborhood().sendKeys(adressNeighborhood);
	}

	public void fillAdressCityField(String adressCity) {
		this.peoplePage.getAdressCity().sendKeys(adressCity);
	}

	public void fillAdressStateField(String adressState) {
		this.peoplePage.getAdressState().sendKeys(adressState);
	}

	public void fillAdressCountryField(String adressCountry) {
		this.peoplePage.getAdressCountry().sendKeys(adressCountry);
	}

	public void clickOnAddAdressButton() {
		this.peoplePage.getAddAdressButton().click();
	}

	public void clickOnAddPhoneButton() {
		this.peoplePage.getPhoneAddButton().click();
	}

	public void clickOnEditButton() {
		this.peoplePage.getEditButton().click();
	}

	public void clickOnEditEmailButton() {
		this.peoplePage.getEditEmailButton().click();
	}

	public void cleanForm() {
		this.peoplePage.getPeopleNameField().clear();
		this.peoplePage.getPeopleNameField().sendKeys(Keys.SPACE, Keys.BACK_SPACE);

		clickOnEditEmailButton();
		this.peoplePage.getEmailField().clear();
		this.peoplePage.getEmailField().sendKeys(Keys.SPACE, Keys.BACK_SPACE);
	}

	public void clearLoginField() {
		this.peoplePage.getUserLoginField().clear();
		this.peoplePage.getUserLoginField().sendKeys(Keys.SPACE, Keys.BACK_SPACE);
	}

	public Boolean isMessageToastDisplayed() {
		return this.commonPage.getToast().isDisplayed();

	}

	public Boolean isPeopleInvalidNameLabelDisplayed() {
		try {
			this.peoplePage.getPeopleInvalidNameLabel();
			return this.peoplePage.getPeopleInvalidNameLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean isHowToBeCalledInvalidlabelDisplayed() {
		try {
			this.peoplePage.getHowTobeCalledLabel();
			return this.peoplePage.getHowToBeCalledField().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean isCpfnvalidlabelDisplayed() {
		try {
			this.peoplePage.getCpfLabel();
			return this.peoplePage.getCpfField().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickOnAccessDataTab() {
		this.peoplePage.getAccessDataTab().click();
	}

	public void fillUserLoginField(String login) {
		this.peoplePage.getUserLoginField().sendKeys(login);
	}

	public void selectUserRole(String userRole) {
		this.peoplePage.getUserRolesSelect().sendKeys(userRole, Keys.ENTER);
	}

	public void clickOnUserAccessToogle() {
		this.peoplePage.getUserAccessToogle().click();
	}

	public boolean isInvalidLoginLabelDisplayed() {
		try {
			this.peoplePage.getInvalidUserLabel();
			return this.peoplePage.getInvalidUserLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isInvalidCountryCodeLabelDisplayed() {
		try {
			this.peoplePage.getCountryCodeLabel();
			return this.peoplePage.getPhoneCode().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isInvalidPhoneLabelDisplayed() {
		try {
			this.peoplePage.getPhoneNumberLabel();
			return this.peoplePage.getPhoneNumber().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isInvalidCEPLabelDisplayed() {
		try {
			this.peoplePage.getCepLabel();
			return this.peoplePage.getAdressCep().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isInvalidStreetLabelDisplayed() {
		try {
			this.peoplePage.getStreetLabel();
			return this.peoplePage.getAdressStreet().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isInvalidNumberLabelDisplayed() {
		try {
			this.peoplePage.getNumberLabel();
			return this.peoplePage.getAdressNumber().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isInvalidComplementLabelDisplayed() {
		try {
			this.peoplePage.getComplementLabel();
			return this.peoplePage.getAdressComplement().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isInvalidNeighborhoodLabelDisplayed() {
		try {
			this.peoplePage.getNeighborhoodLabel();
			return this.peoplePage.getAdressNeighborhood().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isInvalidCityLabelDisplayed() {
		try {
			this.peoplePage.getCityLabel();
			return this.peoplePage.getAdressCity().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isInvalidStateLabelDisplayed() {
		try {
			this.peoplePage.getStateLabel();
			return this.peoplePage.getAdressState().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isInvalidCountryLabelDisplayed() {
		try {
			this.peoplePage.getCountryLabel();
			return this.peoplePage.getAdressCountry().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getPeopleNameFieldValue() {
		return this.peoplePage.getPeopleNameField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getHowToBeCalledFieldValue() {
		return this.peoplePage.getHowToBeCalledField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getCpfFieldValue() {
		return this.peoplePage.getCpfField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getEmailFieldValue() {
		return this.peoplePage.getEmailField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getPhoneCodeValue() {
		return this.peoplePage.getPhoneCode().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getPhoneNumberValue() {
		return this.peoplePage.getPhoneNumber().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getAdressCepValue() {
		return this.peoplePage.getAdressCep().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getAdressStreetValue() {
		return this.peoplePage.getAdressStreet().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}
	
	public String getAdressStateValue() {
		return this.peoplePage.getAdressState().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getAdressNumberValue() {
		return this.peoplePage.getAdressNumber().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getAdressComplementValue() {
		return this.peoplePage.getAdressComplement().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getAdressNeighborhoodValue() {
		return this.peoplePage.getAdressNeighborhood().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getAdressCityValue() {
		return this.peoplePage.getAdressCity().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getAdressCountryValue() {
		return this.peoplePage.getAdressCountry().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getUserLoginFieldValue() {
		return this.peoplePage.getUserLoginField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

}
