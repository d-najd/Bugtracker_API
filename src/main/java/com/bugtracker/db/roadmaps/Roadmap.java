package com.bugtracker.db.roadmaps;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.bugtracker.project.Project;
import com.bugtracker.project.roadmaps.Project_Roadmaps;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name="roadmaps")
@Entity
public class Roadmap {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "due_date")
    private String dueDate;
    @Column(name = "date_created")
    private String dateCreated;
    
	public Roadmap() {
		super();
	}

	public Roadmap(Integer id, Integer userId, String title, String description, String startDate, String dueDate,
			String dateCreated) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.dateCreated = dateCreated;
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
	public String getDescription() {
		return description;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public String getDateCreated() {
		return dateCreated;
	}
}