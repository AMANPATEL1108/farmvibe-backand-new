package com.example.farmvibe_backand_new.api.controller.authController;


import com.example.farmvibe_backand_new.api.dto.request.AuthRequest;
import com.example.farmvibe_backand_new.api.model.User;
import com.example.farmvibe_backand_new.api.repository.UserRepository;
import com.example.farmvibe_backand_new.api.service.JwtService;
import com.example.farmvibe_backand_new.api.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired private UserAuthService userAuthService;
    @Autowired private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.ok(userAuthService.createUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        return userAuthService.authenticateAndGenerateToken(request);
    }

}
