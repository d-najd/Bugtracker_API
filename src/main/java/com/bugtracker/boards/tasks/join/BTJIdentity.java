package com.bugtracker.boards.tasks.join;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.lang.NonNull;

@Embeddable
public class BTJIdentity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NonNull
	@Column(name = "board_id")
    private Integer boardId;
	@NonNull
	@Column(name = "task_id")
    private Integer taskId;

    public BTJIdentity() {

    }

    public BTJIdentity(Integer boardId, Integer taskId) {
        this.boardId = boardId;
        this.taskId = taskId;
    }

	public Integer getBoardId() {
		return boardId;
	}

	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BTJIdentity that = (BTJIdentity) o;

        if (!boardId.equals(that.boardId)) 
        	return false;
        return taskId.equals(that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, taskId);
    }
}