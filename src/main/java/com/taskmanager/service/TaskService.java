package com.taskmanager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.DTO.TaskRequest;
import com.taskmanager.DTO.TaskResponse;
import com.taskmanager.enums.TaskStatus;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TaskResponse createTask(TaskRequest request, Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId não pode ser nulo");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User não encontrado: " + userId));

        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        task.setStatus(request.status() != null ? request.status() : TaskStatus.PENDING);
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return toResponse(savedTask);
    }

    public TaskResponse getTaskById(Long taskId, Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId não pode ser nulo");
        }
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
        .orElseThrow(() -> new RuntimeException(
                        "Tarefa não encontrada ou você não tem permissão para acessá-la"));
        return toResponse(task);
    }

    public List<TaskResponse> getAllTasks(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId não pode ser nulo");
        }

        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskResponse updateTask(Long taskId, TaskRequest request, Long userId){
        if (userId == null) {
            throw new IllegalArgumentException("userId não pode ser nulo");
        }

        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException(
                        "Tarefa não encontrada ou você não tem permissão para atualizá-la"));
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());

        if(request.status() != null) {
            task.setStatus(request.status());
        }

        Task updatedTask = taskRepository.save(task);
        return toResponse(updatedTask);
    }

    @Transactional
    public void deleteTask(Long taskId, Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId não pode ser nulo");
        }

        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException(
                        "Tarefa não encontrada ou você não tem permissão para excluí-la"));

        taskRepository.delete(task);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus());
    }
}
