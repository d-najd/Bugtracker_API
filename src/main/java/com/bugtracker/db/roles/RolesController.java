package com.bugtracker.db.roles;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.db.user.MyUserDetails;

@RestController
@RequestMapping("/roles")
public class RolesController {

	@Autowired
	private RolesRepository roleRepository;
	
	@GetMapping("/all")
	public List<Roles> getAllRoles(){
		return roleRepository.findAll();
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
		return roleRepository.findAllByRolesIdentityUsername(username);
	}
	
	@GetMapping("/projectId/{projectId}")
	public List<Roles> getAllRolesByProjectId(@PathVariable Integer projectId) {
		return roleRepository.findAllByRolesIdentityProjectId(projectId);
	}
	
	@GetMapping
	public Roles getRolesByIdentity(@RequestBody RolesIdentity identity) {
		return roleRepository.findByRolesIdentity(identity);
	}

	@PostMapping
	public Roles addRole(@RequestBody Roles role) {
		return roleRepository.save(role);
	}
	
	@PutMapping
	public Roles updateTopic(@RequestBody Roles role) {
		return roleRepository.save(role);
	}
	
	@DeleteMapping("/{username}")
	public Boolean deleteTopic(@RequestBody RolesIdentity identity){
		return roleRepository.deleteByRolesIdentity(identity);
	}
}
