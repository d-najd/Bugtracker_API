package com.bugtracker.db.user;

import java.util.List;
import java.util.Optional;

import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	
	@GetMapping
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/{username}")
	public User getUserById(@PathVariable String username){
		return userRepository.getById(username);
	}

	@PostMapping
	public ResponseEntity<String> addUser(@RequestBody User user) {
		Optional<User> dbUser = userRepository.findById(user.getUsername());
				
		if (dbUser != null)
			return new ResponseEntity<String>("A user with the username already exists", HttpStatus.BAD_REQUEST);
		
		if (user.getPassword().length() <= 3 || user.getUsername().length() <= 3) 
			return new ResponseEntity<String>("Usernames or passwords shorter than 3 characters are not allowed", HttpStatus.BAD_REQUEST);
		
		user.setActive(true);
		//userRepository.save(user);
		return new ResponseEntity<String>("Something doesn't work properly, trying to save user with username that doesn't exist says that it exists", HttpStatus.NOT_IMPLEMENTED);
		//return ResponseEntity.ok("ok");
	}
	
	@PutMapping
	public ResponseEntity<String> editUser(
			@AuthenticationPrincipal MyUserDetails userDetails) {
		
		return new ResponseEntity<String>("this feature is currently unavaible", HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@DeleteMapping("/{username}")
	public String deleteUser(@PathVariable String username){
		return "disabled";
		//DISABLED
		//userRepository.deleteById(username);
		//rolesRepository.deleteById(username);
	}
}
