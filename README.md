# ⚙️ AsyncTaskEngine — Concurrent Task Processing System

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?style=flat-square)
![H2](https://img.shields.io/badge/Database-H2%20%2F%20PostgreSQL-blue?style=flat-square)
![Status](https://img.shields.io/badge/Status-Active-success?style=flat-square)

A backend system for managing and executing **heavy tasks in parallel** using a custom ThreadPool, with real-time monitoring of thread activity.

---

## 🎯 Purpose

Most applications block the main thread when executing long operations (file processing, emails, scraping). AsyncTaskEngine solves this by delegating tasks to a managed thread pool, enabling:

- Non-blocking task submission
- Parallel execution with configurable concurrency
- Real-time visibility into thread and queue state

---

## 🏗️ Architecture

```
Client Request
      │
      ▼
TaskController          → REST API layer
      │
      ▼
TaskService             → Business logic (submit, find, update status)
      │
      ├──► TaskRepository      → Persistence (JPA / H2 / PostgreSQL)
      │
      └──► ExecutorService     → Async execution via ThreadPool
                │
                ▼
         ThreadPoolConfig      → 5 core threads, 10 max, 100 queue capacity
                │
                ▼
         MonitoringService     → Real-time pool & task statistics
```

---

## 🧵 Thread Pool Configuration

| Parameter | Value | Description |
|---|---|---|
| `corePoolSize` | 5 | Threads always active |
| `maxPoolSize` | 10 | Max threads under high load |
| `queueCapacity` | 100 | Tasks waiting in queue |
| `rejectionPolicy` | `CallerRunsPolicy` | Backpressure — no task loss |
| `gracefulShutdown` | 30s | Waits for running tasks on stop |

> **Why CallerRunsPolicy?** Under heavy load, instead of throwing exceptions or silently dropping tasks, the calling thread executes the task itself. This naturally slows down submission and prevents system crashes — a key scalability pattern.

---

## 📦 Task Model

```
Task {
  id             Long          Auto-generated
  type           TaskType      EMAIL | FILE_PROCESSING | SCRAPING
  priority       TaskPriority  LOW | MEDIUM | HIGH (auto-assigned by type)
  status         TaskStatus    PENDING → RUNNING → DONE | FAILED
  submittedAt    LocalDateTime When the task was received
  startedAt      LocalDateTime When a thread picked it up
  completedAt    LocalDateTime When execution finished
  threadName     String        Which thread executed it
  failureReason  String        Error message if FAILED
}
```

**Priority assignment (automatic):**
```
EMAIL           → LOW    (fast ~5s)
FILE_PROCESSING → MEDIUM (slow ~20s)
SCRAPING        → HIGH   (medium ~10s)
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+

### Run locally
```bash
git clone https://github.com/your-username/async-task-engine.git
cd async-task-engine
mvn spring-boot:run
```

The application starts on **http://localhost:8080**

H2 Console available at **http://localhost:8080/h2-console**
```
JDBC URL : jdbc:h2:mem:taskdb
Username : SA
Password : (empty)
```

---

## 📡 API Reference

### Task Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/tasks/submit` | Submit a new task |
| `GET` | `/api/tasks` | List all tasks |
| `GET` | `/api/tasks/{id}` | Get task by ID |

**Submit a task:**
```bash
curl -X POST http://localhost:8080/api/tasks/submit \
  -H "Content-Type: application/json" \
  -d '{"type": "EMAIL"}'
```

**Response:**
```json
{
  "id": 1,
  "type": "EMAIL",
  "priority": "LOW",
  "status": "RUNNING",
  "submittedAt": "2026-03-04T05:20:28",
  "startedAt": "2026-03-04T05:20:28",
  "completedAt": null,
  "threadName": "taskExecutor-1",
  "failureReason": null
}
```

---

### Monitoring Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/monitor/status` | ThreadPool live state |
| `GET` | `/api/monitor/activeThreads` | List active thread names |
| `GET` | `/api/monitor/statistics` | Task counts by status |

**Pool status response:**
```json
{
  "activeThreads": 4,
  "poolSize": 5,
  "queueSize": 7
}
```

**Statistics response:**
```json
{
  "completedTasks": 23,
  "pendingTasks": 8,
  "failedTasks": 0
}
```

---

## 🗂️ Project Structure

```
src/main/java/com/tread/
├── config/
│   └── ThreadPoolConfig.java       # ThreadPool bean configuration
├── controller/
│   ├── TaskController.java         # Task REST endpoints
│   └── MonitorController.java      # Monitoring REST endpoints
├── dto/
│   ├── TaskRequest.java            # Incoming task payload
│   ├── ThreadPoolStatus.java       # Pool/stats response DTO
│   └── ThreadInfo.java             # Active thread info DTO
├── exception/
│   └── TaskNotFoundException.java  # Custom exception
├── model/
│   ├── Task.java                   # JPA entity
│   └── enums/
│       ├── TaskType.java           # EMAIL, FILE_PROCESSING, SCRAPING
│       ├── TaskStatus.java         # PENDING, RUNNING, DONE, FAILED
│       └── TaskPriority.java       # LOW, MEDIUM, HIGH
├── repository/
│   └── TaskRepository.java         # Spring Data JPA
└── service/
    ├── TaskService.java            # Task service interface
    ├── ExecutorService.java        # Executor service interface
    ├── MonitoringService.java      # Monitoring service interface
    └── Impl/
        ├── TaskServiceImpl.java
        ├── ExecutorServiceImpl.java
        └── MonitoringServiceImpl.java
```

---

## 🔑 Key Concepts Demonstrated

| Concept | Implementation |
|---|---|
| **ThreadPool** | `ThreadPoolTaskExecutor` with custom config |
| **Async Execution** | `@Async("taskExecutor")` + `CompletableFuture` |
| **Backpressure** | `CallerRunsPolicy` prevents overload |
| **Graceful Shutdown** | `setWaitForTasksToCompleteOnShutdown(true)` |
| **Thread Safety** | `AtomicInteger`, status transitions |
| **Live Monitoring** | `Thread.getAllStackTraces()` + pool introspection |

---

## 🛣️ Roadmap

- [ ] Priority-based queue (`PriorityBlockingQueue`)
- [ ] Task retry mechanism on failure
- [ ] Redis cache for task results
- [ ] Spring Security with JWT
- [ ] Actuator + Micrometer metrics
- [ ] Docker support

---

## 👤 Author

Built as part of a hands-on **Backend & Scalable Systems** learning path.

> *"Don't just learn threads — build systems that prove you understand them."*