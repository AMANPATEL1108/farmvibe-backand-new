package com.example.farmvibe_backand_new.api.serviceImpl;

import com.example.farmvibe_backand_new.api.model.User;
import com.example.farmvibe_backand_new.api.repository.UserRepository;
import com.example.farmvibe_backand_new.api.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    
    public User updateUserDetails(User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        User existingUser = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getUser_firstName() != null) {
            existingUser.setUser_firstName(user.getUser_firstName());
        }

        if (user.getUser_lastName() != null) {
            existingUser.setUser_lastName(user.getUser_lastName());
        }

        if (user.getUser_email() != null) {
            existingUser.setUser_email(user.getUser_email());
        }

        if (user.getProfileImageUrl() != null) {
            existingUser.setProfileImageUrl(user.getProfileImageUrl());
        }

        existingUser.setUpdatedDate(ZonedDateTime.now(ZoneId.of("UTC")));
        return userRepository.save(existingUser);
    }

}
