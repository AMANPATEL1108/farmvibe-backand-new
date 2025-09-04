package com.example.farmvibe_backand_new.api.service;

import com.example.farmvibe_backand_new.api.dto.request.AuthRequest;
import com.example.farmvibe_backand_new.api.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


public interface UserAuthService {
    Object createUser(User user);
    ResponseEntity<Optional<User>> findByUsername(String username);
    ResponseEntity<?> authenticateAndGenerateToken(AuthRequest request);
}
