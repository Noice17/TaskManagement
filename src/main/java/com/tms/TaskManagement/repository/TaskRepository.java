package com.tms.TaskManagement.repository;

import com.tms.TaskManagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository <Task, Long> {
    boolean existsById(Long id);
}
