package com.bugtracker.db.boards;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bugtracker.db.boards.tasks.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name="boards")
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "project_id")
    private Integer projectId;
    @Column(name = "position")
    private Integer position;
    @Column(name = "title")
    private String title;
    
    @ManyToMany
    @JoinTable(
            name = "boards_tasks_join",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    Set<Task> tasks = new HashSet<>();
        
	public Board() {
		super();
	}
	
	public Board(Integer id, Integer projectId, Integer position, String title, Set<Task> tasks) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.position = position;
		this.title = title;
		this.tasks = tasks;
	}

	public Integer getId() {
		return id;
	}
	
	public Integer getPosition() {
		return position;
	}

	public String getTitle() {
		return title;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	

	public Integer getProjectId() {
		return projectId;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public void addTask(Task task) {
		tasks.add(task);
	}
    
}
