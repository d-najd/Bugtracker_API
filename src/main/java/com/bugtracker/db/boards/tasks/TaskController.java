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
import com.bugtracker.db.project.ProjectRepository;
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
		
		@Autowired
		ProjectRepository projectRepository;
	 	
	    @GetMapping("/all")
	    public List<Task> getAllTasks() {
	        return taskRepository.findAll();
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> removeTask(
	    		@AuthenticationPrincipal MyUserDetails userDetails,
	    		@PathVariable Integer id) {
	    	try {
	    		Integer bid = btjRepository.findOneByBtjIdentityTaskId(
		    			id).getBtjIdentity().getBoardId();
		    	Integer projectId = boardRepository.getById(bid).getProjectId();
		    	
		    	if (!Roles_Global.hasAuthorities(userDetails, projectId, 
		    			new SimpleGrantedAuthority(Roles_Global.a_delete), rolesRepository, projectRepository))
		    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
	    	} catch (EntityNotFoundException | NullPointerException e) {
	    		return new ResponseEntity<String>("trying to remove task that doesn't exist?", HttpStatus.I_AM_A_TEAPOT);
	    	}
	    	
	    	
	    	taskRepository.deleteById(id);
	    	return ResponseEntity.ok("ok");
	    }

	    @ResponseBody
	    @GetMapping("/{id}")
	    public Optional<Task> getTaskById(@PathVariable Integer id) {
	    	return taskRepository.findById(id);
	    }
	    
	    @PostMapping("/boards/{bid}")
	    public ResponseEntity<String> createAndSetTaskToBoard(
	    		@AuthenticationPrincipal MyUserDetails userDetails,
	    		@RequestBody Task task,
	    		@PathVariable Integer bid
	    ) {
	    	Board board;
	    	try {
	    		board = boardRepository.getById(bid);
	    		
		    	if (!Roles_Global.hasAuthorities(userDetails, board.getProjectId(), 
		    			new SimpleGrantedAuthority(Roles_Global.a_create), rolesRepository, projectRepository))
		    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
	    	} catch (EntityNotFoundException | NullPointerException e) {
	    		return new ResponseEntity<String>("trying to create task in nonexistant board?", HttpStatus.I_AM_A_TEAPOT);
	    	}
	    	
	        task = taskRepository.save(task);
	        board.addTask(task);
	        boardRepository.save(board);
	        return ResponseEntity.ok("ok");
	    }
}
