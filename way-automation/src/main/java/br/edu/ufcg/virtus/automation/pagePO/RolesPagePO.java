package br.edu.ufcg.virtus.automation.pagePO;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.edu.ufcg.virtus.automation.config.SeleniumConfig;
import br.edu.ufcg.virtus.automation.entity.Module;
import br.edu.ufcg.virtus.automation.entity.Permission;
import br.edu.ufcg.virtus.automation.entity.Role;
import br.edu.ufcg.virtus.automation.page.CommonPage;
import br.edu.ufcg.virtus.automation.page.RolesPage;

public class RolesPagePO {

	public RolesPage rolesPage;
	public CommonPage commonPage;
	private CommonPagePO commonPagePO;

	public RolesPagePO(SeleniumConfig seleniumConfig, CommonPage commonPage) {
		this.rolesPage = new RolesPage(seleniumConfig);
		this.commonPagePO = new CommonPagePO(seleniumConfig);
		this.commonPage = commonPage;
	}

	public void openRolesPage() {
		this.rolesPage.getRolesMenu().click();
	}

	public void clickOnAddButton() {
		WebElement addButton = this.commonPage.getAddButton();
		addButton.click();
	}

	public List<Role> readRolesTable() {
		List<Role> rolesList = new ArrayList<Role>();
		WebElement table = this.rolesPage.getRolesTable();
		List<WebElement> rows = table.findElements(By.tagName("li"));
		for (int i = 0; i < rows.size(); i++) {
			rolesList.add(new Role(rows.get(i).findElements(By.tagName("div")).get(0).getText()));
		}
		return rolesList;
	}

	public void selectARoleFromTable(String roleName) {
		this.fillSearchRoleField(roleName);
		this.rolesPage.getRolesTable().findElement(By.tagName("li")).click();
	}

	public List<Module> readModulesNamesTable() {
		List<Module> moduleList = new ArrayList<Module>();
		List<WebElement> rows = this.rolesPage.getModulesTable();
		for (int i = 0; i < rows.size(); i++) {
			moduleList.add(new Module(rows.get(i).getText()));
		}
		return moduleList;
	}

	public List<Permission> readPermissionNamesTable() {
		List<Permission> permissionList = new ArrayList<Permission>();
		List<WebElement> rows = this.rolesPage.getModulesTable();
		for (int i = 0; i < rows.size(); i++) {
			permissionList.add(new Permission(rows.get(i).findElement(By.xpath("//*[@id=\"collapse0\"]/div/div/div/label")).getText()));

		}
		return permissionList;
	}

	public void selectAPermissionFromTable(String label, Integer moduleIndex, Integer permissionIndex) {
		this.fillSearchPermissionField(label);
		this.rolesPage.getAllPermissionsCheckbox().get(permissionIndex).click();
	}

	public Boolean isRoleDisplayedOnTable(String role) {
		List<Role> roles = this.readRolesTable();
		for (Role o : roles) {
			if (o.getRoleName().equals(role))
				return true;
		}
		return false;
	}

	public Boolean isModuleNameDisplayedOnTable(String module) {
		List<Module> modules = this.readModulesNamesTable();

		for (Module temp : modules) {
			if (temp.getModuleName().equals(module))
				return true;
		}
		return false;
	}

	public Boolean isPermissionNameDisplayedOnTable(String permission) {
		this.fillSearchPermissionField(permission);
		List<Permission> permissions = this.readPermissionNamesTable();

		for (Permission temp : permissions) {
			if (temp.getPermissionName().equals(permission))
				return true;
		}
		return false;
	}

	public void insertRole(String roleName) {
		this.commonPagePO.goToRolesPage();
		this.clickOnAddButton();
		this.fillRoleNameField(roleName);
		this.commonPagePO.clickOnSaveButton();
	}

	public void removeRoleFromTable(String roleName) {
		this.commonPagePO.goToRolesPage();
		this.fillSearchRoleField(roleName);
		this.commonPage.getDeleteButton().click();
		this.rolesPage.getConfirmRemovalButton().click();
	}

	public void fillSearchRoleField(String role) {
		WebElement searchRole = this.rolesPage.getSearchRoleField();
		this.rolesPage.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(searchRole));
		this.rolesPage.getSearchRoleField().clear();
		this.rolesPage.getSearchRoleField().sendKeys(role);
	}

	public void fillSearchPermissionField(String permission) {
		WebElement searchPermission = this.rolesPage.getSearchPermissionField();
		this.rolesPage.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(searchPermission));
		this.rolesPage.getSearchPermissionField().clear();
		this.rolesPage.getSearchPermissionField().sendKeys(permission);
	}

	public void fillRoleNameField(String role) {
		WebElement roleNameField = this.rolesPage.getRoleNameField();
		this.rolesPage.seleniumConfig.getWait().until(ExpectedConditions.elementToBeClickable(roleNameField));
		this.rolesPage.getRoleNameField().clear();
		this.rolesPage.getRoleNameField().sendKeys(role);
	}

}
