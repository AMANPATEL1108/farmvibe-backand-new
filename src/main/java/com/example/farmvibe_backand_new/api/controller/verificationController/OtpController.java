package com.example.farmvibe_backand_new.api.controller.verificationController;

import com.example.farmvibe_backand_new.api.dto.OtpVerificationRequest;
import com.example.farmvibe_backand_new.api.dto.SendOtpRequest;
import com.example.farmvibe_backand_new.api.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, Object>> sendOtp(@RequestBody SendOtpRequest request) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(request.getUsername());
        boolean result = otpService.generateOtp(request.getUsername());

        if (result) {
            response.put("success", true);
            response.put("message", "OTP sent successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid username.");
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody OtpVerificationRequest request) {
        Map<String, Object> response = new HashMap<>();
        boolean isValid = otpService.validateOtp(request.getUsername(), request.getOtp());

        if (isValid) {
            response.put("success", true);
            response.put("message", "OTP verified successfully.");
            return ResponseEntity.ok(response);
        }else{
            response.put("success", false);
            response.put("message", "Invalid OTP.");
            return ResponseEntity.status(400).body(response);
        }

    }

}