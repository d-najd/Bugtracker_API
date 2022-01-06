package com.bugtracker.db.roadmaps;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="roadmaps")
@Entity
public class Roadmap {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "project_id")
    private Integer projectId;
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

	public Roadmap(Integer id, Integer projectId, String title, String description, String startDate, String dueDate,
			String dateCreated) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.dateCreated = dateCreated;
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
	public Integer getProjectId() {
		return projectId;
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