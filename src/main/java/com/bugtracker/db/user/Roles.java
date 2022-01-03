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
    @Column(name = "manage_project")
    private boolean manageProject;
    @Column(name = "manage_users")
    private boolean manageUsers;
    @Column(name = "create")
    private boolean create;
    @Column(name = "edit")
    private boolean edit;
    @Column(name = "delete")
    private boolean delete;

	public Roles() {
		super();
	}
	
	public Roles(String userName) {
		super();
		this.username = userName;
	}

	public Roles(String username, boolean manageProject, boolean manageUsers, boolean create, boolean edit,
			boolean delete) {
		super();
		this.username = username;
		this.manageProject = manageProject;
		this.manageUsers = manageUsers;
		this.create = create;
		this.edit = edit;
		this.delete = delete;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isManageProject() {
		return manageProject;
	}

	public void setManageProject(boolean manageProject) {
		this.manageProject = manageProject;
	}

	public boolean isManageUsers() {
		return manageUsers;
	}

	public void setManageUsers(boolean manageUsers) {
		this.manageUsers = manageUsers;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	
	
    
}
