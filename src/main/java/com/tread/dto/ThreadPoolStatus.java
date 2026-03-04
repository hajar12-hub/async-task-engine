package com.tread.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThreadPoolStatus {
    private int activeThreads;
    private int poolSize;
    private int queueSize;

    // infos de la DB
    private long completedTasks;
    private long pendingTasks;
    private long failedTasks;
}
