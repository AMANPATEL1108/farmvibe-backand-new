package com.example.farmvibe_backand_new.api.controller.authController;


import com.example.farmvibe_backand_new.api.dto.request.AuthRequest;
import com.example.farmvibe_backand_new.api.model.User;
import com.example.farmvibe_backand_new.api.repository.UserRepository;
import com.example.farmvibe_backand_new.api.service.JwtService;
import com.example.farmvibe_backand_new.api.serviceImpl.JwtServiceImplementation;
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
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setUser_password(passwordEncoder.encode(user.getUser_password()));
        if (!user.getUser_role().startsWith("ROLE_")) {
            user.setUser_role("ROLE_" + user.getUser_role());
        }
        return ResponseEntity.ok(userRepository.save(user));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        System.out.println("Called  1");
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        User user = userRepository.findByUsername(request.username()).orElseThrow();
        System.out.println(request.username()+" "+request.password());
        return ResponseEntity.ok(Map.of("token", jwtService.generateToken(user)));
    }

}
