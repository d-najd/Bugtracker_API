package com.bugtracker.db.roadmaps;

import java.sql.Date;

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
    @Column(name = "field_id")
    private Integer fieldId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "due_date")
    private Date dueDate;
    @Column(name = "date_created")
    private Date dateCreated;

    public Roadmap() {
    	super();
    }

	public Roadmap(Integer field_id, Integer user_id, String title, String description, Date startDate,
			Date dueDate, Date dateCreated) {
		super();
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.dateCreated = dateCreated;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getField_id() {
		return fieldId;
	}

	public void setField_id(Integer field_id) {
		this.fieldId = field_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public void cleanData() {
	    fieldId = null;
	    userId = null;
	}
	
	public Roadmap returnCleanData() {
	    fieldId = null;
	    userId = null;
	    return this;
	}
}