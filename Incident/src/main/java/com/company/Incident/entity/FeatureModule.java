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
@Table(name="module_features")
public class FeatureModule{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int moduleFeaturesId;
	
	@ManyToOne
	@JoinColumn(name="feature_id")
	private Feature featureId;
	
	@ManyToOne
	@JoinColumn(name="module_id")
	private Module moduleId;
	
	@Column
	private String createdBy;
	
	@Column
	private String modifiedBy;
	
	@Column
	private String createdDate;
	
	@Column
	private String modifiedDate;

	public int getModuleFeaturesId() {
		return moduleFeaturesId;
	}

	public void setModuleFeaturesId(int moduleFeaturesId) {
		this.moduleFeaturesId = moduleFeaturesId;
	}

	public Feature getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Feature featureId) {
		this.featureId = featureId;
	}

	public Module getModuleId() {
		return moduleId;
	}

	public void setModuleId(Module moduleId) {
		this.moduleId = moduleId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	

}
