package com.project_management.belfazt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessRestrictedException extends RuntimeException{
	
	public AccessRestrictedException(String message) {
		super(message);
	}
	
}
