package com.tread.repository;

import com.tread.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tread.model.enums.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {
    int countByStatus(TaskStatus status);

}
