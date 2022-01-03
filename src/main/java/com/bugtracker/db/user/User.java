package com.bugtracker.db.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "active")
    private boolean active;
    
    @ManyToOne
    private Roles roles;

	public User() {
		super();
	}
	public User(String username, String password, Roles roles, boolean active) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.active = active;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public boolean isActive() {
		return active;
	}
	public Roles getRoles() {
		return roles;
	}
	public void setRoles(Roles roles) {
		this.roles = roles;
	}
}
