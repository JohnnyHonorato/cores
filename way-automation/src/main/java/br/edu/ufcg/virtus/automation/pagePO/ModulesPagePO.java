package br.edu.ufcg.virtus.automation.pagePO;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.ModulesData;
import br.edu.ufcg.virtus.automation.entity.Module;
import br.edu.ufcg.virtus.automation.page.ModulesPage;

public class ModulesPagePO {

	public ModulesPage modulesPage;
	public CommonPagePO commonPagePO;

	public ModulesPagePO(SeleniumConfig seleniumConfig) {
		this.modulesPage = new ModulesPage(seleniumConfig);
		this.commonPagePO = new CommonPagePO(seleniumConfig);
	}
	
	public void clickOnCoreCard() {
		this.modulesPage.getCorePageCard().click();
	}

	public void clickOnModulesMenu() {
		this.modulesPage.getModulesMenu().click();
	}

	public void clickOnAddModulesButton() {
		this.modulesPage.getAddModulesButton().click();
	}

	public void fillModulesNameField(String moduleName) {
		this.modulesPage.getModuleNameField().sendKeys(moduleName);
	}
	
	public void fillModulesNameField(Keys moduleName) {
		this.modulesPage.getModuleNameField().sendKeys(moduleName);
	}

	public void fillModulesLinkField(String link) {
		this.modulesPage.getModuleLinkField().sendKeys(link);
	}
	
	public void fillModulesLinkField(Keys blankInput) {
		this.modulesPage.getModuleLinkField().sendKeys(blankInput);
	}	

	public void fillModulesIconField(String icon) {
		this.modulesPage.getModuleIconField().sendKeys(icon);
	}
	
	public void fillModulesIconField(Keys blankInput) {
		this.modulesPage.getModuleIconField().sendKeys(blankInput);
	}

	public void selectOpenMode(String openModeType) {
		Select openModeSelect = new Select(this.modulesPage.getModuleOpenModeSelect());
		openModeSelect.selectByVisibleText(openModeType);
	}

	public void clickOnPublicModeToogle() {
		this.modulesPage.getPublicModeToogle().click();
	}

	public void clickOnAddPermissionsButton() {
		this.modulesPage.getPermissionsAddButton().click();
	}

	public void fillPathField(String validPath) {
		this.modulesPage.getModuleModeSPAPath().sendKeys(validPath);
	}

	public void fillPermissionNameField(String validPermissionName) {
		this.modulesPage.getPermissionsNameField().sendKeys(validPermissionName);
	}

	public void fillPermissionLabelField(String validPermissionLabel) {
		this.modulesPage.getPermissionsLabelField().sendKeys(validPermissionLabel);
	}

	public void fillPermissionDescriptionField(String validPermissionDescription) {
		this.modulesPage.getPermissionsDescriptionField().sendKeys(validPermissionDescription);	
	}
	
	public void fillPermissionNameField(Keys validPermissionName) {
		this.modulesPage.getPermissionsNameField().sendKeys(validPermissionName);
	}

	public void fillPermissionLabelField(Keys validPermissionLabel) {
		this.modulesPage.getPermissionsLabelField().sendKeys(validPermissionLabel);
	}

	public void fillPermissionDescriptionField(Keys validPermissionDescription) {
		this.modulesPage.getPermissionsDescriptionField().sendKeys(validPermissionDescription);	
	}
	
	public List<Module> readModuleTable() {

		List<Module> moduleList = new ArrayList<Module>();

		WebElement table = this.modulesPage.getModulesTable();
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for (int i = 1; i < rows.size(); i++) {
			List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
			moduleList.add(new Module(cells.get(0).getText()));
		}
		return moduleList;
	}
	
	public Boolean isModuleDisplayedOnTable(String module) {
		List<Module> moduleList = this.readModuleTable();

		for (Module m : moduleList) {
			if (m.getModuleName().equals(module))
				return true;
		}
		return false;
	}

	public boolean isEmptyNameLabelDisplayed() {
		return this.modulesPage.getInvalidNameLabel().isDisplayed();
	}

	public boolean isInvalidLinkLabelDisplayed() {
		return this.modulesPage.getInvalidLinkLabel().isDisplayed();
	}

	public boolean isInvalidIconLabelDisplayed() {
		return this.modulesPage.getInvalidIconLabel().isDisplayed();
	}
	
	public void fillFormForValidModule(String moduleName, String link, String path) {
		fillModulesNameField(moduleName);
		fillModulesLinkField(link);
		fillModulesIconField(ModulesData.VALID_ICON);
		selectOpenMode(ModulesData.OPEN_MODE_TYPE_SPA);
		fillPathField(path);
		clickOnPublicModeToogle();
		clickOnAddPermissionsButton();
		fillPermissionNameField(ModulesData.VALID_PERMISSION_NAME);
		fillPermissionLabelField(ModulesData.VALID_PERMISSION_LABEL);
		fillPermissionDescriptionField(ModulesData.VALID_PERMISSION_DESCRIPTION);
	}

