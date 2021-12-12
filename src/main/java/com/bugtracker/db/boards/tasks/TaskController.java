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

import com.bugtracker.db.boards.Board;
import com.bugtracker.db.boards.BoardRepository;
import com.kriscfoster.school.subject.Subject;
import com.kriscfoster.school.teacher.Teacher;

@RestController
public class TaskController {
	 	@Autowired
	    TaskRepository taskRepository;
	   
		@Autowired
		BoardRepository boardRepository;
	 	
	    @GetMapping("/tasks/all")
	    public List<Task> getAllTasks() {
	        return taskRepository.findAll();
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
	    public Task removeTask(@PathVariable Integer id) {
	    	taskRepository.deleteById(id);
	    	return new Task();
	    }

	    @ResponseBody
	    @GetMapping("/tasks/{fieldid}")
	    public Optional<Task> getTaskById(@PathVariable Integer id) {
	    	return taskRepository.findById(id);
	    }
	    
	    @PutMapping("/tasks/{subjectId}/board/{teacherId}")
	    Task assignTeacherToSubject(
	            @PathVariable Integer subjectId,
	            @PathVariable Integer teacherId
	    ) {
	        Task subject = taskRepository.findById(subjectId).get();
	        Board teacher = boardRepository.findById(teacherId).get();
	        subject.setBoard(teacher);
	        return taskRepository.save(subject);
	    }
}
