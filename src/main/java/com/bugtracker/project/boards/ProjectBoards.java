package com.bugtracker.project.boards;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name="project_boards")
@Entity
public class ProjectBoards {
	
    @EmbeddedId
    private ProjectBoardsIdentity projectBoardsIdentity;

	public ProjectBoards() {
		super();
	}

	public ProjectBoards(ProjectBoardsIdentity projectBoardsIdentity) {
		super();
		this.projectBoardsIdentity = projectBoardsIdentity;
	}

	public ProjectBoardsIdentity getProjectBoardsIdentity() {
		return projectBoardsIdentity;
	}

	public void setProjectBoardsIdentity(ProjectBoardsIdentity projectBoardsIdentity) {
		this.projectBoardsIdentity = projectBoardsIdentity;
	}
}
