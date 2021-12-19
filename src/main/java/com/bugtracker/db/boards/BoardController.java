package com.bugtracker.db.boards;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.QueryConstructor;
import com.bugtracker.db.boards.tasks.Task;
import com.bugtracker.db.boards.tasks.TaskRepository;
import com.bugtracker.db.roadmaps.EmptyObj;
import com.bugtracker.db.roadmaps.Roadmap;

@RestController
@RequestMapping("/boards")
public class BoardController {
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@GetMapping("/all")
	public List<Board> getAllBoards(){
		return boardRepository.findAll();
	}
	
    @ResponseBody
    @GetMapping("/all/{userid}")
    public List<Board> getAllRoadmapsByUID(@PathVariable Integer userid) {
        return boardRepository.findAllByUserId(userid);
    }
    
    @ResponseBody
    @GetMapping("/{id}")
    public Board getBoard(@PathVariable Integer id){
    	return boardRepository.findById(id).get();
    }
	
	@ResponseBody
	@PostMapping
    public Board createBoard(@RequestBody Board board){
    	return boardRepository.save(board);
    }
    
    @ResponseBody
    @PutMapping
    public Board editBoard(@RequestBody Board board){
    	return boardRepository.save(board);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeBoard(@PathVariable Integer id) {
    	try {
    		//set the other boards in the correct position
	    	final String query = "UPDATE boards SET position = position - 1"
	    			+ " WHERE boards.position > " + boardRepository.getById(id).getPosition() + ";";
	    	//remove tasks
	    	final String query1 = "DELETE FROM board_tasks WHERE EXISTS ("
	    			+ " SELECT boards_tasks_join.task_id"
	    			+ "	FROM boards_tasks_join"
	    			+ "	WHERE boards_tasks_join.task_id = board_tasks.id"
	    			+ " AND boards_tasks_join.board_id = " + id + ")";
	    	ArrayList<String> queries = new ArrayList<>();
	    	queries.add(query);
	    	queries.add(query1);
	    	
	    	QueryConstructor.sendQuery(queries);

	    	
	    	boardRepository.deleteById(id);
	    	
	    	
    	} catch (SQLException e) {
        	e.printStackTrace();
        	return ResponseEntity.ok("Server SQL exception");
    	}  		
    	return ResponseEntity.ok("ok");
    }
    
    
    /*
    @PutMapping("/{id}/task/{taskId}")
    Board setTaskToBoard(
            @PathVariable Integer id,
            @PathVariable Integer taskId
    ) {
        Board board = boardRepository.findById(id).get();
        Task task = taskRepository.findById(taskId).get();
        board.tasks.add(task);
        return boardRepository.save(board);
    }
    */
    
    @PutMapping("/{id}/task/{taskId}")
    public String setTaskToBoard() {
    	return ("this method is disabled because with the current configuration "
    			+ "it will break stuff");	
    }
    
    @PutMapping("/swap/first/{fId}/second/{sId}")
    public EmptyObj swapBoardsRequest(
    		@PathVariable("fId") Integer fId,
    		@PathVariable("sId") Integer sId) {
    	try {
    		ArrayList<String> queries = new ArrayList<>();
    		final String query = "UPDATE boards SET position = " +
    			boardRepository.getById(sId).getPosition() + 
    			" WHERE id = " + fId + ";";
    		final String query2 = "UPDATE boards SET position = " +
        			boardRepository.getById(fId).getPosition() + 
        			" WHERE id = " + sId + ";";
    		queries.add(query);
    		queries.add(query2);
    		QueryConstructor.sendQuery(queries);
    	} catch (SQLException e) {
        	e.printStackTrace();
        	return null;
    	}
    	return new EmptyObj();
    }
}
