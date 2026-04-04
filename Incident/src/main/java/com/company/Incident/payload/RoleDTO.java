package com.company.Incident.payload;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

	@Digits(integer = 10, fraction = 0, message = "Role ID must be a valid number")
	private int roleId;
	@NotBlank(message = "Role name is required")
	private String roleName;
	@NotBlank(message = "Role description is required")
	private String roleDiscription;

}
