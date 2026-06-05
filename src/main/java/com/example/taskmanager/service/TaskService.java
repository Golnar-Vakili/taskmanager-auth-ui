package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllByUser(User user) {
        return taskRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public List<Task> findByUserAndStatus(User user, Task.Status status) {
        return taskRepository.findByUserAndStatusOrderByCreatedAtDesc(user, status);
    }

    public Task findByIdAndUser(Long id, User user) {
        return taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Task nicht gefunden oder kein Zugriff."));
    }

    @Transactional
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public Task update(Long id, User user, Task updatedTask) {
        Task task = findByIdAndUser(id, user);
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setPriority(updatedTask.getPriority());
        task.setDueDate(updatedTask.getDueDate());
        return taskRepository.save(task);
    }

    @Transactional
    public void delete(Long id, User user) {
        Task task = findByIdAndUser(id, user);
        taskRepository.delete(task);
    }
}
