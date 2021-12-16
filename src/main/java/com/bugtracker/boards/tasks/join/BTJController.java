package com.bugtracker.boards.tasks.join;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.bugtracker.db.boards.tasks.Task;
import com.bugtracker.db.boards.tasks.TaskRepository;
import com.kriscfoster.school.subject.Subject;
import com.kriscfoster.school.teacher.Teacher;

@RestController
@RequestMapping("/btj")
public class BTJController {
	 	@Autowired
	    BTJRepository BTJRepository;
	   
		@Autowired
		TaskRepository taskRepository;
	 	
	    @GetMapping("/all")
	    public List<BoardTaskJoin> getAllBTJ(){
	    	return BTJRepository.findAll();
	    }
	    
	    @GetMapping()
	    public Optional<BoardTaskJoin> getBTJ() {
	    	return BTJRepository.findById(new BTJIdentity(24, 6));
	    }
	    
	    //TODO add a case where if the task isn't in any other project
	    //to be removed from the server
	    @DeleteMapping("/board/{bid}/task/{tid}")
	    public BoardTaskJoin removeBTJ(
	    		@PathVariable("bid") Integer bid,
	    		@PathVariable("tid") Integer tid) {
	    	BTJRepository.deleteById(new BTJIdentity(bid, tid));
	    	taskRepository.deleteById(tid);
	    	return new BoardTaskJoin();
	    }
}
