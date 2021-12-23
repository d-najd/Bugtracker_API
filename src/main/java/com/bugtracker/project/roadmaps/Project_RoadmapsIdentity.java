package com.bugtracker.project.roadmaps;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.lang.NonNull;

@Embeddable
public class Project_RoadmapsIdentity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NonNull
	@Column(name = "project_id")
    private Integer projectId;
	@NonNull
	@Column(name = "roadmap_id")
    private Integer roadmapId;

    public Project_RoadmapsIdentity() {

    }

	public Project_RoadmapsIdentity(Integer projectId, Integer roadmapId) {
		super();
		this.projectId = projectId;
		this.roadmapId = roadmapId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public Integer getRoadmapId() {
		return roadmapId;
	}

	public void setRoadmapId(Integer roadmapId) {
		this.roadmapId = roadmapId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project_RoadmapsIdentity that = (Project_RoadmapsIdentity) o;

        if (!projectId.equals(that.projectId)) 
        	return false;
        return roadmapId.equals(that.roadmapId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, roadmapId);
    }
}