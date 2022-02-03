package br.edu.ufcg.virtus.automation.entity;

public class Permission {
	
	private String permissionName;
	private String label;
	private String description;
	
	public Permission(String permissionName
//		, String label, String description
		) {
		this.permissionName = permissionName;
//		this.label = label;
//		this.description = description;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

//	public String getLabel() {
//		return label;
//	}
//
//	public void setLabel(String label) {
//		this.label = label;
//	}
//	
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
}
