package com.bugtracker.project.roadmaps;

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
@RequestMapping("/project/roadmaps")
public class Project_RoadmapsController {
		public static final String dbLocation = "jdbc:mysql://localhost:3306/bugtracker_db?useSSL=false";
		public static final String dbUname = "root";
		public static final String dbPass = "j6t2gu6k46ek";
		
	 	@Autowired
	    Project_RoadmapsRepository project_RoadmapsRepository;
	   
		@Autowired
		TaskRepository taskRepository;
		
		@Autowired
		BoardRepository boardRepository;
	 	
	    @GetMapping("/all")
	    public List<Project_Roadmaps> getAllRoadmaps(){
	    	return project_RoadmapsRepository.findAll();
	    	
	    }
	    
	    
	    /*
	    @GetMapping("/{projectId}")
	    public List<Project_Roadmaps> getAllRoadmapsByProjectId(){
	    	
	    }
	    */
}
