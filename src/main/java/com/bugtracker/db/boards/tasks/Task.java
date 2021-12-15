package com.bugtracker.db.boards.tasks;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@Table(name="board_tasks")
@Entity
public class Task {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "date_created")
    private String dateCreated;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "tasks")
    private Set<Board> boards = new HashSet<>();
    
    public Task(){
    	super();
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

	public Set<Board> getBoards() {
		return boards;
	}

	public void setBoards(Set<Board> boards) {
		this.boards = boards;
	}
}
