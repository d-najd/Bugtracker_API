package com.bugtracker.db.roadmaps;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.project.ProjectRepository;

@RestController
@RequestMapping("/roadmaps")
public class RoadmapController {
    @Autowired
    RoadmapRepository roadmapRepository;
  
	@Autowired
	ProjectRepository projectRepository;

    @GetMapping("/all")
    public List<Roadmap> getAllRoadmaps() {
        return roadmapRepository.findAll();
    }
	
    @GetMapping("/all/{projectId}")
    public List<Roadmap> getAllRoadmapsByProjectId(@PathVariable Integer projectId) {
        return roadmapRepository.findAllByProjectId(projectId);
    }
    
	@ResponseBody
    @PutMapping
    public Roadmap editRoadmap(@RequestBody Roadmap roadmap){
    	return roadmapRepository.save(roadmap);
    }
    
    @ResponseBody
    @PostMapping
    public Roadmap addRoadmap(@RequestBody Roadmap roadmap){
		try {
			Roadmap r = roadmapRepository.save(roadmap);
			return r;
		} catch (DataIntegrityViolationException e){
			System.out.print("\n\nTrying to save board to nonexistant project\n\n");
			return null;
		}
    }
    
    @DeleteMapping("/{fieldid}")
    public ResponseEntity<String> removeRoadmap(@PathVariable Integer fieldid) {
    	roadmapRepository.deleteById(fieldid);
    	return ResponseEntity.ok("ok");
    }

    @ResponseBody
    @GetMapping("/{fieldid}")
    public Optional<Roadmap> getRoadmapByFieldId(@PathVariable Integer fieldid) {
    	return roadmapRepository.findById(fieldid);
    }
}
