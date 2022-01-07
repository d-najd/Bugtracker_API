package com.bugtracker.db.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.db.roles.Roles;
import com.bugtracker.db.roles.RolesRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@GetMapping
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/{username}")
	public User getUserById(@PathVariable String username){
		return userRepository.getById(username);
	}

	@PostMapping
	public User addUser(@RequestBody User user) {
		Roles r = rolesRepository.findById(user.getUsername()).get();
		//user.setRoles(r);
		return userRepository.save(user);
	}
	
	@PutMapping
	public void editUser(@RequestBody User user) {
		userRepository.save(user);
	}
	
	@DeleteMapping("/{username}")
	public void deleteUser(@PathVariable String username){
		userRepository.deleteById(username);
		rolesRepository.deleteById(username);
	}
}
