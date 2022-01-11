package com.bugtracker.db.boards.tasks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.db.boards.Board;
import com.bugtracker.db.boards.BoardRepository;
import com.bugtracker.db.btj.BTJRepository;
import com.bugtracker.db.roles.RolesRepository;
import com.bugtracker.db.roles.Roles_Global;
import com.bugtracker.db.user.MyUserDetails;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	 	@Autowired
	    TaskRepository taskRepository;
	   
		@Autowired
		BoardRepository boardRepository;
		
	 	@Autowired
	    BTJRepository btjRepository;
	 	
		@Autowired
		RolesRepository rolesRepository;
	 	
	    @GetMapping("/all")
	    public List<Task> getAllTasks() {
	        return taskRepository.findAll();
	    }
	    
	   	@PutMapping
	    public ResponseEntity<String> editTask(
	    		@AuthenticationPrincipal MyUserDetails userDetails,
	    		@RequestBody Task task){
	    	//TODO CHECK WHAT HAPPENS IF THE TASK DOESN'T EXIST IN ANY PROJECT
	    	//AND ADD THE REST OF THE LOGIC
	    	
	    	try {
		    	Integer boardId = btjRepository.findOneByBtjIdentityTaskId(
		    			task.getId()).getBtjIdentity().getBoardId();
		    	Integer projectId = boardRepository.findById(boardId).get().getProjectId();
		    	
		    	if (!Roles_Global.hasAuthorities(userDetails, projectId, 
		    			new SimpleGrantedAuthority(Roles_Global.a_edit), rolesRepository))
		    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
	    	} catch (EntityNotFoundException e) {
	    		return new ResponseEntity<String>("trying to edit a task that doesn't exist?", HttpStatus.I_AM_A_TEAPOT);
			}
	    	taskRepository.save(task);
	    	return ResponseEntity.ok("ok");
	    }
	    
	    @PostMapping("/board/{boardId}")
	    public ResponseEntity<String> createAndSetTaskToBoard(
	    		@AuthenticationPrincipal MyUserDetails userDetails,
	    		@RequestBody Task task,
	            @PathVariable Integer boardId
	    ) {
	    	Board board;
	    	try {
	    		board = boardRepository.getById(boardId);
		    	if (!Roles_Global.hasAuthorities(userDetails, board.getProjectId(), 
		    			new SimpleGrantedAuthority(Roles_Global.a_create), rolesRepository))
		    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
	    	} catch (EntityNotFoundException e) {
	    		return new ResponseEntity<String>("trying to create a task in board that doens't exist?", HttpStatus.I_AM_A_TEAPOT);
			}
	    		
	        task = taskRepository.save(task);
	        board.addTask(task);
	        return ResponseEntity.ok("ok");
	    }
	    
	    
	    @ResponseBody
	    @GetMapping("/{id}")
	    public Optional<Task> getTaskById(@PathVariable Integer id) {
	    	return taskRepository.findById(id);
	    }
	    
	    
	    @PostMapping
	    //public Task addTask(@RequestBody Task task)
	    public String addTask(){
	    	return ("this function is decapitated, please use /tasks/board/{boardId} \n"
	    			+ "for creating and setting task to board");
	    	//return taskRepository.save(task);
	    }
	    
	    @DeleteMapping("/{id}")
	    public String removeTask(@PathVariable Integer id) {
	    	return ("this function is decapitated, please use /tasks/board/{boardId} \n"
	    			+ "for creating and setting task to board");
	    }
	    
	    
	    
}
