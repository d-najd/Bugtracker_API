package com.bugtracker.db.boards;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.db.boards.tasks.Task;
import com.bugtracker.db.boards.tasks.TaskRepository;
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
    
    @ResponseBody
    @DeleteMapping("/{id}")
    public Board removeBoard(@PathVariable Integer id) {
    	boardRepository.deleteById(id);
    	return new Board();
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
}
