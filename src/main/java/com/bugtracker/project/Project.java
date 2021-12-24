package com.bugtracker.project;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.bugtracker.db.boards.tasks.Task;
import com.bugtracker.db.roadmaps.Roadmap;

@Table(name="project")
@Entity
public class Project {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	@Column(name = "title")
	private String title;
	
	public Project() {
		super();
	}
	
	public Project(Integer id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	
	
}
