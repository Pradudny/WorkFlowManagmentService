package com.company.Incident.payload;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	@Digits(integer = 10, fraction = 0, message = "User ID must be a valid number")
	private int userId;

	@NotBlank(message = "First name is required")
	// @Pattern(regexp = "[a-zA-Z]$", message = "First name can only contain
	// letters")
	private String userFname;

	@NotBlank(message = "Last name is required")
	// @Pattern(regexp = "[a-zA-Z]$", message = "Last name can only contain
	// letters")
	private String userLname;

	@NotBlank(message = "Department is required")
	@Pattern(regexp = "^[a-zA-Z 0-9]+$", message = "Department can only contain letters and spaces")
	private String department;

	@NotBlank(message = "User role is required")
	private String urole;

	@NotBlank(message = "Birth date is required")
	private String birthDate;

	@NotBlank(message = "Address is required")
	private String address;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;

	@Pattern(regexp = "^[0-9]{10}$", message = "phone number must be 10 digits")
	private String phoneNumber;

	private String createdBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String createdDate;
}