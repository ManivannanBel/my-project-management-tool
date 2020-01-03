package com.project_management.belfazt.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project_management.belfazt.model.User;
import com.project_management.belfazt.payload.UpdateFullnameRequest;
import com.project_management.belfazt.payload.UpdatePasswordRequest;
import com.project_management.belfazt.payload.UserProfileResponse;
import com.project_management.belfazt.services.ProfileService;
import com.project_management.belfazt.services.ValidationErrorService;
import com.project_management.belfazt.validator.UserValidator;


@RestController
@RequestMapping("api/profile")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private ValidationErrorService validationErrorService;
	
	@Autowired
	private UserValidator userValidator;
	
	@GetMapping("")
	public ResponseEntity<?> profile(Principal principal){
		
		UserProfileResponse userProfile = profileService.getUserProfileDetails(principal.getName());
		
		return new ResponseEntity<UserProfileResponse>(userProfile, HttpStatus.OK);
	}
	
	@PutMapping("/updateFullname")
	public ResponseEntity<?> updateFullname(@Valid @RequestBody UpdateFullnameRequest request, BindingResult result, Principal principal){
		//System.err.println(fullname);
		ResponseEntity<?> errorMap = validationErrorService.validateError(result);
		if(errorMap != null)return errorMap;
		
		UserProfileResponse userProfile = profileService.updateFullname(request.getFullname(), principal.getName());
		
		return new ResponseEntity<UserProfileResponse>(userProfile, HttpStatus.OK);
		
	}
	
	@PutMapping("/updatePassword")
	public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordRequest request, BindingResult result, Principal principal){
		
		User nUser = new User();
		nUser.setPassword(request.getPassword());
		nUser.setConfirmPassword(request.getConfirmPassword());
		userValidator.validate(nUser, result);
		
		ResponseEntity<?> errorMap = validationErrorService.validateError(result);
		if(errorMap != null) return errorMap;
		
		profileService.updatePassword(request.getPassword(), principal.getName());
		
		return new ResponseEntity<String>("Password changed", HttpStatus.OK); 
	}
}
