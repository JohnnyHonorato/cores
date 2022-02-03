package br.edu.ufcg.virtus.automation.entity;

import java.util.List;

public class Module {
	
	private String moduleName;
	private String link;
	private String icon;
	private String openMode;
	private String pathSPA;
	private Boolean isPublic;
	private List<Permission> permissionList;
	
	public Module(String moduleName
//			, String link, String icon, String openMode, String pathSPA,
//			Boolean isPublic, List<Permission> permissionList
			) {
		this.moduleName = moduleName;
//		this.link = link;
//		this.icon = icon;
//		this.openMode = openMode;
//		this.pathSPA = pathSPA;
//		this.isPublic = isPublic;
//		this.permissionList = permissionList;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

//	public String getLink() {
//		return link;
//	}
//
//	public void setLink(String link) {
//		this.link = link;
//	}
//	
//	public String getIcon() {
//		return icon;
//	}
//
//	public void setIcon(String icon) {
//		this.icon = icon;
//	}
//	
//	public String getOpenMode() {
//		return openMode;
//	}
//	
//	public void setOpenMode(String openMode) {
//		this.openMode = openMode;
//	}
//
//	public String getPathSPA() {
//		return pathSPA;
//	}
//
//	public void setPathSPA(String pathSPA) {
//		this.pathSPA = pathSPA;
//	}
//
//	public Boolean getIsPublic() {
//		return isPublic;
//	}
//
//	public void setIsPublic(Boolean isPublic) {
//		this.isPublic = isPublic;
//	}
//
//	public List<Permission> getPermissionList() {
//		return permissionList;
//	}
//
//	public void setPermissionList(List<Permission> permissionList) {
//		this.permissionList = permissionList;
//	}
}
