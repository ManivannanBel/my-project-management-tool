package com.project_management.belfazt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project_management.belfazt.dao.UserRepository;
import com.project_management.belfazt.model.User;
import com.project_management.belfazt.payload.UserProfileResponse;

@Service
public class ProfileService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User getUserDetails(String username) {
		return userRepository.findByUsername(username);
	}
	
	public UserProfileResponse getUserProfileDetails(String username) {
		User user = getUserDetails(username);
		UserProfileResponse userProfileData = new UserProfileResponse(user.getUsername(), user.getFullname(), user.getCreated_at(), user.getUpdated_at(), user.getLast_login(), user.getProjects());
		return userProfileData;
	}
	
	public UserProfileResponse updateFullname(String fullname, String username) {
		
		User user = getUserDetails(username);
		
		user.setFullname(fullname);
		
		userRepository.save(user);
		
		UserProfileResponse userProfileData = new UserProfileResponse(user.getUsername(), user.getFullname(), user.getCreated_at(), user.getUpdated_at(), user.getLast_login(), user.getProjects());
		return userProfileData;
		
	}
	
	public void updatePassword(String password, String username) {
		
		User user = getUserDetails(username);
		
		user.setPassword(bCryptPasswordEncoder.encode(password));
		
		userRepository.save(user);
		
	}
	
}
