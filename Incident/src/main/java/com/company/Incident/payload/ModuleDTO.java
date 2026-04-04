package com.company.Incident.payload;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDTO {

	@Digits(integer = 10, fraction = 0, message = "Module ID must be a valid number")
	private int moduleId;

	@NotBlank(message = "Module name is required")
	private String moduleName;

	private String createdBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String createdDate;

	private String modifiedBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String modifiedDate;

}
