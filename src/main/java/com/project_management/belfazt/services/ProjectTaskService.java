package com.project_management.belfazt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project_management.belfazt.dao.BacklogRepository;
import com.project_management.belfazt.dao.ProjectRepository;
import com.project_management.belfazt.dao.ProjectTaskRepository;
import com.project_management.belfazt.exceptions.ProjectNotFoundException;
import com.project_management.belfazt.model.Backlog;
import com.project_management.belfazt.model.ProjectTask;

@Service
public class ProjectTaskService {

		@Autowired
		private BacklogRepository backlogRepository;
		
		@Autowired
		private ProjectTaskRepository projectTaskRepository;
		
		@Autowired
		private ProjectRepository projectRepository;
		
		@Autowired
		private ProjectService projectService;
		
		public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

			//PT to be added to a specific project, project != null, Backlog exists
			// X---XBacklog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			//Use project service to get the backlog, because you don't have to check correct user access again in this code
			//Project project = projectService.findProjectByIdentifier(projectIdentifier, username);
			Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
			try {
				
				//Set Created_By column, it may be created by leader or members
				projectTask.setCreatedBy(username);
				
				projectIdentifier = projectIdentifier.toUpperCase();
				
				//set backlog to projectTask
				projectTask.setBacklog(backlog);
				//Get the project sequence from backlog
				Integer backlogSequence = backlog.getPTSequence();
				//update it
				backlogSequence++;
				//set it back to backlog
				backlog.setPTSequence(backlogSequence);
				//////backlogRepository.save(backlog);
				//add backlog sequence to the project task
				projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
				//Add Project identifier to project task
				projectTask.setProjectIdentifier(projectIdentifier);
	
				//Initial priority when priority null
				if( projectTask.getPriority() == null || projectTask.getPriority() == 0) {
					projectTask.setPriority(5);
				}
				
				//Initial status when status null
				if(projectTask.getStatus() == null || projectTask.getStatus().equals("")) {
					projectTask.setStatus("TO_DO");
				}
				
				return projectTaskRepository.save(projectTask);
			}catch (Exception e) {
				throw new ProjectNotFoundException("Project not found");
			}
		}
		
		public Iterable<ProjectTask> findBacklogById(String projectIdentifier, String username){
			
			//Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
			
			projectService.findProjectByIdentifier(projectIdentifier.toUpperCase(), username);
			
			//No need to check for project null, because of projectService where it is already checked
			/*if(project == null) {
				throw new ProjectNotFoundException("Project with ID: '"+projectIdentifier+"' does not exists");
			}*/
			
			return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier.toUpperCase());
		}
		
		public ProjectTask findProjectTaskByProjectSequence(String backlogId, String projectSequence, String username) {
			
			//make sure PT is searched on existing backlog
			//X--XBacklog backlog = backlogRepository.findByProjectIdentifier(backlogId.toUpperCase());
			projectService.findProjectByIdentifier(backlogId.toUpperCase(), username);
			//Everything is prechecked in above project service
			/*if(backlog == null) {
				throw new ProjectNotFoundException("Project with ID: '"+backlogId.toUpperCase()+"' does not exists");
			}*/
			
			//make sure the task exists
			ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectSequence.toUpperCase());
			if(projectTask == null) {
				throw new ProjectNotFoundException("Project task  '"+projectSequence.toUpperCase()+"'  not found");
			}
			
			//make sure PT searched on the correct backlog
			if(!projectTask.getProjectIdentifier().equals(backlogId.toUpperCase())) {
				throw new ProjectNotFoundException("Project task  '"+projectSequence.toUpperCase()+"'  not exist in the project "+backlogId.toUpperCase());
			}
			
			return projectTaskRepository.findByProjectSequence(projectSequence.toUpperCase());
		}
		
		public ProjectTask updateProjectTaskByProjectSequence(ProjectTask updatedTask, String projectIdentifier, String projectSequence, String username) {
			
			ProjectTask projectTask = findProjectTaskByProjectSequence(projectIdentifier.toUpperCase(), projectSequence.toUpperCase(), username);
			
			projectTask = updatedTask;
			
			return projectTaskRepository.save(projectTask);
		}
		
		public void deleteProjectTaskByProjectSequence(String projectIdentifier, String projectSequence, String username) {
			
			ProjectTask projectTask = findProjectTaskByProjectSequence(projectIdentifier.toUpperCase(), projectSequence.toUpperCase(), username);
			
			projectTaskRepository.delete(projectTask);
			
		}

}
