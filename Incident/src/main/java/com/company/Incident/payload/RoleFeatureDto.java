package com.company.Incident.payload;

import java.util.List;

public class RoleFeatureDto {

	private List<RoleInfo> roles;
	private List<FeatureInfo> features;

	private RoleFeatureDto(Builder builder) {
		this.roles = builder.roles;
		this.features = builder.features;
	}

	public List<RoleInfo> getRoles() {
		return roles;
	}

	public List<FeatureInfo> getFeatures() {
		return features;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class RoleInfo {

		private int id;
		private String name;

		private RoleInfo(RoleInfoBuilder builder) {
			this.id = builder.id;
			this.name = builder.name;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public static RoleInfoBuilder builder() {
			return new RoleInfoBuilder();
		}

		public static class RoleInfoBuilder {

			private int id;
			private String name;

			public RoleInfoBuilder id(int id) {
				this.id = id;
				return this;
			}

			public RoleInfoBuilder name(String name) {
				this.name = name;
				return this;
			}

			public RoleInfo build() {
				return new RoleInfo(this);
			}
		}
	}

	public static class FeatureInfo {

		private int id;
		private String name;

		private FeatureInfo(FeatureInfoBuilder builder) {
			this.id = builder.id;
			this.name = builder.name;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public static FeatureInfoBuilder builder() {
			return new FeatureInfoBuilder();
		}

		public static class FeatureInfoBuilder {

			private int id;
			private String name;

			public FeatureInfoBuilder id(int id) {
				this.id = id;
				return this;
			}

			public FeatureInfoBuilder name(String name) {
				this.name = name;
				return this;
			}

			public FeatureInfo build() {
				return new FeatureInfo(this);
			}
		}
	}

	public static class Builder {

		private List<RoleInfo> roles;
		private List<FeatureInfo> features;

		public Builder roles(List<RoleInfo> roles) {
			this.roles = roles;
			return this;
		}

		public Builder features(List<FeatureInfo> features) {
			this.features = features;
			return this;
		}

		public RoleFeatureDto build() {
			return new RoleFeatureDto(this);
		}
	}
}
