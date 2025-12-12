package com.taskmanager.DTO;

import com.taskmanager.enums.TaskStatus;

import java.time.LocalDate;

public record TaskResponse(
        Long id,
        String title,
        String description,
        LocalDate dueDate,
        TaskStatus status
) {}