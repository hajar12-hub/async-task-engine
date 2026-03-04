package com.tread.service.Impl;

import com.tread.model.Task;
import com.tread.model.enums.TaskStatus;
import com.tread.repository.TaskRepository;
import com.tread.service.ExecutorService;
import com.tread.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class ExecutorServiceImpl implements ExecutorService {
    private final TaskRepository taskRepository;

    @Async("taskExecutor")
    @Override
    public CompletableFuture<Task> executeTask(Task task) {
        task.setStatus(TaskStatus.RUNNING);
        task.setThreadName(Thread.currentThread().getName());
        task.setStartedAt(LocalDateTime.now());
        try {
            Thread.sleep(task.getType().getDurationMs());
            task.setStatus(TaskStatus.DONE);
            task.setCompletedAt(LocalDateTime.now());
        } catch (InterruptedException e) {
            task.setStatus(TaskStatus.FAILED);
            task.setFailureReason(e.getMessage());
            Thread.currentThread().interrupt();
        }
        taskRepository.save(task);
        return CompletableFuture.completedFuture(task);

}}
