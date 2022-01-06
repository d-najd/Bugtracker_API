package com.bugtracker.db.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.apache.tomcat.jni.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
    
    @ManyToOne
    private Roles roles;

	public User() {
		super();
	}
	public User(String username, String password, Roles roles, Boolean active) {
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
	
	public Boolean getActive() {
		return active;
	}
	public Roles getRoles() {
		return roles;
	}
	public void setRoles(Roles roles) {
		this.roles = roles;
	}
	
	public List<GrantedAuthority> _getAuthorities() {
		List<GrantedAuthority> authorities = roles._getAuthorities();
		if (username.equals("admin"))
			authorities.add(new SimpleGrantedAuthority("ROLE_owner"));
		return authorities;
	}
}
