package com.taskmanager.repository;

import com.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Fetch all active (non-deleted) tasks
    List<Task> findAllByIsDeletedFalse();

    // Fetch all soft-deleted tasks
    List<Task> findAllByIsDeletedTrue();
}
