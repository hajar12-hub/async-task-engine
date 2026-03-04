package com.tread.dto;


import com.tread.model.enums.TaskPriority;
import com.tread.model.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
        private TaskType type;
}
