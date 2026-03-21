package com.company.Incident.payload;

import java.util.List;

public class AuthorizeFeatureRequest {

	private int roleId;
	private List<FeatureAccess> features;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public List<FeatureAccess> getFeatures() {
		return features;
	}

	public void setFeatures(List<FeatureAccess> features) {
		this.features = features;
	}

	public static class FeatureAccess {

		private int featureId;
		private boolean enabled;

		public int getFeatureId() {
			return featureId;
		}

		public void setFeatureId(int featureId) {
			this.featureId = featureId;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	}
}
