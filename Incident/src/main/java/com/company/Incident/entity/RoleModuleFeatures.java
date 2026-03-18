package com.company.Incident.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="role_module_features")
public class RoleModuleFeatures {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rmfId;
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role roleId;
	
	@ManyToOne
	@JoinColumn(name="featureModule_id")
	private FeatureModule featureModuleId;
	
	@Column
	private String createdBy;
	
	@Column
	private String ModifiedBy;
	
	@Column
	private String createdDate;
	
	public int getRmfId() {
		return rmfId;
	}

	public void setRmfId(int rmfId) {
		this.rmfId = rmfId;
	}

	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	public FeatureModule getFeatureModuleId() {
		return featureModuleId;
	}

	public void setFeatureModuleId(FeatureModule featureModuleId) {
		this.featureModuleId = featureModuleId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return ModifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	
	
}
