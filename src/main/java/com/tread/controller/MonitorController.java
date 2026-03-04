package com.tread.controller;

import com.tread.dto.ThreadInfo;
import com.tread.dto.ThreadPoolStatus;
import com.tread.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/monitor")
@RequiredArgsConstructor
public class MonitorController {
    private final MonitoringService monitoringService;

    @GetMapping("/status")
    public ResponseEntity<ThreadPoolStatus> getPoolStatus(){
        return ResponseEntity.ok(monitoringService.getPoolStatus());
    }
    @GetMapping("/activeThreads")
    public ResponseEntity<List<ThreadInfo>> getActiveThreads(){
        return ResponseEntity.ok(monitoringService.getActiveThreads());
    }
    @GetMapping("/statistics")
    public ResponseEntity<ThreadPoolStatus> getStatistics(){
        return ResponseEntity.ok(monitoringService.getStatistics());
    }
}
