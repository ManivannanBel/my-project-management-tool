package com.project_management.belfazt.exceptions;

public class UsernameAlreadyExistsResponse {

	private String username;

	public UsernameAlreadyExistsResponse(String username) {
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
