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

import com.bugtracker.boards.tasks.join.BoardTaskJoin;

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

	@PostMapping("/{roles}")
	public void addUser(@RequestBody User user, @PathVariable String roles) {
		Roles r = rolesRepository.findOneByUsername("new");
		
		System.out.print("\n\n username is " + r.getUsername() + "\n\n");
		user.setRoles(r);
		userRepository.save(user);
	}
	
	@PutMapping("/{username}")
	public void editUser(@RequestBody User user) {
		if (user.getRoles() == null)
		{
			System.out.print("\n\ntrying to save user without roles, doesn't make much sense does it?\n\n");
			return;
		}
		userRepository.save(user);
	}
	
	@DeleteMapping("/{username}")
	public void deleteUser(@PathVariable String username){
		userRepository.deleteById(username);
		rolesRepository.deleteById(username);
	}
}
