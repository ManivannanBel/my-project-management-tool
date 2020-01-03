package com.project_management.belfazt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project_management.belfazt.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);
	
	//User findById(Long Id);
	User getById(Long id);
	
	@Query("SELECT u FROM User u WHERE username LIKE %?1% AND username!=?2")
	List<User> findAllMatchingUsernames(String queryString, String currentUser);
}
