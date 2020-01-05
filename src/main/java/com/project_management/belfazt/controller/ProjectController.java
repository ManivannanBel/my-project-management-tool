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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project_management.belfazt.model.Project;
import com.project_management.belfazt.services.ProjectService;
import com.project_management.belfazt.services.ValidationErrorService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ValidationErrorService validationErrorService;
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal){
		
		ResponseEntity<?> errorMap = validationErrorService.validateError(result);
		if(errorMap != null) return errorMap;
		
		project = projectService.saveOrUpdateProject(project, principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal){
		
		Project project = projectService.findProjectByIdentifier(projectId, principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.OK);
		
	}
	
	@GetMapping("")
	public Iterable<Project> getAllProjects(Principal principal){
		return projectService.findAllProjects(principal.getName());
	}
	
	@GetMapping("/teamProjects")
	public Iterable<Project> getAllTeamProjects(Principal principal){
		//System.out.println("\n\ncontroller\n\n");
		return projectService.findAllTeamProjects(principal.getName());
	}
	
	@DeleteMapping("{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal){
		projectService.deleteProjectByIdentifier(projectId, principal.getName());
		
		return new ResponseEntity<String>("Project with ID " + projectId + " was deleted", HttpStatus.OK);
	}

}
