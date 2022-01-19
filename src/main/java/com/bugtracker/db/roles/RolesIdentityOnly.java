package com.bugtracker.db.roles;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import org.springframework.lang.NonNull;

public class RolesIdentityOnly {
	@NonNull
	@Column(name = "username")
	private String username;
	@NonNull
	@Column(name = "project_id")
	private Integer projectId;

	public RolesIdentityOnly() {
		super();
	}

	public RolesIdentityOnly(String username, Integer projectId) {
		super();
		this.username = username;
		this.projectId = projectId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
}
