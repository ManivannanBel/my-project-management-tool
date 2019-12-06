package com.project_management.belfazt.payload;

import javax.validation.constraints.NotBlank;

public class UpdatePasswordRequest {

	@NotBlank(message = "password field should not be empty")
	private String password;
	@NotBlank(message = "password field should not be empty")
	private String confirmPassword;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}
