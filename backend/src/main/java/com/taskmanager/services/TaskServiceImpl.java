package com.taskmanager.services;

import com.taskmanager.entity.*;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.services.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Task getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));
        if (task.isDeleted()) {
            throw new IllegalArgumentException("Task with ID " + id + " is deleted.");
        }
        return task;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask) {
        Task task = getTaskById(id);
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setExpectedStartDateTime(updatedTask.getExpectedStartDateTime());
        task.setExpectedEndDateTime(updatedTask.getExpectedEndDateTime());
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        task.setDeleted(true); // Soft delete
        taskRepository.save(task);
    }

    @Override
    public List<Task> getAllDeletedTasks() {
        return taskRepository.findAllByIsDeletedTrue();
    }
}
