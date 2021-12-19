package com.bugtracker.boards.tasks.join;

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

import com.bugtracker.QueryConstructor;
import com.bugtracker.db.boards.Board;
import com.bugtracker.db.boards.BoardRepository;
import com.bugtracker.db.boards.tasks.Task;
import com.bugtracker.db.boards.tasks.TaskRepository;
import com.bugtracker.db.roadmaps.EmptyObj;
import com.kriscfoster.school.subject.Subject;
import com.kriscfoster.school.teacher.Teacher;

@RestController
@RequestMapping("/btj")
public class BTJController {
		public static final String dbLocation = "jdbc:mysql://localhost:3306/bugtracker_db?useSSL=false";
		public static final String dbUname = "root";
		public static final String dbPass = "j6t2gu6k46ek";
		
	 	@Autowired
	    BTJRepository BTJRepository;
	   
		@Autowired
		TaskRepository taskRepository;
		
		@Autowired
		BoardRepository boardRepository;
	 	
	    @GetMapping("/all")
	    public List<BoardTaskJoin> getAllBTJ(){
	    	return BTJRepository.findAll();
	    }
	    /*
	    @GetMapping()
	    public Optional<BoardTaskJoin> getBTJ() {
	    	return BTJRepository.findById(new BTJIdentity(24, 6));
	    }
	    */
	    
	    /**
	     * removes task from the BTJ (the link between board and task) and from the tasks table
	     * @param bid id of the board where the task is located
	     * @param tid id of the task
	     * @return "ok" string response if the request was success null if it failed 
	     */
	   
	    //TODO add a case where if the task isn't in any other project
	    //to be removed from the server
	    @DeleteMapping("/board/{bid}/task/{tid}")
	    public ResponseEntity<String> removeBTJ(
	    		@PathVariable("bid") Integer bid,
	    		@PathVariable("tid") Integer tid) {
	    	final String query = "UPDATE board_tasks SET position = position - 1 WHERE EXISTS ("
	    			+ "	SELECT boards_tasks_join.task_id"
	    			+ "	FROM boards_tasks_join"
	    			+ "	WHERE boards_tasks_join.task_id = board_tasks.id"
	    			+ "	AND boards_tasks_join.board_id = " + bid
	    			+ "	AND board_tasks.position > " + taskRepository.getById(tid).getPosition() + ");";
	    	try {
		    	QueryConstructor.sendQuery(query);
	    	} catch (SQLException e) {
	        	e.printStackTrace();
		    	//TODO add case where the entire column gets removed if something fucks up and you get here
	        	return null;
	    	}
	    	BTJRepository.deleteById(new BTJIdentity(bid, tid));
	    	taskRepository.deleteById(tid);
	    	
	    	return ResponseEntity.ok("ok");
	    }
	    /**
	     * swaps task from one board to another
	     * @param tid id of the task which needs to be swapped
	     * @param bid id of the second board where the task needs to go
	     * @param pos position on the second board where the task needs to be
	     * @return empty object if the method failed EmptyObj if it is success
	     */
	    
	    @PutMapping("/taskid/{tid}/boardid/{bid}/newpos/{pos}")
	    public EmptyObj swapTaskBoard(
	    		@PathVariable Integer tid,
	    		@PathVariable Integer bid,
	    		@PathVariable Integer pos)
	    {
	    	//update position in the second board
	    	final String query = "UPDATE board_tasks"
	    			+ " SET position = position + 1"
	    			+ " WHERE EXISTS ("
	    			+ "	SELECT boards_tasks_join.task_id"
	    			+ "	FROM boards_tasks_join"
	    			+ "	WHERE boards_tasks_join.task_id = board_tasks.id"
	    			+ " AND board_tasks.position >= " + pos
	    			+ " AND boards_tasks_join.board_id = " + bid + ");";
	    	//changing the board for the task
	    	final String query1 = "UPDATE boards_tasks_join, board_tasks"
	    			+ " SET boards_tasks_join.board_id = " + bid + ","
	    			+ " board_tasks.position = " + pos
	    			+ " WHERE board_tasks.id = " + tid
	    			+ " AND boards_tasks_join.task_id = board_tasks.id;";
	    	
	    	ArrayList<String> queries = new ArrayList<>();
	    	queries.add(query);
	    	queries.add(query1);
	    	try {			
		    	QueryConstructor.sendQuery(queries);
	    	} catch (SQLException e) {
	        	e.printStackTrace();
	        	//TODO add a case if it fails to transfer the task to remove both boards to prevent memory leak and corruption
	        	return null;
	    	}
	    	return new EmptyObj();
	    }
	    
	    
}
