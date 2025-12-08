package com.taskmanager.service;

import com.taskmanager.model.User;
import com.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user){
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalArgumentException("Username já existe!");
        } else if ( (user.getRoles().isEmpty()) ) {
            user.getRoles().add("ROLE_USER"); // Ou "USER", dependendo da sua convenção
        }
        String encrypted = passwordEncoder.encode(user.getPassword());
        user.setPassword(encrypted);
        return userRepository.save(user);

    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
