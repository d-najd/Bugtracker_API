package com.bugtracker.db.project;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.bugtracker.db.boards.tasks.Task;
import com.bugtracker.db.roadmaps.Roadmap;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name="project")
@Entity
public class Project {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	@Column(name = "title")
	private String title;
	@JsonIgnore
	@Column(name = "owner_id")
	private String ownerId;
	
	public Project() {
		super();
	}
	
	public Project(Integer id, String title, String ownerId) {
		super();
		this.id = id;
		this.title = title;
		this.ownerId = ownerId;
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	
	
}
