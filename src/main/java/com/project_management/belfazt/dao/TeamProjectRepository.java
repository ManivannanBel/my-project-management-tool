package com.project_management.belfazt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project_management.belfazt.model.TeamProject;

@Repository
public interface TeamProjectRepository extends JpaRepository<TeamProject, Long>{
	
	
	
}
