package com.tread.service;

import com.tread.model.Task;
import com.tread.model.enums.TaskStatus;

import java.util.List;
import java.util.Optional;


public interface TaskService {

    public Task submitTask(Task task);
    public Task findById(Long taskId);
    public List<Task> findAll();
    public Task updateStatus(Long taskId, TaskStatus status);

}
