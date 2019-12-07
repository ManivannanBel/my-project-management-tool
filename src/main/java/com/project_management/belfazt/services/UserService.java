package com.project_management.belfazt.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project_management.belfazt.dao.UserRepository;
import com.project_management.belfazt.exceptions.UsernameAlreadyExistsException;
import com.project_management.belfazt.model.User;

@Service 
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {

		try {
			
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			
			//username should be unique
			
			newUser.setUsername(newUser.getUsername());
			//password and confirm password match
			newUser.setConfirmPassword("");
			
			return userRepository.save(newUser);
			
		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exist");
		}	
	}
	
	public void updateLastLogin(String username) {
		User user = userRepository.findByUsername(username);
		
		user.setLast_login(new Date());
		
		userRepository.save(user);
		
	}
	
}
