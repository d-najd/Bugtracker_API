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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.db.roadmaps.Roadmap;

@RestController
public class BoardController {
	@Autowired
	BoardRepository boardRepository;
	
	@GetMapping("/boards/all")
	public List<Board> getAllBoards(){
		return boardRepository.findAll();
	}
	
    @ResponseBody
    @GetMapping("/boards/all/{userid}")
    public List<Board> getAllRoadmapsByUID(@PathVariable Integer userid) {
        return boardRepository.findAllByUserId(userid);
    }
	
	@ResponseBody
	@PostMapping("/boards")
    public Board addBoard(@RequestBody Board board){
    	return boardRepository.save(board);
    }
    
    @ResponseBody
    @PutMapping("/boards")
    public Board editBoard(@RequestBody Board board){
    	return boardRepository.save(board);
    }
    
    @ResponseBody
    @DeleteMapping("/boards/{id}")
    public Board removeBoard(@PathVariable Integer id) {
    	boardRepository.deleteById(id);
    	return new Board();
    }
}
