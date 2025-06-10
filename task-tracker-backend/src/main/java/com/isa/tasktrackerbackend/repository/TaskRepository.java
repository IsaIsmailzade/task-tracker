package com.isa.tasktrackerbackend.repository;

import com.isa.tasktrackerbackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTasksByUser_Id(Long userId);
}
