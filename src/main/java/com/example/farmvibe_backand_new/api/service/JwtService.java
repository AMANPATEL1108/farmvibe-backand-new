package com.example.farmvibe_backand_new.api.service;

import com.example.farmvibe_backand_new.api.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(User user);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
