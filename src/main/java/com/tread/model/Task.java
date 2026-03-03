package com.tread.model;

import com.fasterxml.jackson.annotation.JsonTypeId;
import com.tread.model.enums.TaskPriority;
import com.tread.model.enums.TaskStatus;
import com.tread.model.enums.TaskType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private String threadName;
    private String failureReason;
}

