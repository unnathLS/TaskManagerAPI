package com.taskmanager.DTO;

public record JwtResponse(
    String token,
    String type,
    String username,
    String email,
    String roles,
    long expiresIn
) {
    // Construtor de conveniência que define "Bearer" automaticamente
    public JwtResponse(String token, String username, String email, String roles) {
        this(token, "Bearer", username, email, roles, 86400000L);
    }
}
