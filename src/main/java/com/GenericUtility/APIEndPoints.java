package com.GenericUtility;

public interface APIEndPoints {
	
	String createProject = "/addProject";
	String updateProject = "/projects/{projectId}";
	String getAllProjects = "/projects/{projectId}";
	String getSingleProject = "/projects/{projectId}";
	String deleteProject = "/projects/{projectId}";
	
}
