package com.bugtracker.db.boards.tasks;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bugtracker.db.boards.Board;
import com.kriscfoster.school.teacher.Teacher;

@Table(name="board_tasks")
@Entity
public class Task {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "date_created")
    private String dateCreated;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bid", referencedColumnName = "id")
    private Board board;
    
    public Task(){
    	super();
    }

	public Integer getTaskId() {
		return taskId;
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
