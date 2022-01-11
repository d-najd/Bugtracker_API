package com.bugtracker.db.boards;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
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

import com.bugtracker.QueryConstructor;
import com.bugtracker.db.boards.tasks.TaskRepository;
import com.bugtracker.db.project.ProjectRepository;
import com.bugtracker.db.roadmaps.Roadmap;
import com.bugtracker.db.roles.RolesRepository;
import com.bugtracker.db.roles.Roles_Global;
import com.bugtracker.db.user.MyUserDetails;

import net.bytebuddy.asm.Advice.Exit;


@RestController
@RequestMapping("/boards")
public class BoardController {
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	TaskRepository taskRepository;
 	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	RolesRepository rolesRepository;
	

	@ResponseBody
	@GetMapping("/all/{projectId}/getByUser")
	public ResponseEntity<List<Board>> getProjectBoardsByUser(
			@AuthenticationPrincipal MyUserDetails userDetails,
			@PathVariable Integer projectId){
    	if (!Roles_Global.hasAuthorities(userDetails, projectId, 
    			(GrantedAuthority) null, rolesRepository))
    		return new ResponseEntity<List<Board>>(HttpStatus.FORBIDDEN);
    	
    	return new ResponseEntity<List<Board>>(
    			boardRepository.findAllByProjectId(projectId), HttpStatus.OK);
    }
    
	@ResponseBody
	@PostMapping
    public ResponseEntity<String> createBoard(
    		@AuthenticationPrincipal MyUserDetails userDetails,
    		@RequestBody Board board){
    	if (!Roles_Global.hasAuthorities(userDetails, board.getProjectId(), 
    			new SimpleGrantedAuthority(Roles_Global.a_create), rolesRepository))
    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
		
    	boardRepository.save(board);
    	return ResponseEntity.ok("ok");
    }
    
    @ResponseBody
    @PutMapping
    public ResponseEntity<String> editBoard(
    		@AuthenticationPrincipal MyUserDetails userDetails,
    		@RequestBody Board board){
    	
    	if (!Roles_Global.hasAuthorities(userDetails, board.getProjectId(), 
    			new SimpleGrantedAuthority(Roles_Global.a_edit), rolesRepository))
    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
		
    	boardRepository.save(board);
    	return ResponseEntity.ok("ok");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeBoard(
    		@AuthenticationPrincipal MyUserDetails userDetails,
    		@PathVariable Integer id) {
    	
    	try {
    		Integer board = boardRepository.getById(id).getProjectId();
		} catch (EntityNotFoundException e) {
    		return new ResponseEntity<String>("trying to get a board that doesn't exist?", HttpStatus.I_AM_A_TEAPOT);
		}
    	
    	if (!Roles_Global.hasAuthorities(userDetails, boardRepository.getById(id).getProjectId(), 
    			new SimpleGrantedAuthority(Roles_Global.a_delete), rolesRepository))
    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
    	
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
    		System.out.print("FATAL ERROR A MEMORY HAS PROBABLY OCCURED WHILE TRYING TO REMOVE BOARD");
        	e.printStackTrace();
    		return new ResponseEntity<String>("SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    	} 
    	return ResponseEntity.ok("ok");
    	//return ResponseEntity.ok("the function for now is diabled until there is added case where it checks if the task is in the selected project and only update the other tasks if it is in that project");
    	
    }
    
    @PutMapping("/swap/first/{fId}/second/{sId}")
    public ResponseEntity<String> swapBoardsRequest(
    		@AuthenticationPrincipal MyUserDetails userDetails,
    		@PathVariable("fId") Integer fId,
    		@PathVariable("sId") Integer sId) {
    	
    	try {
    		Integer board = boardRepository.getById(fId).getProjectId();
    		Integer board1 = boardRepository.getById(sId).getProjectId();
    		if (!board.equals(board1))
    			return new ResponseEntity<String>("trying to swap boards between projects?", HttpStatus.BAD_REQUEST);
		} catch (EntityNotFoundException e) {
    		return new ResponseEntity<String>("trying to get a swap boards that don't exist?", HttpStatus.I_AM_A_TEAPOT);
		}
    	
    	if (!Roles_Global.hasAuthorities(userDetails, boardRepository.getById(fId).getProjectId(), 
    			new SimpleGrantedAuthority(Roles_Global.a_edit), rolesRepository))
    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
    	
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
    	} catch (EntityNotFoundException | SQLException e) {
    		System.out.print("\n\nwhile trying to swap boards one of the boards doesn't exist which should be impossible except if 2 users make the same request in the same time and the second is sql error in which case there is probably a memory leak\n\n");
    		e.printStackTrace();
        	return new ResponseEntity<String>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	return ResponseEntity.ok("ok");
    }
    
	@GetMapping("/all")
	public List<Board> getAllBoardsOld() {
    	return boardRepository.findAll();
	}
	
	@GetMapping("/all/{projectId}")
	public List<Board> getAllBoards(@PathVariable Integer projectId){
        return boardRepository.findAllByProjectId(projectId);
	}
	
    @ResponseBody
    @GetMapping("/{id}")
    public String getBoard(@PathVariable Integer id){
    	return "disabed";
    	//return boardRepository.findById(id).get();
    } 
}
