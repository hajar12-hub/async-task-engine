package com.tread.service.Impl;

import com.tread.config.ThreadPoolConfig;
import com.tread.dto.ThreadInfo;
import com.tread.dto.ThreadPoolStatus;
import com.tread.model.enums.TaskStatus;
import com.tread.repository.TaskRepository;
import com.tread.service.MonitoringService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MonitoringServiceImpl implements MonitoringService {
    private final TaskRepository taskRepository;
    private final ThreadPoolTaskExecutor executor;

    @Override
    public ThreadPoolStatus getPoolStatus() {
        ThreadPoolExecutor pool = executor.getThreadPoolExecutor();
        return ThreadPoolStatus.builder()
                .activeThreads(pool.getActiveCount())
                .poolSize(pool.getPoolSize())
                .queueSize(pool.getQueue().size())
                .build();}

    @Override
    public List<ThreadInfo> getActiveThreads() {
        return Thread.getAllStackTraces().keySet()
                .stream()
                .filter(t -> t.getName().startsWith("AsyncTask-"))
                .map(t -> ThreadInfo.builder()
                        .name(t.getName())
                        .state(t.getState().toString())
                .build())
        .collect(Collectors.toList());
    }

    @Override
    public ThreadPoolStatus getStatistics() {
        return ThreadPoolStatus.builder()
                .completedTasks(taskRepository.countByStatus(TaskStatus.DONE))
                .pendingTasks(taskRepository.countByStatus(TaskStatus.PENDING))
                .failedTasks(taskRepository.countByStatus(TaskStatus.FAILED))
                .build();
    }
}
