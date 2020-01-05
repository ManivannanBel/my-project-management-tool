package com.project_management.belfazt.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TeamMember {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "team_member", updatable = false, nullable = false)
	@JsonIgnore
	private User teamMember;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id", updatable = false, nullable = false)
	private Project project;
	
	public User getTeamMember() {
		return teamMember;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public void setTeamMember(User teamMember) {
		this.teamMember = teamMember;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
