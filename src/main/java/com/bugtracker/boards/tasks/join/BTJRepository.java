package com.bugtracker.boards.tasks.join;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BTJRepository extends JpaRepository<BoardTaskJoin, BtjIdentity> {
	public BoardTaskJoin findOneByBtjIdentityTaskId(Integer taskId);
	
	public List<BoardTaskJoin> findAllByBtjIdentityBoardId(Integer boardId);
}