package com.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.DTO.JwtResponse;
import com.taskmanager.DTO.LoginRequest;
import com.taskmanager.DTO.RegisterRequest;
import com.taskmanager.model.User;
import com.taskmanager.service.UserService;
import com.taskmanager.util.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        User user = new User(request.username(), request.email(), request.password());
        userService.registerUser(user);
        return ResponseEntity.ok("Usuário registrado com sucesso: " + request.email());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());

        String jwt = jwtUtil.generateToken(userDetails);

        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .get()
                .getAuthority()
                .replace("ROLE_", "");

        JwtResponse response = new JwtResponse(jwt, userDetails.getUsername(), request.email(), role);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/api/auth/teste-logado")
    public String testeLogado() {
        return "DEU CERTO PORRA! Você está autenticado como: " +
                SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
