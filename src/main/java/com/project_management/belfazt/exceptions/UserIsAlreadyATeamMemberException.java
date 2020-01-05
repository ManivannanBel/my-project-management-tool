package com.project_management.belfazt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserIsAlreadyATeamMemberException extends RuntimeException{
	
	public UserIsAlreadyATeamMemberException(String message) {
		super(message);
	}
	
}
