package com.bugtracker.db.btj;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.QueryConstructor;
import com.bugtracker.WebConfiguration;
import com.bugtracker.db.boards.BoardRepository;
import com.bugtracker.db.boards.tasks.Task;
import com.bugtracker.db.boards.tasks.TaskRepository;
import com.bugtracker.db.roles.RolesRepository;
import com.bugtracker.db.roles.Roles_Global;
import com.bugtracker.db.user.MyUserDetails;

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
		
		@Autowired
		RolesRepository rolesRepository;
	 	
	    @GetMapping("/all")
	    public List<BoardTaskJoin> getAllBTJ(){
	    	return btjRepository.findAll();
	    }
	        
	    /**
	     * removes task from the BTJ (the link between board and task) and from the tasks table
	     * @param bid id of the board where the task is located
	     * @param tid id of the task
	     * @return "ok" string response if the request was success null if it failed 
	     */
	    
	    @DeleteMapping("/board/{bid}/task/{tid}")
	    public ResponseEntity<String> removeBTJOLD(@PathVariable("bid") Integer bid, @PathVariable("tid") Integer tid){
	    	return new ResponseEntity<String>("this method is disabled please use /btj/task/{tid}", HttpStatus.BAD_REQUEST); 
	    }
	    
	    @DeleteMapping("/task/{tid}")
	    public ResponseEntity<String> removeBTJ(
	    		@AuthenticationPrincipal MyUserDetails userDetails,
	    		@PathVariable("tid") Integer tid) {
	    	Integer bid;
	    	try {
	    		bid = btjRepository.findOneByBtjIdentityTaskId(
		    			tid).getBtjIdentity().getBoardId();
		    	Integer projectId = boardRepository.findById(bid).get().getProjectId();
		    	
		    	if (!Roles_Global.hasAuthorities(userDetails, projectId, 
		    			new SimpleGrantedAuthority(Roles_Global.a_delete), rolesRepository))
		    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
	    	} catch (EntityNotFoundException | NullPointerException e) {
	    		return new ResponseEntity<String>("trying to edit a task that doesn't exist?", HttpStatus.I_AM_A_TEAPOT);
			}	    	
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
	        	return new ResponseEntity<String>("Something went wrong while trying to remove a task, a memory leak is probably caused, the task in question was: " + tid, HttpStatus.INTERNAL_SERVER_ERROR);
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
	    public ResponseEntity<String> swapTaskBoard() {
	    	return new ResponseEntity<String>("this method is disabled please use /btj/task/{tid}/endboard/{sbid}/newpos/{pos}", HttpStatus.BAD_REQUEST); 
	    }
	    
	    
	    @PutMapping("/task/{tid}/endboard/{sBid}/newpos/{pos}")
	    public ResponseEntity<String> swapTasksBoard(
	    		@AuthenticationPrincipal MyUserDetails userDetails,
	    		@PathVariable("tid") Integer tid,
	    		@PathVariable("sBid") Integer sBid,
	    		@PathVariable("pos") Integer pos
	    		){
	    	
	    	Integer fBid;
	    	try {
	    		fBid = btjRepository.findOneByBtjIdentityTaskId(
		    			tid).getBtjIdentity().getBoardId();
		    	Integer projectId = boardRepository.getById(fBid).getProjectId();
		    	Integer projectId1 = boardRepository.getById(sBid).getProjectId();
		    	
		    	if (!projectId.equals(projectId1))
		    		return new ResponseEntity<String>("Can't swap boards between projects", HttpStatus.BAD_REQUEST);
		    	
		    	if (!Roles_Global.hasAuthorities(userDetails, projectId, 
		    			new SimpleGrantedAuthority(Roles_Global.a_edit), rolesRepository))
		    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
	    	} catch (EntityNotFoundException | NullPointerException e) {
	    		return new ResponseEntity<String>("trying to edit a task that doesn't exist?", HttpStatus.I_AM_A_TEAPOT);
	    	}
	    	
	    	Integer startTaskPos = taskRepository.getById(tid).getPosition();
	    	
	    	//update position in the second board
	    	final String query1 = "UPDATE board_tasks"
	    			+ " SET position = position + 1"
	    			+ " WHERE EXISTS ("
	    			+ "	SELECT boards_tasks_join.task_id"
	    			+ "	FROM boards_tasks_join"
	    			+ "	WHERE boards_tasks_join.task_id = board_tasks.id"
	    			+ " AND board_tasks.position >= " + pos
	    			+ " AND boards_tasks_join.board_id = " + sBid + ");";
	    	//changing the board for the task
	    	final String query2 = "UPDATE boards_tasks_join, board_tasks"
	    			+ " SET boards_tasks_join.board_id = " + sBid + ","
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
	    			+ " AND boards_tasks_join.board_id = " + fBid
	    			+ " AND board_tasks.position > " + startTaskPos + ");";
	    	
	    	ArrayList<String> queries = new ArrayList<>();
	    	queries.add(query1);
	    	queries.add(query2);
	    	queries.add(query3);
	    	try {
		    	QueryConstructor.sendQuery(queries);
	    	} catch (SQLException e) {
	        	e.printStackTrace();
	        	return new ResponseEntity<String>("Something went wrong while trying to swap tasks, a memory leak is probably caused, the task in question was: " + tid + " end board: " + sBid + " new position: " + pos, HttpStatus.INTERNAL_SERVER_ERROR);
	    	}
	    	return ResponseEntity.ok("ok");
	    }
}
