package com.example.farmvibe_backand_new.api.serviceImpl;

import com.example.farmvibe_backand_new.api.dto.request.AuthRequest;
import com.example.farmvibe_backand_new.api.model.User;
import com.example.farmvibe_backand_new.api.repository.UserRepository;
import com.example.farmvibe_backand_new.api.service.UserAuthService;
import com.example.farmvibe_backand_new.api.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public ResponseEntity<?> createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (!user.getRole().startsWith("ROLE_")) {
            user.setRole("ROLE_" + user.getRole());
        }
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<?> authenticateAndGenerateToken(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        Optional<User> userOptional = userRepository.findByUsername(request.username());

        if (userOptional.isPresent()) {
            String token = jwtService.generateToken(userOptional.get());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @Override
    public ResponseEntity<Optional<User>> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return ResponseEntity.ok(user);
    }
}
