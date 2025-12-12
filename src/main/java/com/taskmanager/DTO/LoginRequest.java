package com.taskmanager.DTO;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message ="Email ou Username é obrigatório")
    String identifier,

    @NotBlank(message = "Senha é obrigatória")
    String password
) {}
