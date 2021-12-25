package com.bugtracker.db.boards;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

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

import com.bugtracker.EmptyObj;
import com.bugtracker.QueryConstructor;
import com.bugtracker.db.boards.tasks.Task;
import com.bugtracker.db.boards.tasks.TaskRepository;
import com.bugtracker.db.roadmaps.Roadmap;
import com.bugtracker.project.Project;
import com.bugtracker.project.ProjectRepository;
import com.bugtracker.project.boards.ProjectBoards;
import com.bugtracker.project.boards.ProjectBoardsIdentity;
import com.bugtracker.project.boards.ProjectBoardsRepository;
import com.bugtracker.project.roadmaps.ProjectRoadmaps;
import com.bugtracker.project.roadmaps.ProjectRoadmapsIdentity;

@RestController
@RequestMapping("/boards")
public class BoardController {
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
 	@Autowired
    ProjectBoardsRepository projectBoardsRepository;
 	
	@Autowired
	ProjectRepository projectRepository;
	
	
	@GetMapping("/all")
	public List<Board> getAllBoardsOld() {
    	System.out.print("\n\nWARRNING, this is an outdated method and probably performance heavy in the long run, use ip/all/{projectId} instead\n\n");
    	return boardRepository.findAll();
	}
	
	@GetMapping("/all/{projectId}")
	public List<Board> getAllBoards(@PathVariable Integer projectId){
    	List <Integer> boardsIds = new ArrayList<>();
    	List<ProjectBoards> sBoardsRaw = projectBoardsRepository.
    			findAllByProjectBoardsIdentityProjectId(projectId);
    	for (ProjectBoards sBoard : sBoardsRaw) {
    		boardsIds.add(sBoard.getProjectBoardsIdentity().getBoardId());
    	}    	
    	
        return boardRepository.findAllById(boardsIds);
	}
    
    @ResponseBody
    @GetMapping("/{id}")
    public Board getBoard(@PathVariable Integer id){
    	return boardRepository.findById(id).get();
    }
	
    @PostMapping
    public ResponseEntity<String> createBoardOld(){
    	return ResponseEntity.ok("this method has been decapitated, use ip/{projectId} instead");

    }
    
	@ResponseBody
	@PostMapping("/{projectId}")
    public Board createBoard(@RequestBody Board board, @PathVariable Integer projectId){
    	boolean crash = false;
    	try {
    		Project project = projectRepository.getById(projectId);
    		String title = project.getTitle();
    	} catch(EntityNotFoundException e){
    		System.out.print("\n\nWARRNING, some moron is trying to create a board in nonexisting project, why not crash his phone instead?\n\n");
    		crash = true;
    	}
    	
    	if (crash == true) {
    		return new Board("can't save a board to nonexisting project, seems dumb don't you think?");
    	}
    	
    	Board newBoard = boardRepository.save(board);
    	ProjectBoardsIdentity identity = new ProjectBoardsIdentity(projectId, newBoard.getId());
    	ProjectBoards projectBoard = new ProjectBoards(identity);
    	projectBoardsRepository.save(projectBoard);
    	return newBoard;
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
    	return ResponseEntity.ok("the function for now is diabled until there is added case where it checks if the task is in the selected project and only update the other tasks if it is in that project");
    	
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
    	} catch (EntityNotFoundException | SQLException e) {
        	return new EmptyObj("well either it is a SQLException or you tried to swap boards which don't exist");
    	}
    	return new EmptyObj();
    }
}
