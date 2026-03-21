package com.company.Incident.payload;

public class RoleDTO {

	private int roleId;
	private String roleName;
	private String roleDiscription;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDiscription() {
		return roleDiscription;
	}

	public void setRoleDiscription(String roleDiscription) {
		this.roleDiscription = roleDiscription;
	}
}
