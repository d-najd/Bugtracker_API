package com.bugtracker.db.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
	List<Project> findAllByIdIn(List<Integer> ids);
	Project findByProjectId(Integer projectId);
}
