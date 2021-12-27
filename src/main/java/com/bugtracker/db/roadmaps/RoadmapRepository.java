package com.bugtracker.db.roadmaps;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bugtracker.db.users.User;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

public interface RoadmapRepository extends JpaRepository<Roadmap, Integer> {

	public List<Roadmap> findAllByProjectId(Integer projectId);
	//public static final String FIND_PROJECTS = "SELECT * FROM roadmaps";
	
	//public Optional<Roadmap> findByIdAndUserId(Integer fieldId, Integer userId);
	 
	//@Query(value = FIND_PROJECTS, nativeQuery = true)
	//public List<Roadmap> findAllByUserId(Integer userId);
}
