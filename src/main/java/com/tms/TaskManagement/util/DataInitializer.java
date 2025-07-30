package com.tms.TaskManagement.util;

import com.tms.TaskManagement.entity.Team;
import com.tms.TaskManagement.entity.User;
import com.tms.TaskManagement.repository.TeamRepository;
import com.tms.TaskManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, TeamRepository teamRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initData(){
        return args ->{
            if(teamRepository.count() == 0 && userRepository.count() == 0) {
                //Create Team
                Team adminTeam = new Team();
                adminTeam.setName("Admin Office");
                teamRepository.save(adminTeam);

                //Create User
                User admin = new User();
                admin.setUsername("Admin");
                admin.setEmail("admin@email.com");
                admin.setPassword(passwordEncoder.encode("password"));
                admin.setRole(User.UserRole.ADMIN);
                admin.setTeam(adminTeam);
                admin.setCreatedAt(LocalDateTime.now());
                admin.setUpdatedAt(LocalDateTime.now());
                userRepository.save(admin);

                User user = new User();
                user.setUsername("User");
                user.setEmail("user@email.com");
                user.setPassword(passwordEncoder.encode("password"));
                user.setRole(User.UserRole.USER);
                user.setTeam(adminTeam);
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());
                userRepository.save(user);
            }
        };
    }
}
