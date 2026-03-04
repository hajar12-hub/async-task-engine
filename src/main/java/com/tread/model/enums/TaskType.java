package com.tread.model.enums;

public enum TaskType {
    EMAIL(5000), FILE_PROCESSING(20000), SCRAPING(10000);
    private final int durationMs;

    TaskType(int durationMs) {
        this.durationMs = durationMs;
    }
    public int getDurationMs(){
        return durationMs;
    }
}
