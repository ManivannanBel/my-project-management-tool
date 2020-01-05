package com.project_management.belfazt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project_management.belfazt.model.Project;
import com.project_management.belfazt.model.TeamProject;
import com.project_management.belfazt.model.User;

@Repository
public interface TeamProjectRepository extends JpaRepository<TeamProject, Long>{
	
	TeamProject findByTeamLeader(User user);
	
	TeamProject findByProject(Project project);
}
