package com.bugtracker.db.boards.tasks;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	//public static final String FIND_PROJECTS = "SELECT * FROM roadmaps";
	
	public Optional<Task> findByIdAndBoardId(Integer boardId, Integer userId);
	 
	//@Query(value = FIND_PROJECTS, nativeQuery = true)
	public List<Task> findAllByBoardId(Integer boardId);
}