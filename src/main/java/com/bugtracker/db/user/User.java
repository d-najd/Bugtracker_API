package com.bugtracker.db.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.bugtracker.db.roles.Roles;

import java.util.ArrayList;
import java.util.List;

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
    private Boolean active;
    
    //@ManyToOne
    //private Roles roles;

	public User() {
		super();
	}
	public User(String username, String password, Boolean active) {
		super();
		this.username = username;
		this.password = password;
		this.active = active;
	}
	
	public User(String username) {
		super();
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	/*
	public List<GrantedAuthority> _getAuthorities() {
		return null;
		/* TODO fix the logic behind authorities to enable this
		List<GrantedAuthority> authorities = roles._getAuthorities();
		if (username.equals("admin"))
			authorities.add(new SimpleGrantedAuthority("ROLE_owner"));
		return authorities;
		
	}
	*/
	
}
