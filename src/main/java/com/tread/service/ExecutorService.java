package com.tread.service;

import com.tread.model.Task;

import java.util.concurrent.CompletableFuture;

public interface ExecutorService {
    CompletableFuture<Task> executeTask(Task task);
}
