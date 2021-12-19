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

@RestController
@RequestMapping("/roadmaps")
public class RoadmapController {
    @Autowired
    RoadmapRepository roadmapRepository;
   
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

    //NOTE everyone will be able to update whichever field they choose, needs to check if the
    //userid is authenticated and if he is updating his field id and not the field id of someone else
    @ResponseBody
    @PutMapping
    public Roadmap editRoadmap(@RequestBody Roadmap roadmap){
    	return roadmapRepository.save(roadmap);
    }
    
    @ResponseBody
    @PostMapping
    public Roadmap addRoadmap(@RequestBody Roadmap roadmap){
    	return roadmapRepository.save(roadmap);
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
