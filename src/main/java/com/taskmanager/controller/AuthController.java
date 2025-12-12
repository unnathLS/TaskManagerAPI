package com.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import java.util.List;
import java.util.stream.Collectors;

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
                new UsernamePasswordAuthenticationToken(request.identifier(), request.password()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        // O principal agora é o nosso objeto User que implementa UserDetails
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String jwt = jwtUtil.generateToken(userDetails); // O subject do token será o email

        // Como UserDetails.getUsername() agora retorna o email, podemos usá-lo
        // diretamente.
        // Para pegar o username original, precisaríamos de um cast ou um método
        // customizado.
        User user = userService.findByEmailOrUsername(userDetails.getUsername());

        JwtResponse response = new JwtResponse(
                jwt,
                user.getUsername(),
                user.getEmail(),
                roles);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/teste-logado")
    public String testeLogado() {
        return "Você está autenticado como: " + SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
