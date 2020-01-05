package com.project_management.belfazt.exceptions;

public class UserIsAlreadyATeamMemberExceptionResponse {

	private String message;

	public UserIsAlreadyATeamMemberExceptionResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
