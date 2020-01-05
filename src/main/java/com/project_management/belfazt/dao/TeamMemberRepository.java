package com.project_management.belfazt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project_management.belfazt.model.Project;
import com.project_management.belfazt.model.TeamMember;
import com.project_management.belfazt.model.User;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>{
	
	Iterable<TeamMember> findByTeamMember(User member);
	
	Iterable<TeamMember> findByProject(Project project);
	
	TeamMember findByTeamMemberAndProject(User member, Project project);
	
}
