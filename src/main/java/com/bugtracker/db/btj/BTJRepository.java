package com.bugtracker.db.btj;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BTJRepository extends JpaRepository<BoardTaskJoin, BtjIdentity> {
	public BoardTaskJoin findOneByBtjIdentityTaskId(Integer taskId);
	
	public List<BoardTaskJoin> findAllByBtjIdentityBoardId(Integer boardId);
}