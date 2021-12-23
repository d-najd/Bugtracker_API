package com.bugtracker.db.roadmaps;

import java.util.List;
import java.util.Optional;

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

import com.bugtracker.project.roadmaps.Project_Roadmaps;
import com.bugtracker.project.roadmaps.Project_RoadmapsIdentity;
import com.bugtracker.project.roadmaps.Project_RoadmapsRepository;

@RestController
@RequestMapping("/roadmaps")
public class RoadmapController {
    @Autowired
    RoadmapRepository roadmapRepository;
    
 	@Autowired
    Project_RoadmapsRepository project_RoadmapsRepository;
  
    
    @GetMapping("/all")
    public List<Roadmap> getAllRoadmaps() {
        return roadmapRepository.findAll();
    }
    
    @ResponseBody
    @GetMapping("/all/{userid}")
    public List<Roadmap> getAllRoadmaps(@PathVariable Integer userid) {
        return roadmapRepository.findAllByUserId(userid);
    }
    
    @ResponseBody
    @GetMapping("/{userid}/{fieldid}")
    public Optional<Roadmap> getRoadmapByFieldId(@PathVariable("userid") Integer userid, @PathVariable("fieldid") Integer fieldid){
    	return roadmapRepository.findByIdAndUserId(fieldid, userid);
    }
    
    @ResponseBody
    @PutMapping()
    public Roadmap editRoadmap(@RequestBody Roadmap roadmap){
    	Roadmap roadmapNew = roadmapRepository.save(roadmap);
    	return roadmapNew;
    }
    
    @ResponseBody
    @PostMapping("/{projectId}")
    public Roadmap addRoadmap(@RequestBody Roadmap roadmap, @PathVariable Integer projectId){
    	Roadmap newRoadmap = roadmapRepository.save(roadmap);
    	Project_RoadmapsIdentity identity = new Project_RoadmapsIdentity(projectId, newRoadmap.getId());
    	Project_Roadmaps project_roadmap = new Project_Roadmaps(identity);
    	project_RoadmapsRepository.save(project_roadmap);
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
