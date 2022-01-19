package com.bugtracker.db.roles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.db.project.ProjectRepository;
import com.bugtracker.db.user.MyUserDetails;

@RestController
@RequestMapping("/roles")
public class RolesController {

	@Autowired
	RolesRepository rolesRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@GetMapping("/all")
	public List<Roles> getAllRoles(){
		return rolesRepository.findAll();
	}
	
	@GetMapping("/currentRoles")
	public Collection<? extends GrantedAuthority> getCurrentRoles(
			@AuthenticationPrincipal MyUserDetails userDetails) {
		return userDetails.getAuthorities();
	}
	
	@GetMapping("/addAuthority")
	public void addAuthorityTest(@AuthenticationPrincipal MyUserDetails userDetails) {
		userDetails.addAuthority(new SimpleGrantedAuthority("ROLE_JESUS"));
	}
	
	@GetMapping("/username/{username}")
	public List<Roles> getAllRolesByUsername(@PathVariable String username) {
		return rolesRepository.findAllByRolesIdentityUsername(username);
	}
	
	@GetMapping("/projectId/{projectId}")
	public List<Roles> getAllRolesByProjectId(@PathVariable Integer projectId) {
		return rolesRepository.findAllByRolesIdentityProjectId(projectId);
	}
	
	@GetMapping("/username/{username}/projectId/{projectId}")
	public ResponseEntity<Roles> getAllRolesByIdentity(
			@AuthenticationPrincipal MyUserDetails userDetails,
			@PathVariable String username,
			@PathVariable Integer projectId){
		
		RolesIdentity rolesIdentity = new RolesIdentity(username, projectId);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(Roles_Global.a_manage_project));
		authorities.add(new SimpleGrantedAuthority(Roles_Global.a_manage_users));
				
		if (rolesIdentity.getUsername().equals(userDetails.getUsername()) || Roles_Global.hasAuthorities(userDetails, rolesIdentity.getProjectId(), 
    			authorities, rolesRepository, projectRepository)) {
			return new ResponseEntity<Roles>(rolesRepository.findByRolesIdentity(rolesIdentity), HttpStatus.OK);
		} else
			return new ResponseEntity<Roles>((Roles) null, HttpStatus.FORBIDDEN);
	}
	
	@GetMapping
	public String getRolesByIdentityNOTWORKING(
			@AuthenticationPrincipal MyUserDetails userDetails,
			@RequestBody RolesIdentityOnly identity) {
		
		return "this shit doesn't want to work for some F****** reason use the more primitive one above in code or xxx.xxx.xxx/roles/username/{}/projectId/{}";
		/*
		RolesIdentity rolesIdentity = new RolesIdentity(identity.getUsername(), identity.getProjectId());
		
		List<Roles> test = new ArrayList<>();
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(Roles_Global.a_manage_project));
		authorities.add(new SimpleGrantedAuthority(Roles_Global.a_manage_users));
				
		if (rolesIdentity.getUsername().equals(userDetails.getUsername()) || Roles_Global.hasAuthorities(userDetails, rolesIdentity.getProjectId(), 
    			authorities, rolesRepository, projectRepository)) {
			//return new ResponseEntity<Roles>(rolesRepository.findByRolesIdentity(identity.getRolesIdentity()), HttpStatus.OK);
			test.add(rolesRepository.findByRolesIdentity(rolesIdentity))
			return test;
		} else
			//return new ResponseEntity<Roles>((Roles) null, HttpStatus.FORBIDDEN);
			return null;
			*/
	}

	//TODO check if the user making the request has authority to do this request
	@PostMapping
	public ResponseEntity<String> addRole(
			@AuthenticationPrincipal MyUserDetails userDetails,
			@RequestBody Roles role) {
		if (!role.getManageProject() && !role.getManageUsers())
		{
	    	if (!Roles_Global.hasAuthorities(userDetails, role.getRolesIdentity().getProjectId(), 
	    			new SimpleGrantedAuthority(Roles_Global.a_manage_users), rolesRepository, projectRepository))
	    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
		} else if (!role.getManageProject()) {
	    	if (!Roles_Global.hasAuthorities(userDetails, role.getRolesIdentity().getProjectId(), 
	    			new SimpleGrantedAuthority(Roles_Global.a_manage_project), rolesRepository, projectRepository))
	    		return new ResponseEntity<String>("missing authories for current action", HttpStatus.FORBIDDEN);
		}
		
		rolesRepository.save(role);
		return ResponseEntity.ok("ok");
	}
	
	@PutMapping
	public ResponseEntity<String> updateRoles(
			@AuthenticationPrincipal MyUserDetails userDetails,
			@RequestBody Roles role) {
		return new ResponseEntity<String>("the post request is being used because there is no way to know whether a user is in the database", HttpStatus.SERVICE_UNAVAILABLE); 
		//return roleRepository.save(role);
	}
	
	//TODO check if the user making the request has authority to do this request
	@DeleteMapping
	public Boolean deleteRoles(@RequestBody RolesIdentity identity){
		return rolesRepository.deleteByRolesIdentity(identity);
	}
}
