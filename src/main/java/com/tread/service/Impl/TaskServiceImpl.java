package com.tread.service.Impl;


import com.tread.exception.TaskNotFoundException;
import com.tread.model.Task;
import com.tread.model.enums.TaskPriority;
import com.tread.model.enums.TaskStatus;
import com.tread.repository.TaskRepository;
import com.tread.service.ExecutorService;
import com.tread.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ExecutorService executorService;


    @Override
    public Task submitTask(Task task) {
        task.setStatus(TaskStatus.PENDING);
        task.setSubmittedAt(LocalDateTime.now());
        switch (task.getType()){
            case EMAIL:
                task.setPriority(TaskPriority.LOW);
                break;
            case FILE_PROCESSING:
                task.setPriority(TaskPriority.MEDIUM);
                break;
            case SCRAPING:
                task.setPriority(TaskPriority.HIGH);
        }
        Task saved = taskRepository.save(task);
        executorService.executeTask(saved);
        return saved;
    }

    @Override
    public Task findById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(()->  new TaskNotFoundException("Task with id " + taskId + " not found"));
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task updateStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " not found"));
        task.setStatus(status);
        taskRepository.save(task);
        return task;
    }
}
