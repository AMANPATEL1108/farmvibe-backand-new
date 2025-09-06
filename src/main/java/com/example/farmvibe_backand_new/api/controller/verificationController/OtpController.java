package com.example.farmvibe_backand_new.api.controller.verificationController;

import com.example.farmvibe_backand_new.api.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam("phone") String phone) {
        boolean result = otpService.generateOtp(phone);

        if (result) {
            return ResponseEntity.ok("{\"success\": \"OTP sent successfully.\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"error\": \"Invalid phone number format.\"}");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam("phone") String phone, @RequestParam("otp") String otp) {
        boolean isValid = otpService.validateOtp(phone, otp);

        if (isValid) {
            return ResponseEntity.ok("{\"success\": true}");
        }

        return ResponseEntity.status(400).body("{\"success\": false, \"message\": \"Invalid OTP.\"}");
    }
}
