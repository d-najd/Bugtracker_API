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
	
	public Project() {
		super();
	}
	
	public Project(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
