package com.example.farmvibe_backand_new.api.controller.verificationController;

import com.example.farmvibe_backand_new.api.dto.TokenVerificationRequest;
import com.example.farmvibe_backand_new.api.dto.TokenVerificationResponse;
import com.example.farmvibe_backand_new.api.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class TokenVerificationController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/verify-token")
    public ResponseEntity<TokenVerificationResponse> verifyToken(@RequestBody TokenVerificationRequest request) {
        try {
            String token = request.getToken();

            if (token == null || token.isBlank()) {
                return ResponseEntity.badRequest()
                        .body(new TokenVerificationResponse(false, "Token is missing", null));
            }

            String username = jwtService.extractUsername(token);

            User userDetails = new User(username, "", new ArrayList<>());
            boolean isValid = jwtService.isTokenValid(token, userDetails);

            if (isValid) {
                return ResponseEntity.ok(
                        new TokenVerificationResponse(true, "Token is valid", username)
                );
            } else {
                return ResponseEntity.status(401)
                        .body(new TokenVerificationResponse(false, "Invalid token", null));
            }

        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(new TokenVerificationResponse(false, "Token verification failed: " + e.getMessage(), null));
        }
    }
}
