package com.bugtracker.db.roadmaps;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bugtracker.db.User;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

public interface RoadmapRepository extends JpaRepository<Roadmap, Integer> {

	public Optional<Roadmap> findByFieldId(Integer fieldid);
	
	public Optional<Roadmap> findByFieldIdAndUserId(Integer fieldId, Integer userId);

	public List<Roadmap> findAllByUserId(Integer userId);
}
