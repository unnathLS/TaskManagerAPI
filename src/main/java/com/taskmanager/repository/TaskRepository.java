package com.taskmanager.repository;

import com.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Lista todas as tarefas de um usuário específico
    List<Task> findByUserId(Long userId);

    // Busca uma tarefa específica garantindo que pertence ao usuário
    Optional<Task> findByIdAndUserId(Long taskId, Long userId);
}