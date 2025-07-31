package com.tms.TaskManagement.repository;

import com.tms.TaskManagement.entity.Task;
import com.tms.TaskManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository <Task, Long> {
    Optional<Task> findByUser(User user);
    boolean existsById(Long id);
}
