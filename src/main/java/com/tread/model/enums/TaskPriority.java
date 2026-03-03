package com.tread.model.enums;

public enum TaskPriority {
    LOW(3),
    HIGH(1),
    MEDIUM(2);

    private final int value;

    TaskPriority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
