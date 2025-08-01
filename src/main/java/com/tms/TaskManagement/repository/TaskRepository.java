package com.tms.TaskManagement.repository;

import com.tms.TaskManagement.dto.TaskDTO;
import com.tms.TaskManagement.entity.Task;
import com.tms.TaskManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository <Task, Long> {
    Optional<Task> findByUser(User user);
    boolean existsById(Long id);
    List<Task> findByUserId(Long userId);
    List<Task> findByStatusAndUserId(String status, Long userId);
    List<Task> findByUser_Team_Id(Long id);
    @Query("SELECT t FROM Task t WHERE t.dueDate < CURRENT_DATE AND t.status <> :completed")
    List<Task> getOverdueTasks(@Param("completed") Task.TaskStatus completed);
    List<Task> findByUserIdAndTaskType(Long userId, Task.TaskType taskType);
}
