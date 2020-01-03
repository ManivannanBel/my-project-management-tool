package com.project_management.belfazt.payload;

import java.util.Date;
import java.util.Set;

import com.project_management.belfazt.model.Project;

public class UserProfileResponse {
	private String username;
	private String fullname;
	private Date created_at;
	private Date updated_at;
	private Date last_login;
	private Set<Project> projects;
	public UserProfileResponse(String username, String fullname, Date created_at, Date updated_at, Date last_login,
			Set<Project> projects) {
		super();
		this.username = username;
		this.fullname = fullname;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.last_login = last_login;
		this.projects = projects;
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
	public Date getLast_login() {
		return last_login;
	}
	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}
	public Set<Project> getProjects() {
		return projects;
	}
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	
}
