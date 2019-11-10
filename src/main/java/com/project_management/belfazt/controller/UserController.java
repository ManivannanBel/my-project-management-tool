package com.project_management.belfazt.controller;

import static com.project_management.belfazt.security.SecurityConstants.TOKEN_PREFIX;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project_management.belfazt.model.User;
import com.project_management.belfazt.payload.JWTLoginSuccessResponse;
import com.project_management.belfazt.payload.LoginRequest;
import com.project_management.belfazt.security.JWTTokenProvider;
import com.project_management.belfazt.services.UserService;
import com.project_management.belfazt.services.ValidationErrorService;
import com.project_management.belfazt.validator.UserValidator;;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private ValidationErrorService validationErrorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JWTTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
		
		ResponseEntity<?> errorMap = validationErrorService.validateError(result);
		if(errorMap!=null) return errorMap;
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
		); 
		 
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
		
		return new ResponseEntity<JWTLoginSuccessResponse>(new JWTLoginSuccessResponse(true, jwt), HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
		//validate passwords
		userValidator.validate(user, result);
		
		ResponseEntity<?> errorMap = validationErrorService.validateError(result);
		if(errorMap!=null) return errorMap;
		
		User newUser = userService.saveUser(user);
		
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
}
