package com.project_management.belfazt.exceptions;

public class AccessRestrictedExceptionResponse {
	private String accessDenied;

	public AccessRestrictedExceptionResponse(String accessDenied) {
		super();
		this.accessDenied = accessDenied;
	}

	public String getAccessDenied() {
		return accessDenied;
	}

	public void setAccessDenied(String accessDenied) {
		this.accessDenied = accessDenied;
	}
}
