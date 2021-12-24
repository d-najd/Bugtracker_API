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
public class ProjectRoadmaps {
	
    @EmbeddedId
    private ProjectRoadmapsIdentity projectRoadmapsIdentity;

	public ProjectRoadmaps() {
		super();
	}

	public ProjectRoadmaps(ProjectRoadmapsIdentity projectRoadmapsIdentity) {
		super();
		this.projectRoadmapsIdentity = projectRoadmapsIdentity;
	}

	public ProjectRoadmapsIdentity getProjectRoadmapsIdentity() {
		return projectRoadmapsIdentity;
	}

	public void setProjectRoadmapsIdentity(ProjectRoadmapsIdentity projectRoadmapsIdentity) {
		this.projectRoadmapsIdentity = projectRoadmapsIdentity;
	}

	
}
