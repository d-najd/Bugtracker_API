package com.bugtracker.db.roadmaps;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapRepository extends JpaRepository<Roadmap, Integer> {

	public List<Roadmap> findAllByProjectId(Integer projectId);
	public List<Roadmap> findAllByProjectIdIn(List<Integer> projectIds);

	//public static final String FIND_PROJECTS = "SELECT * FROM roadmaps";
	
	//public Optional<Roadmap> findByIdAndUserId(Integer fieldId, Integer userId);
	 
	//@Query(value = FIND_PROJECTS, nativeQuery = true)
	//public List<Roadmap> findAllByUserId(Integer userId);
}
