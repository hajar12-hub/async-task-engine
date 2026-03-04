package com.tread.controller;


import com.tread.dto.TaskRequest;
import com.tread.model.Task;
import com.tread.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/submit")
    public ResponseEntity<Task> submitTask(@RequestBody TaskRequest request){
        Task task = new Task();
        task.setType(request.getType());
        return ResponseEntity.ok(taskService.submitTask(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable long id){
        return ResponseEntity.ok(taskService.findById(id));
    }
    @GetMapping
    public ResponseEntity<List<Task>> findAll(){
        return ResponseEntity.ok(taskService.findAll());
    }
}
