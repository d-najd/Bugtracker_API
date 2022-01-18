package com.bugtracker.db.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.bugtracker.db.boards.Board;
import com.bugtracker.db.boards.BoardRepository;
import com.bugtracker.db.boards.tasks.TaskRepository;
import com.bugtracker.db.btj.BTJRepository;
import com.bugtracker.db.btj.BoardTaskJoin;
import com.bugtracker.db.roadmaps.Roadmap;
import com.bugtracker.db.roles.Roles_Global;
import com.bugtracker.db.roles.Roles;
import com.bugtracker.db.roles.RolesIdentity;
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
	RolesRepository rolesRepository;
	
	@GetMapping("/all")
	public ResponseEntity<List<Project>> getAllProjects(){
		return new ResponseEntity<List<Project>>(
				projectRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Project>> getProjectsById(@PathVariable Integer id){
		return new ResponseEntity<Optional<Project>>(
				projectRepository.findById(id), HttpStatus.OK);
	}
	
	@GetMapping("/getByUser")
	public ResponseEntity<List<Project>> getAllProjectsByUser(
			@AuthenticationPrincipal MyUserDetails userDetails){
		//getting all roles including the project ids
		
		List<Roles> roles = rolesRepository.findAllByRolesIdentityUsername(userDetails.getUsername());
		//getting just the project ids
		List<Integer> ids = new ArrayList<>();
		for (Roles role : roles) {
			ids.add(role.getRolesIdentity().getProjectId());
		}
		return new ResponseEntity<List<Project>>(
				projectRepository.findAllByIdIn(ids), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> createProject(
			@RequestBody Project project,
			@AuthenticationPrincipal MyUserDetails userDetails) {
		project.setOwnerId(userDetails.getUsername());
		project = projectRepository.save(project);
		Roles roles = new Roles(userDetails.getUsername(), project.getId(), true, true, true, true, true);
		rolesRepository.save(roles);
		return ResponseEntity.ok("ok");
	}
	
	@PutMapping
	public ResponseEntity<String> editProject(
			@RequestBody Project project,
			@AuthenticationPrincipal MyUserDetails userDetails) {
		if (!Roles_Global.hasAuthorities(userDetails, project.getId(),
				new SimpleGrantedAuthority(Roles_Global.a_manage_project), rolesRepository, projectRepository))
			return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
		
		project.setOwnerId(userDetails.getUsername());
		projectRepository.save(project);
		return ResponseEntity.ok("ok");
	}
	
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeProject(
    		@PathVariable Integer id,
    		@AuthenticationPrincipal MyUserDetails userDetails) {
    	//check if it is the owner of the project trying to remove it since he should be the only one allowed
    	try {
    		String owner = projectRepository.getById(id).getOwnerId();
    		
        	if (!owner.equals(userDetails.getUsername())) 
        		return new ResponseEntity<String>("You aren't the admin to remove the project", HttpStatus.FORBIDDEN);
		} catch (EntityNotFoundException e) {
    		return new ResponseEntity<String>("trying to get a project that doesn't exist?", HttpStatus.I_AM_A_TEAPOT);
		}
    		
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
