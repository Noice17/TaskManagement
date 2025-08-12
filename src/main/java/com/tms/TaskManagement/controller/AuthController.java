package com.tms.TaskManagement.controller;


import com.tms.TaskManagement.dto.UserDTO;
import com.tms.TaskManagement.dto.UserUpdateDTO;
import com.tms.TaskManagement.entity.LoginRequest;
import com.tms.TaskManagement.entity.User;
import com.tms.TaskManagement.exception.custom.EmailAlreadyExistsException;
import com.tms.TaskManagement.exception.custom.InvalidCredentialsException;
import com.tms.TaskManagement.repository.UserRepository;
import com.tms.TaskManagement.service.UserService;
import com.tms.TaskManagement.util.JwtUtil;
import com.tms.TaskManagement.util.MessageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserService userService;
    private final MessageUtil messageUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, UserService userService, MessageUtil messageUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userService = userService;
        this.messageUtil = messageUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            //Auditing
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new InvalidCredentialsException(messageUtil.getMessage("error.invalid_credentials")));


            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", messageUtil.getMessage("auth.login.success"));


            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new InvalidCredentialsException(messageUtil.getMessage("error.invalid_credentials"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO){
        //check if user with email already exists
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            String errorMessage = messageUtil.getMessage("error.email.exists", userDTO.getEmail());
            throw new EmailAlreadyExistsException(errorMessage);
        }

        userDTO.setRole(User.UserRole.USER);
        UserDTO created = userService.createUser(userDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("user", created);
        response.put("message", messageUtil.getMessage("auth.register.success"));

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}
