package com.tread.service;

import com.tread.dto.ThreadInfo;
import com.tread.dto.ThreadPoolStatus;

import java.util.List;

public interface MonitoringService {
    ThreadPoolStatus getPoolStatus();     // activeThreads + poolSize + queueSize
    List<ThreadInfo> getActiveThreads();  // liste des threads actifs
    ThreadPoolStatus getStatistics();     // completedTasks + pendingTasks + failedTasks
}
