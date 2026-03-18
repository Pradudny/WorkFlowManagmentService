package com.company.Incident.payload;

import java.beans.JavaBean;

import com.company.Incident.entity.User;

@JavaBean
public class UserDTO {

		private String userFname;
		private String userLname;
		private String department;
		private String urole;
		private String birthDate;
		private String address;
		private String email;
		private Long phoneNumber;
		private User createdBy;
		private String createdDate;
		

		

		public String getUserFname() {
			return userFname;
		}

		public void setUserFname(String userFname) {
			this.userFname = userFname;
		}

		public String getUserLname() {
			return userLname;
		}

		public void setUserLname(String userLname) {
			this.userLname = userLname;
		}

		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		public String getUrole() {
			return urole;
		}

		public void setUrole(String urole) {
			this.urole = urole;
		}

		public String getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(String birthDate) {
			this.birthDate = birthDate;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Long getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(Long phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public User getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(User createdBy) {
			this.createdBy = createdBy;
		}

		public String getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
	}