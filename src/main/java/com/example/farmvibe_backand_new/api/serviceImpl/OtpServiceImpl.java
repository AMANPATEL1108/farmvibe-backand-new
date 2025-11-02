package com.example.farmvibe_backand_new.api.serviceImpl;

import com.example.farmvibe_backand_new.api.config.TwilioConfig;
import com.example.farmvibe_backand_new.api.service.OtpService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class OtpServiceImpl implements OtpService {

    private static final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);

    @Autowired
    private TwilioConfig twilioConfig;

    private Map<String, OtpData> otpStorage = new HashMap<>();
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final long OTP_EXPIRY_MINUTES = 5;

    @PostConstruct
    public void init() {
        try {
            Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
            logger.info("Twilio initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize Twilio: {}", e.getMessage());
            throw new RuntimeException("Twilio initialization failed", e);
        }
    }

    @Override
    public boolean generateOtp(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            logger.error("Phone number is null or empty");
            return false;
        }

        try {
            // Clean and format phone number
            String cleanedNumber = phoneNumber.trim().replaceAll("\\s+", "");

            // Ensure +91 prefix
            if (!cleanedNumber.startsWith("+91")) {
                if (cleanedNumber.startsWith("91") && cleanedNumber.length() == 12) {
                    cleanedNumber = "+" + cleanedNumber;
                } else if (cleanedNumber.startsWith("0")) {
                    cleanedNumber = "+91" + cleanedNumber.substring(1);
                } else if (cleanedNumber.length() == 10) {
                    cleanedNumber = "+91" + cleanedNumber;
                } else {
                    logger.error("Invalid phone number format: {}", phoneNumber);
                    return false;
                }
            }

            // Validate phone number length
            if (cleanedNumber.length() != 13) { // +91 followed by 10 digits
                logger.error("Invalid phone number length: {}", cleanedNumber);
                return false;
            }

            // Generate OTP
            String otp = String.format("%06d", RANDOM.nextInt(1_000_000));
            long expiryTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(OTP_EXPIRY_MINUTES);

            // Store OTP with expiry
            otpStorage.put(cleanedNumber, new OtpData(otp, expiryTime));

            logger.info("Generated OTP for {}: {}", cleanedNumber, otp);

            // For development/testing, log OTP instead of sending SMS
            if (isDevelopmentEnvironment()) {
                logger.info("DEV MODE: OTP for {} is: {}", cleanedNumber, otp);
                return true;
            }

            // Send OTP using Twilio
            try {
                Message message = Message.creator(
                        new PhoneNumber(cleanedNumber),
                        new PhoneNumber(twilioConfig.getPhoneNumber()),
                        "Your FarmVibe verification code is: " + otp + ". Valid for " + OTP_EXPIRY_MINUTES + " minutes."
                ).create();

                logger.info("OTP sent successfully to {} with SID: {}", cleanedNumber, message.getSid());
                return true;

            } catch (Exception e) {
                logger.error("Twilio SMS sending failed for {}: {}", cleanedNumber, e.getMessage());
                // Fallback: log OTP for development
                logger.info("FALLBACK: OTP for {} is: {}", cleanedNumber, otp);
                return true; // Return true even if SMS fails (for development)
            }

        } catch (Exception e) {
            logger.error("Error generating OTP for {}: {}", phoneNumber, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean validateOtp(String phoneNumber, String otp) {
        if (phoneNumber == null || otp == null || otp.trim().isEmpty()) {
            return false;
        }

        try {
            // Clean and format phone number same way as generation
            String cleanedNumber = phoneNumber.trim().replaceAll("\\s+", "");
            if (!cleanedNumber.startsWith("+91")) {
                if (cleanedNumber.startsWith("91") && cleanedNumber.length() == 12) {
                    cleanedNumber = "+" + cleanedNumber;
                } else if (cleanedNumber.startsWith("0")) {
                    cleanedNumber = "+91" + cleanedNumber.substring(1);
                } else if (cleanedNumber.length() == 10) {
                    cleanedNumber = "+91" + cleanedNumber;
                } else {
                    return false;
                }
            }

            OtpData otpData = otpStorage.get(cleanedNumber);
            if (otpData == null) {
                logger.warn("No OTP found for number: {}", cleanedNumber);
                return false;
            }

            // Check if OTP is expired
            if (System.currentTimeMillis() > otpData.expiryTime) {
                otpStorage.remove(cleanedNumber);
                logger.warn("OTP expired for number: {}", cleanedNumber);
                return false;
            }

            // Validate OTP
            if (otpData.otp.equals(otp.trim())) {
                otpStorage.remove(cleanedNumber); // OTP used once
                logger.info("OTP validated successfully for: {}", cleanedNumber);
                return true;
            } else {
                logger.warn("Invalid OTP for number: {}", cleanedNumber);
                return false;
            }

        } catch (Exception e) {
            logger.error("Error validating OTP for {}: {}", phoneNumber, e.getMessage());
            return false;
        }
    }

    private boolean isDevelopmentEnvironment() {
        // Check if we're in development mode (no valid Twilio credentials)
        return twilioConfig.getAccountSid() == null ||
                twilioConfig.getAccountSid().startsWith("your_") ||
                twilioConfig.getAuthToken() == null ||
                twilioConfig.getAuthToken().startsWith("your_");
    }

    // Helper class to store OTP with expiry
    private static class OtpData {
        String otp;
        long expiryTime;

        OtpData(String otp, long expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }
}