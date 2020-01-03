package com.project_management.belfazt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project_management.belfazt.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
	
	//User findById(Long Id);
	User getById(Long id);
	
	@Query("SELECT username FROM user LIKE '%?%' AND username!='?'")
	List<String> findAllMatchingUsernames(String queryString, String currentUser);
}
