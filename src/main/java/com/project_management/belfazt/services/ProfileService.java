package com.project_management.belfazt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project_management.belfazt.dao.UserRepository;
import com.project_management.belfazt.model.User;

@Service
public class ProfileService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User getUserDetails(String username) {
		
		return userRepository.findByUsername(username);
		
	}
	
	public User updateFullname(String fullname, String username) {
		
		User user = getUserDetails(username);
		
		user.setFullname(fullname);
		
		return userRepository.save(user);
		
	}
	
	public void updatePassword(String password, String username) {
		
		User user = getUserDetails(username);
		
		user.setPassword(bCryptPasswordEncoder.encode(password));
		
		userRepository.save(user);
		
	}
	
}
