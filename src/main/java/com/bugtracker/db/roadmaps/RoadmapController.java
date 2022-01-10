package com.bugtracker.db.roadmaps;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.db.project.Project;
import com.bugtracker.db.project.ProjectRepository;
import com.bugtracker.db.roles.Roles;
import com.bugtracker.db.roles.RolesIdentity;
import com.bugtracker.db.roles.RolesRepository;
import com.bugtracker.db.roles.Roles_Global;
import com.bugtracker.db.user.MyUserDetails;

@RestController
@RequestMapping("/roadmaps")
public class RoadmapController {
    @Autowired
    RoadmapRepository roadmapRepository;
  
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	RolesRepository rolesRepository;

    @GetMapping("/all")
    public List<Roadmap> getAllRoadmaps() {
        return roadmapRepository.findAll();
    }
	
    @GetMapping("/all/{projectId}")
    public List<Roadmap> getAllRoadmapsByProjectId(@PathVariable Integer projectId) {
        return roadmapRepository.findAllByProjectId(projectId);
    }
    
    @GetMapping("/all/{projectId}/getByUser")
    public ResponseEntity<List<Roadmap>> getAllRoadmapsByUserAdProjectId(
    		@AuthenticationPrincipal MyUserDetails userDetails,
    		@PathVariable Integer projectId) {    	
    	if (!Roles_Global.hasAuthorities(userDetails, projectId,
    			(GrantedAuthority) null, rolesRepository))
    		return new ResponseEntity<List<Roadmap>>(HttpStatus.FORBIDDEN);

		return new ResponseEntity<List<Roadmap>>(
				roadmapRepository.findAllByProjectId(projectId), HttpStatus.OK);
    }    
    
    
    @ResponseBody
    @PostMapping
    public ResponseEntity<String> addRoadmap(
    		@AuthenticationPrincipal MyUserDetails userDetails,
    		@RequestBody Roadmap roadmap){
    	
    	if (!Roles_Global.hasAuthorities(userDetails, roadmap.getProjectId(), 
    			new SimpleGrantedAuthority(Roles_Global.a_create), rolesRepository))
    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
    	
    	roadmapRepository.save(roadmap);
    	return ResponseEntity.ok("ok");
    }
    
    
	@ResponseBody
    @PutMapping
    public ResponseEntity<String> editRoadmap(
    		@AuthenticationPrincipal MyUserDetails userDetails,
    		@RequestBody Roadmap roadmap){
		
    	if (!Roles_Global.hasAuthorities(userDetails, roadmap.getProjectId(), 
    			new SimpleGrantedAuthority(Roles_Global.a_edit), rolesRepository))
    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
    	
    	roadmapRepository.save(roadmap);
    	return ResponseEntity.ok("ok");
    }
    

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeRoadmap(
    		@AuthenticationPrincipal MyUserDetails userDetails,
    		@PathVariable Integer id) {
    	
    	if (!Roles_Global.hasAuthorities(userDetails, id, 
    			new SimpleGrantedAuthority(Roles_Global.a_delete), rolesRepository))
    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
    	
    	roadmapRepository.deleteById(id);
    	return ResponseEntity.ok("ok");
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Optional<Roadmap> getRoadmapById(@PathVariable Integer id) {
    	return roadmapRepository.findById(id);
    }
    

}
