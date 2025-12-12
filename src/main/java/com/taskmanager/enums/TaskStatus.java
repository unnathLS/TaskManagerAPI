package com.taskmanager.enums;

public enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED;

    // Método utilitário para converter String para Enum (case-insensitive)
    public static TaskStatus fromString(String status) {
        if (status == null) {
            return PENDING;
        }
        try {
            return TaskStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
    }
}