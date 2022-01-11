package com.bugtracker.db.boards.tasks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
	 	
	    @GetMapping("/all")
	    public List<Task> getAllTasks() {
	        return taskRepository.findAll();
	    }

	    //NOTE everyone will be able to update whichever field they choose, needs to check if the
	    //userid is authenticated and if he is updating his field id and not the field id of someone else
	    @ResponseBody
	    @PutMapping
	    public Task editTask(
	    		@AuthenticationPrincipal MyUserDetails userDetails,
	    		@RequestBody Task task){
	    	
	    	//TODO CHECK WHAT HAPPENS IF THE TASK DOESN'T EXIST IN ANY PROJECT
	    	//AND ADD THE REST OF THE LOGIC
	    	
	    	
	    	Integer boardId = btjRepository.findOneByBtjIdentityTaskId(
	    			task.getId()).getBtjIdentity().getBoardId();
	    	
	    	return null;
	    	
	    	/*
			if (!Roles_Global.hasAuthorities(userDetails, project.getId(),
					new SimpleGrantedAuthority(Roles_Global.a_manage_project), rolesRepository))
				return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
	    	
	    	
	    	
	    	
	    	return taskRepository.save(task);
	    	*/
	    }
	    
	    @ResponseBody
	    @PostMapping
	    //public Task addTask(@RequestBody Task task)
	    public String addTask(){
	    	return ("this function is decapitated, please use /tasks/board/{boardId} \n"
	    			+ "for creating and setting task to board");
	    	
	    	//return taskRepository.save(task);
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> removeTask(@PathVariable Integer id) {
	    	taskRepository.deleteById(id);
	    	return ResponseEntity.ok("ok");
	    }

	    @ResponseBody
	    @GetMapping("/{id}")
	    public Optional<Task> getTaskById(@PathVariable Integer id) {
	    	return taskRepository.findById(id);
	    }
	    
	    @PostMapping("/board/{boardId}")
	    Board createAndSetTaskToBoard(
	    		@RequestBody Task task,
	            @PathVariable Integer boardId
	    ) {
	    	boolean crash = false;
	    	Board board = null;
	    	try {
	    		board = boardRepository.findById(boardId).get();
	    	} catch (NoSuchElementException e){
	    		System.out.print("\n\nWARNING, some moron is trying to save a task inside a nonexisting board");
	    		crash = true;
	    	}
	    	if (crash == true) 
	    		return null;
	    		
	        task = taskRepository.save(task);
	        board.addTask(task);
	        return boardRepository.save(board);
	    }
}
