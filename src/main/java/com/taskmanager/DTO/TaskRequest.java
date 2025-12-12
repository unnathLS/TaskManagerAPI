package com.taskmanager.DTO;

import com.taskmanager.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record TaskRequest(
        @NotBlank(message = "Título é obrigatório")
        String title,

        String description,

        LocalDate dueDate,

        TaskStatus status
) {}