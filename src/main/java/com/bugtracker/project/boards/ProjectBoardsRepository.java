package com.bugtracker.project.boards;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectBoardsRepository extends JpaRepository<ProjectBoards, ProjectBoardsIdentity> {
	public List<ProjectBoards> findAllByProjectBoardsIdentityProjectId(Integer projectId);
}