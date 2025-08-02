package com.tms.TaskManagement.repository;

import com.tms.TaskManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.team.id = :teamId AND u.role = 'ADMIN'")
    Optional<User> getAdminByTeamId(@Param("teamId") Long teamId);
}
