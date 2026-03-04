package com.tread.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ThreadInfo {
    private String name;
    private String state;
}