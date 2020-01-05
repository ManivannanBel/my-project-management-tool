package com.project_management.belfazt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project_management.belfazt.model.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>{
	
}
