package com.bugtracker.db.roadmaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.annotations.common.util.impl.Log;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.project.Project;
import com.bugtracker.project.ProjectRepository;
import com.bugtracker.project.roadmaps.ProjectRoadmaps;
import com.bugtracker.project.roadmaps.ProjectRoadmapsIdentity;
import com.bugtracker.project.roadmaps.ProjectRoadmapsRepository;

@RestController
@RequestMapping("/roadmaps")
public class RoadmapController {
    @Autowired
    RoadmapRepository roadmapRepository;
    
 	@Autowired
    ProjectRoadmapsRepository projectRoadmapsRepository;
  
	@Autowired
	ProjectRepository projectRepository;

    @GetMapping("/all/{projectId}")
    public List<Roadmap> getAllRoadmapsByProjectId(@PathVariable Integer projectId) {
    	List <Integer> roadmapIds = new ArrayList<>();
    	List<ProjectRoadmaps> sRoadmapsRaw = projectRoadmapsRepository.
    			findAllByProjectRoadmapsIdentityProjectId(projectId);
    	for (ProjectRoadmaps sRoadmap : sRoadmapsRaw) {
    		roadmapIds.add(sRoadmap.getProjectRoadmapsIdentity().getRoadmapId());
    	}    	
    	
        return roadmapRepository.findAllById(roadmapIds);
    }
    
	@ResponseBody
    @PutMapping
    public Roadmap editRoadmap(@RequestBody Roadmap roadmap){
    	return roadmapRepository.save(roadmap);
    }
    
    @ResponseBody
    @PostMapping("/{projectId}")
    public Roadmap addRoadmap(@RequestBody Roadmap roadmap, @PathVariable Integer projectId){
    	boolean crash = false;
    	try {
    		Project project = projectRepository.getById(projectId);
    		String title = project.getTitle();
    	} catch(EntityNotFoundException e){
    		System.out.print("\n\nWARRNING, some moron is trying to create a roadmap in nonexisting project, why not crash his phone instead?\n\n");
    		crash = true;
    	}
    	
    	if (crash == true) {
    		return new Roadmap("Cant save a roadmap to a project that doesn't exist, seems dumb don't you think?");
    	}
    	
    	Roadmap newRoadmap = roadmapRepository.save(roadmap);
    	ProjectRoadmapsIdentity identity = new ProjectRoadmapsIdentity(projectId, newRoadmap.getId());
    	ProjectRoadmaps projectRoadmap = new ProjectRoadmaps(identity);
    	projectRoadmapsRepository.save(projectRoadmap);
    	return newRoadmap;
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
