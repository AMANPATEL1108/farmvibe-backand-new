package com.example.farmvibe_backand_new.api.controller.authController;


import com.example.farmvibe_backand_new.api.dto.LoginDTO;
import com.example.farmvibe_backand_new.api.dto.RegisterDTO;
import com.example.farmvibe_backand_new.api.model.User;
import com.example.farmvibe_backand_new.api.service.JwtService;
import com.example.farmvibe_backand_new.api.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired private UserAuthService userAuthService;
    @Autowired private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO register) {
        return ResponseEntity.ok(userAuthService.createUser(register));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
        return userAuthService.authenticateAndGenerateToken(request);
    }

}
