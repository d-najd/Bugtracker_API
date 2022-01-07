package com.bugtracker.db.btj;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name="boards_tasks_join")
@Entity
public class BoardTaskJoin {
	
    @EmbeddedId
    private BtjIdentity btjIdentity;
    
	public BoardTaskJoin() {
		super();
	}

	public BtjIdentity getBtjIdentity() {
		return btjIdentity;
	}
}
