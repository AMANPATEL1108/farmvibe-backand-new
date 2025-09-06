package com.example.farmvibe_backand_new.api.service;

import com.example.farmvibe_backand_new.api.dto.LoginDTO;
import com.example.farmvibe_backand_new.api.dto.RegisterDTO;
import com.example.farmvibe_backand_new.api.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


public interface UserAuthService {
     ResponseEntity<?> createUser(RegisterDTO registerDTO);
    ResponseEntity<Optional<User>> findByUsername(String username);
    ResponseEntity<?> authenticateAndGenerateToken(LoginDTO request);
}
