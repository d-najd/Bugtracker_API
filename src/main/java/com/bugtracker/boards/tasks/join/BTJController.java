package com.bugtracker.boards.tasks.join;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.EmptyObj;
import com.bugtracker.QueryConstructor;
import com.bugtracker.WebConfiguration;
import com.bugtracker.db.boards.BoardRepository;
import com.bugtracker.db.boards.tasks.Task;
import com.bugtracker.db.boards.tasks.TaskRepository;

@RestController
@RequestMapping("/btj")
public class BTJController {
		public static final String dbLocation = WebConfiguration.dbLocation;
		public static final String dbUname = WebConfiguration.dbUname;
		public static final String dbPass = WebConfiguration.dbPass;
		
	 	@Autowired
	    BTJRepository btjRepository;
	   
		@Autowired
		TaskRepository taskRepository;
		
		@Autowired
		BoardRepository boardRepository;
	 	
	    @GetMapping("/all")
	    public List<BoardTaskJoin> getAllBTJ(){
	    	return btjRepository.findAll();
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
	    	btjRepository.deleteById(new BtjIdentity(bid, tid));
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
	    
	    @PutMapping("/startboard/{fbid}/task/{tid}/endboard/{sbid}/newpos/{pos}")
	    public EmptyObj swapTaskBoard(
	    		@PathVariable("fbid") Integer fbid,
	    		@PathVariable("tid") Integer tid,
	    		@PathVariable("sbid") Integer sbid,
	    		@PathVariable("pos") Integer pos
	    		){
	    	
	    	//checking if the first task belongs to the first board so the user doesnt do anything funny
	    	Optional<Task> task = taskRepository.findById(tid);
	    	BoardTaskJoin btj = btjRepository.findOneByBtjIdentityTaskId(tid);
	    	if (btj == null || task == null || btj.getBtjIdentity().getBoardId() != fbid) {
	    		System.out.print("trying to move a task from a board that doesn't belong to the task? or you are trying to move a task that doesn't exist");
	    		return null;
	    	}

	    	Integer startTaskPos = task.get().getPosition();
	    	
	    	//update position in the second board
	    	final String query1 = "UPDATE board_tasks"
	    			+ " SET position = position + 1"
	    			+ " WHERE EXISTS ("
	    			+ "	SELECT boards_tasks_join.task_id"
	    			+ "	FROM boards_tasks_join"
	    			+ "	WHERE boards_tasks_join.task_id = board_tasks.id"
	    			+ " AND board_tasks.position >= " + pos
	    			+ " AND boards_tasks_join.board_id = " + sbid + ");";
	    	//changing the board for the task
	    	final String query2 = "UPDATE boards_tasks_join, board_tasks"
	    			+ " SET boards_tasks_join.board_id = " + sbid + ","
	    			+ " board_tasks.position = " + pos
	    			+ " WHERE board_tasks.id = " + tid
	    			+ " AND boards_tasks_join.task_id = board_tasks.id;";
	    	//updating the positions in the first board
	    	final String query3 = "UPDATE board_tasks "
	    			+ " SET position = position - 1 "
	    			+ " WHERE EXISTS ("
	    			+ "	SELECT boards_tasks_join.task_id"
	    			+ " FROM boards_tasks_join"
	    			+ " WHERE boards_tasks_join.task_id = board_tasks.id"
	    			+ " AND boards_tasks_join.board_id = " + fbid
	    			+ " AND board_tasks.position > " + startTaskPos + ");";
	    	
	    	
	    	ArrayList<String> queries = new ArrayList<>();
	    	queries.add(query1);
	    	queries.add(query2);
	    	queries.add(query3);
	    	
	    	try {
		    	QueryConstructor.sendQuery(queries);
	    	} catch (SQLException e) {
	        	e.printStackTrace();
		    	//TODO add case where the entire column gets removed if something fucks up and you get here
	        	return null;
	    	}
	    	
    		return new EmptyObj();

	    	//return new EmptyObj();
	    	//return ResponseEntity.ok("the method is disabled until a case is added where it checks if the BTJ is in the project, it may not be needed but better be safe than sorry");
	    }
	    

}
