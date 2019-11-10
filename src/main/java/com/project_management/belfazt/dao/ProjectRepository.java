package com.project_management.belfazt.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project_management.belfazt.model.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{

	Project findByProjectIdentifier(String projectIdentifier);
	
	Iterable<Project> findAllByProjectLeader(String username);
}
