package com.bugtracker.db.boards.tasks;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepositoryCustom {
    
	
	@Modifying
	@Query(value = "alter table bugtracker_db.board_tasks add column test int(1) not null default 0", 
	  nativeQuery = true)
	public void customMethod();
}