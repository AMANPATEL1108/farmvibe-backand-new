package com.example.farmvibe_backand_new.api.service;

import com.example.farmvibe_backand_new.api.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserServices {

    Optional<User> findByUsername(String username);
    User updateUserDetails(User user);
}
