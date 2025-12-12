package com.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.DTO.TaskRequest;
import com.taskmanager.DTO.TaskResponse;
import com.taskmanager.model.User;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    private Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByEmailOrUsername(username);
        return user.getId();
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request){
        Long userId = getCurrentUserId();
        TaskResponse response = taskService.createTask(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping()
    public ResponseEntity<List<TaskResponse>> getAllTasks(){
        Long userId = getCurrentUserId();
        List<TaskResponse> tasks = taskService.getAllTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id){
        Long userId = getCurrentUserId();
        TaskResponse task = taskService.getTaskById(id, userId);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,@Valid @RequestBody TaskRequest request){
        Long userId = getCurrentUserId();
        TaskResponse task = taskService.updateTask(id, request, userId);
        return ResponseEntity.ok(task);
    
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        Long userId = getCurrentUserId();
        taskService.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
     }


}
