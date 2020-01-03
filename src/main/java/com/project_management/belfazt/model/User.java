package com.project_management.belfazt.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email(message = "Username should be a valid email")
	@NotBlank(message = "username is required")
	@Column(unique = true)
	private String username;
	@NotBlank(message = "please enter your full name")
	private String fullname;
	@NotBlank(message = "password field is required")
	private String password;
	@Transient
	private String confirmPassword;
	private Date created_at;
	private Date updated_at;
	private Date last_login;
	
	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	//OneToMany with project
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "user")
	private Set<Project> projects = new HashSet<Project>();
	
	//OneToMany with team projects
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "teamLeader")
	private Set<TeamProject> teamProjects = new HashSet<TeamProject>();
	
	//OneToMany with teams
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "teamMember")
	private Set<Team> teams = new HashSet<Team>();


	public User() {
	}
	
	@PrePersist
	protected void onCreate() {
		 this.created_at = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_at = new Date();
	}
	

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Set<TeamProject> getTeamProjects() {
		return teamProjects;
	}

	public void setTeamProjects(Set<TeamProject> teamProjects) {
		this.teamProjects = teamProjects;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	/*
	UserDetails implement methods
	*/
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
