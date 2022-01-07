package com.bugtracker.db.roles;

import java.util.List;
import java.util.Optional;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RolesController {

	@Autowired
	private RolesRepository roleRepository;
	
	@GetMapping
	public List<Roles> getAllRoles(){
		return roleRepository.findAll();
	}
	
	@GetMapping("/{username}")
	public Optional<Roles> getRolesByUsername(@PathVariable String username) {
		return roleRepository.findById(username);
	} 

	@PostMapping
	public void addRole(@RequestBody Roles role) {
		roleRepository.save(role);
	}
	
	@PutMapping
	public void updateTopic(@RequestBody Roles role) {
		roleRepository.save(role);
	}
	
	@DeleteMapping("/{username}")
	public void deleteTopic(@PathVariable String username){
		roleRepository.deleteById(username);
	}
}
