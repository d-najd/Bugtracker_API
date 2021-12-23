package com.bugtracker.project.roadmaps;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name="project_roadmaps")
@Entity
public class Project_Roadmaps {
	
    @EmbeddedId
    private Project_RoadmapsIdentity project_RoadmapsIdentity;

	public Project_Roadmaps() {
		super();
	}

	public Project_Roadmaps(Project_RoadmapsIdentity project_RoadmapsIdentity) {
		super();
		this.project_RoadmapsIdentity = project_RoadmapsIdentity;
	}

	public Project_RoadmapsIdentity getProject_RoadmapsIdentity() {
		return project_RoadmapsIdentity;
	}

	public void setProject_RoadmapsIdentity(Project_RoadmapsIdentity project_RoadmapsIdentity) {
		this.project_RoadmapsIdentity = project_RoadmapsIdentity;
	}
}
