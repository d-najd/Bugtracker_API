package com.bugtracker.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bugtracker.boards.tasks.join.BTJRepository;
import com.bugtracker.boards.tasks.join.BoardTaskJoin;
import com.bugtracker.db.boards.Board;
import com.bugtracker.db.boards.BoardRepository;
import com.bugtracker.db.boards.tasks.TaskRepository;
import com.bugtracker.db.roadmaps.Roadmap;

@RestController
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
 	@Autowired
    BTJRepository btjRepository;
	
	@GetMapping("/all")
	public List<Project> getAllProjects(){
		return projectRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Project> getProjectById(@PathVariable Integer id){
		return projectRepository.findById(id);
	}
	
	@ResponseBody
	@PostMapping
	public Project createProject(@RequestBody Project project) {
		return projectRepository.save(project);
	}
	
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeProject(@PathVariable Integer id) {
    	//removing the tasks from the project to prevent a memory leak, since they are not removed with the project when removing it
    	List<Board> boards = boardRepository.findAllByProjectId(id);
    	for (Board board : boards) {
    		List<BoardTaskJoin> btjs = btjRepository.findAllByBtjIdentityBoardId(board.getId());
    		for (BoardTaskJoin btj : btjs)
        		taskRepository.deleteById(btj.getBtjIdentity().getTaskId());
    	}
    	//removing the project
    	projectRepository.deleteById(id);
    	return ResponseEntity.ok("ok");
    } 
}