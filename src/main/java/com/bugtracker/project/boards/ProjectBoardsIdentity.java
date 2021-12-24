package com.bugtracker.project.boards;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.lang.NonNull;

@Embeddable
public class ProjectBoardsIdentity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NonNull
	@Column(name = "project_id")
    private Integer projectId;
	@NonNull
	@Column(name = "board_id")
    private Integer boardId;

    public ProjectBoardsIdentity() {

    }

	public ProjectBoardsIdentity(Integer projectId, Integer boardId) {
		super();
		this.projectId = projectId;
		this.boardId = boardId;
	}

	public Integer getProjectId() {
		return projectId;
	}
	
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getBoardId() {
		return boardId;
	}

	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectBoardsIdentity that = (ProjectBoardsIdentity) o;

        if (!projectId.equals(that.projectId)) 
        	return false;
        return boardId.equals(that.boardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, boardId);
    }
}