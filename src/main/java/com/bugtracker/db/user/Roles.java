package com.bugtracker.db.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "ROLE_manage_project")
    private Boolean manageProject;
    @Column(name = "ROLE_manage_users")
    private Boolean manageUsers;
    @Column(name = "ROLE_create")
    private Boolean create;
    @Column(name = "ROLE_edit")
    private Boolean edit;
    @Column(name = "ROLE_delete")
    private Boolean delete;
    
	public Roles() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public Boolean getManageProject() {
		return manageProject;
	}
	public Boolean getManageUsers() {
		return manageUsers;
	}
	public Boolean getCreate() {
		return create;
	}
	public Boolean getEdit() {
		return edit;
	}
	public Boolean getDelete() {
		return delete;
	}
}
