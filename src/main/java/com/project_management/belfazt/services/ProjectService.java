package com.project_management.belfazt.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project_management.belfazt.dao.BacklogRepository;
import com.project_management.belfazt.dao.ProjectRepository;
import com.project_management.belfazt.dao.TeamMemberRepository;
import com.project_management.belfazt.dao.TeamProjectRepository;
import com.project_management.belfazt.dao.UserRepository;
import com.project_management.belfazt.exceptions.AccessRestrictedException;
import com.project_management.belfazt.exceptions.ProjectIdException;
import com.project_management.belfazt.exceptions.ProjectNotFoundException;
import com.project_management.belfazt.exceptions.UserIsAlreadyATeamMemberException;
import com.project_management.belfazt.exceptions.UserNotExistsException;
import com.project_management.belfazt.model.Backlog;
import com.project_management.belfazt.model.Project;
import com.project_management.belfazt.model.TeamMember;
import com.project_management.belfazt.model.TeamProject;
import com.project_management.belfazt.model.User;
import com.project_management.belfazt.payload.TeamMemberResponse;


@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private BacklogRepository backlogRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TeamMemberRepository teamMemberRepository;
	@Autowired
	private TeamProjectRepository teamProjectRepository;
	
	public Project saveOrUpdateProject(Project project, String username) {
		
		/*Issue while updating
		 *DETAIL, If you update the existing project it will be updated,
		 *but if you update a project of other user, actually their project is also updated or replaced by current user
		 *replace in the since we are assigning the current user and project leader through setUser() and setProjectLeader()
		 *in the below code
		 *
		 *SOLUTION,
		 *	if project.id != null	
		 *		get the project by projectIdentifier,
		 *		 if project exists then, check if projectLeader is same as current user
		 *			if same then update
		 *		 else
		 *			don't update
		 */
		//Issue fix
		if(project.getId() != null) {
			
			Project existingProject = findProjectByIdentifier(project.getProjectIdentifier(), username);
			
			//Check if leader of currently updating project and fetched project are equal
			if(existingProject != null && !(existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("The project that you are trying to update is not found");
			}else if(existingProject == null){
				throw new ProjectNotFoundException("The project with id '"+ project.getProjectIdentifier() +"' does'nt exists");
			}
			
		}
		
		try {
			
			User user = userRepository.findByUsername(username);
			
			project.setUser(user);
			
			project.setProjectLeader(user.getUsername());
			
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			//Create Backlog only while creating a project
			if(project.getId() == null) {
				//Create backlog table
				Backlog backlog = new Backlog();
				//Create relationship
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			//While updating the project
			if(project.getId() != null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			return projectRepository.save(project);			
		}catch (Exception e) {
			throw new ProjectIdException("Project ID '"+ project.getProjectIdentifier().toUpperCase() + "' already exists");
		}
	}
	
	public Project findProjectByIdentifier(String projectId, String username) {
		
		//Get the project from the repository
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		//Check if the project exists
		if(project == null) {
			throw new ProjectIdException("Project ID '"+ projectId.toUpperCase() + "' not found");
		}
		
		//To prevent getting projects from other users using id
		if(!project.getProjectLeader().equals(username) ) {
			
			//check if team member is accessing and grant access to team members
			User teamMemberUser = userRepository.findByUsername(username);
			TeamMember teamMember = teamMemberRepository.findByTeamMemberAndProject(teamMemberUser, project);
			
			if(teamMember != null)
				return teamMember.getProject();
			throw new ProjectNotFoundException("Project not found");
		}
		//System.err.println(project.getProjectName());
		return project;
	}
	
	public Iterable<Project> findAllProjects(String username){
		return projectRepository.findAllByProjectLeader(username);
	}
	
	public Iterable<Project> findAllTeamProjects(String username){
		User user = userRepository.findByUsername(username);
		Iterable<TeamMember> TeamInOthersProjects = teamMemberRepository.findByTeamMember(user);
		List<Project> teamProjects = new ArrayList<Project>();
		for(TeamMember tm : TeamInOthersProjects) {
			teamProjects.add(tm.getProject());
		}
		return teamProjects;
	}
	
	public void deleteProjectByIdentifier(String projectId, String username) {
		//Get the user's project by identifier
		Project project = findProjectByIdentifier(projectId.toUpperCase(), username);
		
		//Preventing team members from deleting the project, only team leader should delete
		
		if(project == null) {
			throw new ProjectIdException("Project ID not "+ projectId.toUpperCase() +" found");
		}
		
		if(!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("You dont have previlage to delete this project");
		}
		
		//Delete the project
		projectRepository.delete(project);
	}
	
	public void addTeamMember(String projectId, String teamMemberUsername, String username) {
		
		//Check if teamMate is existing user
		User teamMemberUser = userRepository.findByUsername(teamMemberUsername);
		
		if(teamMemberUser == null) {
			throw new UserNotExistsException("User " + teamMemberUsername + " does not exists");
		}
		
		//Get the respective project
		Project project = findProjectByIdentifier(projectId, username);
		//Team members should be added only be the Team Leader, So check if projectLeader and Active users are same
		if(!project.getProjectLeader().equals(username)) {
			throw new AccessRestrictedException("Your access is restricted to perform this action");
		}
		
		//Get Active User
		User user = userRepository.findByUsername(username);
		//Get TeamProject
		TeamProject teamProject = teamProjectRepository.findByTeamLeader(user);
		//If current project is not a team project, then add it to team projects
		if(teamProject == null) {
			teamProject = new TeamProject();
			//set relation
			teamProject.setProject(project);
			teamProject.setTeamLeader(user);
			project.setTeamProject(teamProject);
		}
		
		//Check if the selected user is already a team member
		if(teamMemberRepository.findByTeamMemberAndProject(teamMemberUser, project) != null) {
			throw new UserIsAlreadyATeamMemberException("The user '"+ teamMemberUsername +"' is already a team member in this project '"+projectId+"'");
		}
		
		//Create TeamMember
		TeamMember teamMember = new TeamMember();
		//Set relations
		teamMember.setProject(project);
		teamMember.setTeamMember(teamMemberUser);
		
		//Add this teamMember to this project
		Set<TeamMember> teamMembers = project.getTeamMembers();
		teamMembers.add(teamMember);
		
		//Set the team members
		project.setTeamMembers(teamMembers);
		
		//Save
		projectRepository.save(project);
	}
	
	public Iterable<TeamMemberResponse> getAllTeamMembers(String projectId, String username){
		
		Project project = findProjectByIdentifier(projectId, username);
		Iterable<TeamMember> teamMembers = teamMemberRepository.findByProject(project);
		
		TeamProject teamProject = teamProjectRepository.findByProject(project);
		
		if(teamProject == null) {
			return null;
		}
		
		ArrayList<TeamMemberResponse> teamMembersList = new ArrayList<TeamMemberResponse>();
		
		User teamLeader = teamProject.getTeamLeader();
		
		teamMembersList.add(new TeamMemberResponse(teamLeader.getUsername(), teamLeader.getFullname()));
		
		for(TeamMember teamMember : teamMembers) {
			User user = teamMember.getTeamMember();
			TeamMemberResponse teamMemberResponse = new TeamMemberResponse(user.getUsername(), user.getFullname());
			teamMembersList.add(teamMemberResponse);
		}
		
		return teamMembersList;
	}
}
