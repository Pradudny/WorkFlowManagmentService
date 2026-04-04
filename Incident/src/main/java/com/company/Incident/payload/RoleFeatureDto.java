package com.company.Incident.payload;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RoleFeatureDto {

	@NotNull(message = "Roles list cannot be null")
	private List<RoleInfo> roles;
	@NotNull(message = "Features list cannot be null")
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

		@Positive(message = "Role ID must be a positive integer")
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

			@Positive(message = "Role ID must be a positive integer")
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
