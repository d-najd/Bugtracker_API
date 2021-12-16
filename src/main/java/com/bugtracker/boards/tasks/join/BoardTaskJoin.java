package com.bugtracker.boards.tasks.join;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bugtracker.db.boards.Board;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kriscfoster.school.teacher.Teacher;

@Table(name="boards_tasks_join")
@Entity
public class BoardTaskJoin {
	
    @EmbeddedId
    private BTJIdentity btjIdentity;
    
	public BoardTaskJoin() {
		super();
	}

	public BTJIdentity getBtjIdentity() {
		return btjIdentity;
	}
}
