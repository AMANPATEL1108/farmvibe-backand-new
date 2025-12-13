package com.example.farmvibe_backand_new.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long userId;
    private String username;
    private String email;
    // Add other user fields as needed
}
