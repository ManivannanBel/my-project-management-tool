package com.project_management.belfazt.payload;

import javax.validation.constraints.NotBlank;

public class UpdateFullnameRequest {

	@NotBlank(message = "Fullname should not be empty")
	private String fullname;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String data) {
		this.fullname = data;
	}
	
}
