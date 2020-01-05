package com.project_management.belfazt.exceptions;

public class UserNotExistsExceptionResponse {

	private String username;

	public UserNotExistsExceptionResponse(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}	
}