	public void fillSearchField(String moduleName) {
		this.modulesPage.getSearchInput().sendKeys(moduleName);		
	}

	public boolean isNoModuleFoundLabelDisplayed() {
		try {
			this.modulesPage.getNoModuleFoundLabel();
			return this.modulesPage.getNoModuleFoundLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickOnYesDeleteModal() {
		this.modulesPage.getYesOnDeleteModal().click();
	}
	
	public void clickOnNoDeleteModal() {
		this.modulesPage.getNoOnDeleteModal().click();	
	}
	
	public void removeModule(String moduleName) {
		fillSearchField(moduleName);
		this.commonPagePO.clickOnDeleteButton();
		clickOnYesDeleteModal();
	}

	public void cleanForm() {
		this.modulesPage.getModuleNameField().clear();
		this.modulesPage.getModuleNameField().sendKeys(Keys.SPACE, Keys.BACK_SPACE);
		this.modulesPage.getModuleLinkField().clear();
		this.modulesPage.getModuleLinkField().sendKeys(Keys.SPACE, Keys.BACK_SPACE);
		this.modulesPage.getModuleModeSPAPath().clear();
		this.modulesPage.getModuleModeSPAPath().sendKeys(Keys.SPACE, Keys.BACK_SPACE);
		this.modulesPage.getPermissionsNameField().clear();
		this.modulesPage.getPermissionsNameField().sendKeys(Keys.SPACE, Keys.BACK_SPACE);
		this.modulesPage.getPermissionsLabelField().clear();
		this.modulesPage.getPermissionsLabelField().sendKeys(Keys.SPACE, Keys.BACK_SPACE);
		this.modulesPage.getPermissionsDescriptionField().clear();
		this.modulesPage.getPermissionsDescriptionField().sendKeys(Keys.SPACE, Keys.BACK_SPACE);
	}

	public void cleanPermissions() {
		this.modulesPage.getPermissionsNameField().clear();
		this.modulesPage.getPermissionsNameField().sendKeys(Keys.SPACE, Keys.BACK_SPACE);
		this.modulesPage.getPermissionsLabelField().clear();
		this.modulesPage.getPermissionsLabelField().sendKeys(Keys.SPACE, Keys.BACK_SPACE);
		this.modulesPage.getPermissionsDescriptionField().clear();
		this.modulesPage.getPermissionsDescriptionField().sendKeys(Keys.SPACE, Keys.BACK_SPACE);	
	}
	
	public void clickOnPermissionDeleteButton() {
		this.modulesPage.getPermissionDeleteButton().click();
	}

	public boolean doesModuleHavePermissions() {
		try {
			this.modulesPage.getPermissionsNameField().isDisplayed();
			this.modulesPage.getPermissionsLabelField().isDisplayed();
			this.modulesPage.getPermissionsDescriptionField().isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Boolean isModuleInvalidNameLabelDisplayed() {
		try {
			return this.modulesPage.getInvalidNameLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isModuleInvalidLinkLabelDisplayed() {
		try {
			return this.modulesPage.getInvalidLinkLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isModuleInvalidIconLabelDisplayed() {
		try {
			return this.modulesPage.getInvalidIconLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isModuleInvalidPathLabelDisplayed() {
		try {
			return this.modulesPage.getInvalidPathLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isModuleInvalidPermissionNameLabelDisplayed() {
		try {
			return this.modulesPage.getInvalidPermissionNameLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isModuleInvalidPermissionLabelLabelDisplayed() {
		try {
			return this.modulesPage.getInvalidPermissionLabelLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isModuleInvalidPermissionDescriptionLabelDisplayed() {
		try {
			return this.modulesPage.getInvalidPermissionDescriptionLabel().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getModuleNameFieldValue() {
		return this.modulesPage.getModuleNameField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}
	
	public String getModuleLinkFieldValue() {
		return this.modulesPage.getModuleLinkField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}
	
	public String getModuleModeSPAPathValue() {
		return this.modulesPage.getModuleModeSPAPath().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getPermissionsNameFieldValue() {
		return this.modulesPage.getPermissionsNameField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getPermissionsLabelFieldValue() {
		return this.modulesPage.getPermissionsLabelField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getPermissionsDescriptionFieldValue() {
		return this.modulesPage.getPermissionsDescriptionField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

	public String getModuleIconFieldValue() {
		return this.modulesPage.getModuleIconField().getAttribute(CommonData.ATTRIBUTE_VALUE);
	}

}
