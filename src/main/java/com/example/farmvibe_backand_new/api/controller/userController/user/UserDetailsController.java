package com.example.farmvibe_backand_new.api.controller.userController.user;

import com.example.farmvibe_backand_new.api.model.User;
import com.example.farmvibe_backand_new.api.service.JwtService;
import com.example.farmvibe_backand_new.api.service.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserDetailsController {


    @Autowired
    private UserServices userServices;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/profile-details")
    public ResponseEntity<User> getProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = authHeader.substring(7);

        String username = jwtService.extractUsername(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return userServices.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update-profile-details")
    public User updateUser(@RequestBody User user) {
        return userServices.updateUserDetails(user);
    }


}
