package br.edu.ufcg.virtus.automation.pagePO;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.entity.BusinessOpportunity;
import br.edu.ufcg.virtus.automation.page.BusinessOpportunityPage;
import br.edu.ufcg.virtus.automation.page.CommonPage;

public class BusinessOpportunityPagePO {

	public SeleniumConfig seleniumConfig;
	public BusinessOpportunityPage businessOpportunityPage;
	public CommonPage commonPage;
	public CommonPagePO commonPagePO;

	public BusinessOpportunityPagePO(SeleniumConfig seleniumConfig, CommonPage commonPage) {
		this.seleniumConfig = seleniumConfig;
		this.businessOpportunityPage = new BusinessOpportunityPage(seleniumConfig);
		this.commonPage = commonPage;
		this.commonPagePO = new CommonPagePO(seleniumConfig);
	}

	public void fillTitleField(String title) {
		WebElement titleField = this.businessOpportunityPage.getTitleField();
		this.businessOpportunityPage.seleniumConfig.getWait()
				.until(ExpectedConditions.elementToBeClickable(titleField));
		this.businessOpportunityPage.getTitleField().clear();
		this.businessOpportunityPage.getTitleField().sendKeys(title);
	}

	public void fillOrganizationDropbox(String organization) {
		WebElement organizationDropbox = this.businessOpportunityPage.getOrganizationDropbox();
		this.businessOpportunityPage.seleniumConfig.getWait()
				.until(ExpectedConditions.elementToBeClickable(organizationDropbox));
		organizationDropbox.click();
		
		Select drpOrganization = new Select(organizationDropbox);
		drpOrganization.selectByVisibleText(organization);
	}

	public void fillDescriptionField(String description) {
		WebElement descriptionField = this.businessOpportunityPage.getDescriptionField();
		this.businessOpportunityPage.seleniumConfig.getWait()
				.until(ExpectedConditions.elementToBeClickable(descriptionField));
		this.businessOpportunityPage.getDescriptionField().clear();
		this.businessOpportunityPage.getDescriptionField().sendKeys(description);
	}

	public void fillRepresentativeDropbox(String representative) {
		WebElement representativeDropbox = this.businessOpportunityPage.getRepresentativeDropbox();
		this.businessOpportunityPage.seleniumConfig.getWait()
				.until(ExpectedConditions.elementToBeClickable(representativeDropbox));

		Select drpRepresentative = new Select(representativeDropbox);
		drpRepresentative.selectByVisibleText(representative);
	}

	public void fillSearchBusinessOpportunityField(String name) {
		WebElement searchBO = this.businessOpportunityPage.getSearchBusinessOpportunityField();
		this.businessOpportunityPage.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(searchBO));
		this.businessOpportunityPage.getSearchBusinessOpportunityField().clear();
		this.businessOpportunityPage.getSearchBusinessOpportunityField().sendKeys(name);
	}

	public void insertBusinessOpportunity(String title, String organization, String description) {
		this.commonPagePO.goToBusinessOpportunityPage();
		this.commonPage.getAddButton().click();
		this.fillTitleField(title);
		this.fillOrganizationDropbox(organization);
		this.fillDescriptionField(description);
		this.commonPage.getSaveButton().click();
	}

	public void insertBusinessOpportunity(String title, String organization, String description,
			String representative) {
		this.commonPagePO.goToBusinessOpportunityPage();
		this.commonPage.getAddButton().click();
		this.fillTitleField(title);
		this.fillOrganizationDropbox(organization);
		this.fillDescriptionField(description);

		this.fillRepresentativeDropbox(representative);
		this.commonPage.getSaveButton().click();
	}

	public void fillAllBusinessOpportunityFields(String title, String organization, String description,
			String representative) {
		this.commonPagePO.goToBusinessOpportunityPage();
		this.commonPage.getAddButton().click();
		this.fillTitleField(title);
		this.fillOrganizationDropbox(organization);
		this.fillDescriptionField(description);
		this.fillRepresentativeDropbox(representative);
	}

	public void editBusinessOpportunityTitle(String titleOrigin, String titleFinal) {
		this.commonPagePO.goToBusinessOpportunityPage();
		this.fillSearchBusinessOpportunityField(titleOrigin);
		this.commonPage.getEditButton().click();
		this.fillTitleField(titleFinal);
	}

	public void editBusinessOpportunityOrganization(String titleOrigin, String organization) {
		this.commonPagePO.goToBusinessOpportunityPage();
		this.fillSearchBusinessOpportunityField(titleOrigin);
		this.commonPage.getEditButton().click();
		this.fillOrganizationDropbox(organization);
	}

	public void editBusinessOpportunityDescription(String titleOrigin, String description) {
		this.commonPagePO.goToBusinessOpportunityPage();
		this.fillSearchBusinessOpportunityField(titleOrigin);
		this.commonPage.getEditButton().click();
		this.fillDescriptionField(description);
	}

	public void editBusinessOpportunityAllFields(String titleOrigin, String titleFinal, String organization,
			String description) {
		this.commonPagePO.goToBusinessOpportunityPage();
		this.fillSearchBusinessOpportunityField(titleOrigin);
		this.commonPage.getEditButton().click();
		this.fillTitleField(titleFinal);
		this.fillOrganizationDropbox(organization);
		this.fillDescriptionField(description);
	}

	public void removeBusinessOpportunityFromTable(String name) {
		this.commonPagePO.goToBusinessOpportunityPage();
		this.fillSearchBusinessOpportunityField(name);
		this.commonPage.getDeleteButton().click();
		this.businessOpportunityPage.getConfirmRemovalButton().click();
	}

	public List<BusinessOpportunity> readBusinessOpportunityTable() {
		List<BusinessOpportunity> businessOpportunityList = new ArrayList<BusinessOpportunity>();
		WebElement table = this.businessOpportunityPage.getBusinessOpportunityTable();
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (int i = 1; i < rows.size(); i++) {
			List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
			businessOpportunityList.add(new BusinessOpportunity(cells.get(0).getText()));
		}
		return businessOpportunityList;
	}

	public Boolean isBusinesOportunityDisplayedOnTable(String businessOpportunity) {
		this.seleniumConfig.getWait()
				.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CommonPage.TABLE_LOADING)));
		List<BusinessOpportunity> businessOportunities = this.readBusinessOpportunityTable();
		for (BusinessOpportunity o : businessOportunities) {
			if (o.getBusinessOpportunityName().equals(businessOpportunity))
				return true;
		}
		return false;
	}

}
