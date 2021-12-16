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
import com.kriscfoster.school.subject.Subject;

@Table(name="boards")
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
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

	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getTitle() {
		return title;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public void addTask(Task task) {
		tasks.add(task);
	}
    
}
