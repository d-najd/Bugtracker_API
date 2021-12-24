package com.bugtracker.project.roadmaps;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRoadmapsRepository extends JpaRepository<ProjectRoadmaps, ProjectRoadmapsIdentity> {
	public List<ProjectRoadmaps> findAllByProjectRoadmapsIdentityProjectId(Integer projectId);
}