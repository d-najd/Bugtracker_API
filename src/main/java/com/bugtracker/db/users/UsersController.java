package com.bugtracker.db.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
	
    @Autowired
    UsersRepository usersRepository;
    
    @GetMapping("/users/all")
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }
    
    //syntax localhost:8080/hi?name=nameofthing (without "")
    @ResponseBody
    @GetMapping("/users")
    public Optional<User> getUser(@RequestParam Integer id) {
    	return usersRepository.findById(id);
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
    	usersRepository.save(user);
    }

    @PutMapping("/users")
    public void editUser(@RequestBody User user) {
    	usersRepository.save(user);
    }
    
    @ResponseBody
    @DeleteMapping("/users")
    public void removeUser(@RequestParam Integer id) {
    	usersRepository.deleteById(id);
    }
}