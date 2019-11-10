package com.project_management.belfazt.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project_management.belfazt.model.ProjectTask;
import com.project_management.belfazt.services.ProjectTaskService;
import com.project_management.belfazt.services.ValidationErrorService;

@RestController
@RequestMapping("api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private ValidationErrorService validationErrorService;
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, Principal principal){
		
		ResponseEntity<?> errorMap = validationErrorService.validateError(result);
		if(errorMap != null) return errorMap;
		
		ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());
		
		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal){
		
		return projectTaskService.findBacklogById(backlog_id, principal.getName());
		
	}
	
	@GetMapping("/{backlog_id}/{project_task_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String project_task_id, Principal principal){
		
		ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlog_id, project_task_id, principal.getName());
		
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}
	
	@PatchMapping("/{backlog_id}/{project_task_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,  BindingResult result, @PathVariable String backlog_id, @PathVariable String project_task_id, Principal principal){
		
		ResponseEntity<?> errorMap = validationErrorService.validateError(result);
		if(errorMap != null) return errorMap;
		
		ProjectTask updatedProjectTask = projectTaskService.updateProjectTaskByProjectSequence(projectTask, backlog_id, project_task_id, principal.getName());
		
		return new ResponseEntity<ProjectTask>(updatedProjectTask, HttpStatus.OK);
	}
	
	@DeleteMapping("/{backlog_id}/{project_task_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String project_task_id, Principal principal){
		
		projectTaskService.deleteProjectTaskByProjectSequence(backlog_id, project_task_id, principal.getName());
		
		return new ResponseEntity<String>("Project task "+ project_task_id +" was deleted successfully", HttpStatus.OK);
		
	}
}













