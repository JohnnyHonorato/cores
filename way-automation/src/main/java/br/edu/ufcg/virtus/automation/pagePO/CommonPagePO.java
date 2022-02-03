package br.edu.ufcg.virtus.automation.pagePO;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.ConfigData;
import br.edu.ufcg.virtus.automation.page.CommonPage;

public class CommonPagePO {

    public CommonPage commonPage;

    public CommonPagePO(SeleniumConfig seleniumConfig) {
        this.commonPage = new CommonPage(seleniumConfig);
    }

    public void goToPeoplePage() {
        this.commonPage.driver.get(ConfigData.CORE_APPLICATION_URL);
        this.commonPage.driver.get(ConfigData.CORE_APPLICATION_URL + CommonData.PEOPLE_PATH);
    }

    public void goToModulePage() {
        this.commonPage.driver.get(ConfigData.CORE_APPLICATION_URL);
        this.commonPage.driver.get(ConfigData.CORE_APPLICATION_URL + CommonData.MODULES_PATH);
    }

    public void goToRolesPage() {
        this.commonPage.driver.get(ConfigData.CORE_APPLICATION_URL);
        this.commonPage.driver.get(ConfigData.CORE_APPLICATION_URL + CommonData.ROLES_PATH);
    }

    public void goToOrganizationsPage() {
        this.commonPage.driver.get(ConfigData.BUSINESS_APPLICATION_URL);
        this.commonPage.driver.get(ConfigData.BUSINESS_APPLICATION_URL + CommonData.ORGANIZATIONS_PATH);
    }

    public void goToTrackerPage() {
        this.commonPage.driver.get(ConfigData.PORTAL_APPLICATION_URL + CommonData.TRACKER_PATH);
    }

    public void goToBusinessOpportunityPage() {
        this.commonPage.driver.get(ConfigData.BUSINESS_APPLICATION_URL);
        this.commonPage.driver.get(ConfigData.BUSINESS_APPLICATION_URL + CommonData.BUSINESS_OPORTUNITY_PATH);
    }

    public void goToBoard(String boardId) {
        this.commonPage.driver.get(ConfigData.PORTAL_APPLICATION_URL + CommonData.TRACKER_PATH + boardId);
    }

    public void clickOnBackButton() {
        this.commonPage.getBackButton().click();
    }

    public void clickOnSaveButton() {
        final WebElement saveButton = this.commonPage.getSaveButton();
        this.commonPage.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
    }

    public void clickOnDeleteButton() {
        this.commonPage.getDeleteButton().click();
    }

    public String getToastMsg() {
        return this.commonPage.getToast().getText();
    }

    public Boolean isDeleteButtonDisplayed() {
        try {
            this.commonPage.getDeleteButton();
            return this.commonPage.getDeleteButton().isDisplayed();
        } catch(final Exception e) {
            return false;
        }
    }

    public void scrollDownPage() {
        final JavascriptExecutor js = (JavascriptExecutor) this.commonPage.driver;
        js.executeScript(CommonData.SCROLL_DOWN);
    }

    public Boolean isSaveButtonEnabled() {
        return this.commonPage.getSaveButton().isEnabled();
    }

    public void clickOnEditButton() {
        this.commonPage.getEditButton().click();
    }

    public void clickOnYesToConfirmRemoval() {
        this.commonPage.getConfirmRemovalButton().click();
    }
}
