package com.example.farmvibe_backand_new.api.serviceImpl;

import com.example.farmvibe_backand_new.api.config.TwilioConfig;
import com.example.farmvibe_backand_new.api.service.OtpService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class OtpServiceImpl implements OtpService {


    @Autowired
    private TwilioConfig twilioConfig;

    private Map<String, String> otpStorage = new HashMap<>();
    private static final SecureRandom RANDOM = new SecureRandom();

    @PostConstruct
    public void init() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    @Override
    public boolean generateOtp(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        // Always ensure +91 prefix
        if (!phoneNumber.startsWith("+91")) {
            phoneNumber = "+91" + phoneNumber;
        }

        // Generate OTP
        String otp = String.format("%06d", RANDOM.nextInt(1_000_000));

        // Save OTP in memory (for validation later)
        otpStorage.put(phoneNumber, otp);

        // Send OTP using Twilio
        try {
            Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(twilioConfig.getPhoneNumber()),
                    "Your OTP is: " + otp
            ).create();
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP via Twilio: " + e.getMessage(), e);
        }

        return true;
    }

    @Override
    public boolean validateOtp(String phoneNumber, String otp) {
        if (phoneNumber == null || otp == null) {
            return false;
        }

        if (!phoneNumber.startsWith("+91")) {
            phoneNumber = "+91" + phoneNumber;
        }

        String storedOtp = otpStorage.get(phoneNumber);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStorage.remove(phoneNumber);  // OTP used once
            return true;
        }
        return false;
    }
}
