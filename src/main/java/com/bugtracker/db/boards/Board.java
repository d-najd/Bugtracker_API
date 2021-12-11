package com.bugtracker.db.boards;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
    
    public Board() {
    	super();
    }
    
	public Board(Integer fieldId, Integer userId, String title) {
		super();
		this.id = fieldId;
		this.userId = userId;
		this.title = title;
	}
	
	public Integer getFieldId() {
		return id;
	}
	public void setFieldId(Integer fieldId) {
		this.id = fieldId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
    
    
}
