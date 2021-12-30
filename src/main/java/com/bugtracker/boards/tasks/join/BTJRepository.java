package com.bugtracker.boards.tasks.join;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bugtracker.project.boards.ProjectBoards;

public interface BTJRepository extends JpaRepository<BoardTaskJoin, BtjIdentity> {
	public BoardTaskJoin findOneByBtjIdentityTaskId(Integer taskId);
	
	public List<BoardTaskJoin> findAllByBtjIdentityBoardId(Integer boardId);
}