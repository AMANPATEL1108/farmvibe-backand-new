package com.example.farmvibe_backand_new.api.serviceImpl;

import com.example.farmvibe_backand_new.api.dto.LoginDTO;
import com.example.farmvibe_backand_new.api.dto.RegisterDTO;
import com.example.farmvibe_backand_new.api.model.User;
import com.example.farmvibe_backand_new.api.repository.UserRepository;
import com.example.farmvibe_backand_new.api.service.UserAuthService;
import com.example.farmvibe_backand_new.api.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> createUser(RegisterDTO registerDTO) {

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setUser_firstName(registerDTO.getUser_firstName());
        user.setUser_lastName(registerDTO.getUser_lastName());
        user.setUser_email(registerDTO.getUser_email());
        user.setProfileImageUrl(registerDTO.getProfileImageUrl());
        user.setCreatedDate(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
        user.setUpdatedDate(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));


        user.setUser_password(passwordEncoder.encode(registerDTO.getUser_password()));

        if (registerDTO.getRole() == null) {
            user.setRole("ROLE_USER");
        } else {
            if (!registerDTO.getRole().startsWith("ROLE_")) {
                user.setRole("ROLE_" + registerDTO.getRole());
            } else {
                user.setRole(registerDTO.getRole());
            }
        }

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }



    public ResponseEntity<?> authenticateAndGenerateToken(LoginDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            // Only generate token if authentication succeeds
            User user = userRepository.findByUsername(request.username())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(Map.of("token", token));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication failed");
        }
    }



    @Override
    public ResponseEntity<Optional<User>> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return ResponseEntity.ok(user);
    }
}
