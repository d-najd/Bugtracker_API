package com.bugtracker.project.boards;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.EmptyObj;
import com.bugtracker.QueryConstructor;
import com.bugtracker.db.boards.Board;
import com.bugtracker.db.boards.BoardRepository;
import com.bugtracker.db.boards.tasks.Task;
import com.bugtracker.db.boards.tasks.TaskRepository;
import com.kriscfoster.school.subject.Subject;
import com.kriscfoster.school.teacher.Teacher;

@RestController
@RequestMapping("/project/boards")
public class ProjectBoardsController {
	 	@Autowired
	    ProjectBoardsRepository projectBoardsRepository;
	   
		@Autowired
		TaskRepository taskRepository;
		
		@Autowired
		BoardRepository boardRepository;
	 	
	    @GetMapping("/all")
	    public List<ProjectBoards> getAllBoards(){
	    	return projectBoardsRepository.findAll();
	    }
	    
	    
	    /*
	    @GetMapping("/{projectId}")
	    public List<Project_Roadmaps> getAllRoadmapsByProjectId(){
	    	
	    }
	    */
}
