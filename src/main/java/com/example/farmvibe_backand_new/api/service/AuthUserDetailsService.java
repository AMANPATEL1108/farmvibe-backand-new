package com.example.farmvibe_backand_new.api.service;
import org.springframework.security.core.userdetails.UserDetails;
public interface AuthUserDetailsService {
    UserDetails loadUserByUsername(String username);
}