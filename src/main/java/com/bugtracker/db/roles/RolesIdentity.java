package com.bugtracker.db.roles;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.lang.NonNull;

import com.bugtracker.db.btj.BtjIdentity;

@Embeddable
public class RolesIdentity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NonNull
	@Column(name = "username")
    private String username;
	@NonNull
	@Column(name = "project_id")
    private Integer projectId;
	
	public RolesIdentity() {
		super();
	}
	public RolesIdentity(String username, Integer projectId) {
		super();
		this.username = username;
		this.projectId = projectId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolesIdentity that = (RolesIdentity) o;
        if (!username.equals(that.username)) 
        	return false;
        return projectId.equals(that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, projectId);
    }
}
