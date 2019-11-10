package com.project_management.belfazt.dao;

import org.springframework.data.repository.CrudRepository;

import com.project_management.belfazt.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);
	
	//User findById(Long Id);
	User getById(Long id);
}
