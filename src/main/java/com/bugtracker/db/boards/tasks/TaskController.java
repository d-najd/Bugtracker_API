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

import com.bugtracker.boards.tasks.join.BTJRepository;
import com.bugtracker.db.boards.Board;
import com.bugtracker.db.boards.BoardRepository;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	 	@Autowired
	    TaskRepository taskRepository;
	   
		@Autowired
		BoardRepository boardRepository;
	 	
	    @GetMapping("/all")
	    public List<Task> getAllTasks() {
	        return taskRepository.findAll();
	    }

	    //NOTE everyone will be able to update whichever field they choose, needs to check if the
	    //userid is authenticated and if he is updating his field id and not the field id of someone else
	    @ResponseBody
	    @PutMapping
	    public Task editTask(@RequestBody Task task){
	    	return taskRepository.save(task);
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
