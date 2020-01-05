package com.project_management.belfazt.exceptions;

public class AccessRestrictedException {
	
	private String accessDenied;

	public AccessRestrictedException(String accessDenied) {
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
