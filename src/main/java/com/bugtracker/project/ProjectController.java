package com.bugtracker.project;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bugtracker.db.roadmaps.Roadmap;

@RestController
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	ProjectRepository projectRepository;
	
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
    	projectRepository.deleteById(id);
    	return ResponseEntity.ok("ok");
    } 
}
