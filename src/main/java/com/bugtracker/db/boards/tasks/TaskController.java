package com.bugtracker.db.boards.tasks;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
	 	@Autowired
	    TaskRepository taskRepository;
	   
	    @GetMapping("/tasks/all")
	    public List<Task> getAllTasks() {
	        return taskRepository.findAll();
	    }
	    
	    @ResponseBody
	    @GetMapping("/tasks/all/{userid}")
	    public List<Task> getAllTasksByUID(@PathVariable Integer userid) {
	        return taskRepository.findAllByUserId(userid);
	    }
	    
	    @ResponseBody
	    @GetMapping("/tasks/{userid}/{id}")
	    public Optional<Task> getTaskByFieldIdAndUID(@PathVariable("userid") Integer userid, @PathVariable("id") Integer id){
	    	return taskRepository.findByIdAndUserId(id, userid);
	    }

	    //NOTE everyone will be able to update whichever field they choose, needs to check if the
	    //userid is authenticated and if he is updating his field id and not the field id of someone else
	    @ResponseBody
	    @PutMapping("/tasks")
	    public Task editTask(@RequestBody Task task){
	    	return taskRepository.save(task);
	    }
	    
	    @ResponseBody
	    @PostMapping("/tasks")
	    public Task addTask(@RequestBody Task task){
	    	return taskRepository.save(task);
	    }
	    
	    @ResponseBody
	    @DeleteMapping("/tasks/{fieldid}")
	    public Task removeTask(@PathVariable Integer fieldid) {
	    	taskRepository.deleteById(fieldid);
	    	return new Task();
	    }

	    @ResponseBody
	    @GetMapping("/tasks/{fieldid}")
	    public Optional<Task> getTaskById(@PathVariable Integer id) {
	    	return taskRepository.findById(id);
	    }
}
