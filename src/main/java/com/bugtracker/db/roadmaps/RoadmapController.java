package com.bugtracker.db.roadmaps;

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

@RestController
public class RoadmapController {
    @Autowired
    RoadmapRepository roadmapRepository;
   
    @GetMapping("/roadmaps/all")
    public List<Roadmap> getAllRoadmaps() {
        return roadmapRepository.findAll();
    }
    
    @ResponseBody
    @GetMapping("/roadmaps/all/{userid}")
    public List<Roadmap> getAllRoadmaps(@PathVariable Integer userid) {
        return roadmapRepository.findAllByUserId(userid);
    }
    
    @ResponseBody
    @GetMapping("/roadmaps/{userid}/{fieldid}")
    public Optional<Roadmap> getRoadmapByFieldId(@PathVariable("userid") Integer userid, @PathVariable("fieldid") Integer fieldid){
    	return roadmapRepository.findByIdAndUserId(fieldid, userid);
    }

    //NOTE everyone will be able to update whichever field they choose, needs to check if the
    //userid is authenticated and if he is updating his field id and not the field id of someone else
    @ResponseBody
    @PutMapping("/roadmaps/{fieldid}")
    public Roadmap editRoadmap(@PathVariable("fieldid") Integer fieldid, @RequestBody Roadmap roadmap){
    	return roadmapRepository.save(roadmap);
    	
    }
    
    @ResponseBody
    @PostMapping("/roadmaps")
    public Roadmap addRoadmap(@RequestBody Roadmap roadmap){
    	return roadmapRepository.save(roadmap);
    }
    
    @ResponseBody
    @DeleteMapping("/roadmaps/{fieldid}")
    public Roadmap removeRoadmap(@PathVariable Integer fieldid) {
    	roadmapRepository.deleteById(fieldid);
    	return new Roadmap();
    }

    @ResponseBody
    @GetMapping("/roadmaps/{fieldid}")
    public Optional<Roadmap> getRoadmapByFieldId(@PathVariable Integer fieldid) {
    	return roadmapRepository.findById(fieldid);
    }
    
}
