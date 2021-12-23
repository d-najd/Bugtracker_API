package com.bugtracker.db.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
@RequestMapping("/users")
public class UsersController {
	
    @Autowired
    UsersRepository usersRepository;
    
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }
    
    //syntax localhost:8080/hi?name=nameofthing (without "")
    @ResponseBody
    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Integer id) {
    	return usersRepository.findById(id);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
    	usersRepository.save(user);
    }

    @PutMapping
    public void editUser(@RequestBody User user) {
    	usersRepository.save(user);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeUser(@PathVariable Integer id) throws SQLException {
    	usersRepository.deleteById(id);
    	return ResponseEntity.ok("ok");
    }
}