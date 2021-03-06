package com.bugtracker.db.boards;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer>{
	public List<Board> findAllByProjectId(Integer projectId);
}
