package com.bugtracker.db.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.bugtracker.db.boards.Board;
import com.bugtracker.db.boards.BoardRepository;
import com.bugtracker.db.boards.tasks.TaskRepository;
import com.bugtracker.db.btj.BTJRepository;
import com.bugtracker.db.btj.BoardTaskJoin;
import com.bugtracker.db.roadmaps.Roadmap;
import com.bugtracker.db.roles.Roles;
import com.bugtracker.db.roles.RolesRepository;
import com.bugtracker.db.user.MyUserDetails;

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
 	
	@Autowired
	RolesRepository roleRepository;
	
	@GetMapping("/all")
	public List<Project> getAllProjects(){
		return projectRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Project> getProjectById(@PathVariable Integer id){
		return projectRepository.findById(id);
	}
	
	@GetMapping("/getByUser")
	public List<Project> getAllProjectByUser(@AuthenticationPrincipal MyUserDetails userDetails){
		//getting all roles including the project ids
		List<Roles> roles = roleRepository.findAllByRolesIdentityUsername(userDetails.getUsername());
		//getting just the project ids
		List<Integer> ids = new ArrayList<>();
		for (Roles role : roles) {
			ids.add(role.getRolesIdentity().getProjectId());
		}
		return projectRepository.findAllByIdIn(ids);
	}
	
	@ResponseBody
	@PostMapping
	public Project createProject(@RequestBody Project project) {
		return projectRepository.save(project);
	}
	
	@ResponseBody
	@PutMapping
	public Project editProject(@RequestBody Project project) {
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
