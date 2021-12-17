package com.bugtracker.boards.tasks.join;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
	    
	    //TODO add a case where if the task isn't in any other project
	    //to be removed from the server
	    @DeleteMapping("/board/{bid}/task/{tid}")
	    public BoardTaskJoin removeBTJ(
	    		@PathVariable("bid") Integer bid,
	    		@PathVariable("tid") Integer tid) {
	    	try {
	    		updatePositions(bid, tid);
	    	} catch (SQLException e) {
	        	e.printStackTrace();
	        	return null;
	    	}
	    	BTJRepository.deleteById(new BTJIdentity(bid, tid));
	    	taskRepository.deleteById(tid);
	    	return new BoardTaskJoin();
	    }
	    
	    private void updatePositions(Integer bid, Integer tid) throws SQLException {
	    	final String compareTablesQ = "CREATE TABLE IF NOT EXISTS board_tasks_comparing_temprary AS "
	    			+ "(SELECT board_tasks.id"
	    			+ "	FROM board_tasks"
	    			+ "	LEFT OUTER JOIN boards_tasks_join"
	    			+ "	ON boards_tasks_join.task_id"
	    			+ "	WHERE boards_tasks_join.task_id = board_tasks.id"
	    			+ "	AND boards_tasks_join.board_id = " + bid
	    			+ "	AND board_tasks.position > " + taskRepository.getById(tid).getPosition() + ");";
	    	final String updateFieldsQ = "UPDATE board_tasks SET position = position - 1 WHERE EXISTS "
	    			+ "(SELECT id"
	    			+ "	FROM board_tasks_comparing_temprary"
	    			+ "	WHERE board_tasks.id = board_tasks_comparing_temprary.id);";
	    	final String dropTableQ = "DROP TABLE board_tasks_comparing_temprary;";
	    	
	    	ArrayList<String> queries = new ArrayList<>();
	    	queries.add(compareTablesQ);
	    	queries.add(updateFieldsQ);
	    	queries.add(dropTableQ);
	    	
	    	for (String query : queries) {
	    		try (Connection connection = DriverManager.getConnection(dbLocation, dbUname, dbPass);
	    		PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	    			preparedStatement.executeUpdate();
			        preparedStatement.close();
			    } catch (SQLException e) {
			    	//TODO add case where the entire column gets removed if something fucks up and you get here
			        System.out.print("FATAL WARRNING, FAILURE WHILE CHANGING THE POSITIONS OF OTHER TASKS WHILE REMOVING TASK,\n ADD CASE WHERE YOU REMOVE THE ENTIRE COLUMN\n THIS WILL BREAK THE DATA FOR THE CURRENT COLUMN AND POSSIBLY BREAK THE ENTIRE PROJECT");
			        throw e;
			   }
	    	}
	    }
	    
	    /*
	     *         final String INSERT_USERS_SQL = "INSERT INTO board_tasks" +
	                "  (id, position, title, description, date_created) VALUES " +
	                " (?, ?, ?, ?, ?);";
	                
	            preparedStatement.setInt(1, 5);
	            preparedStatement.setString(2, "customquery");
	     * 
	     */
}
