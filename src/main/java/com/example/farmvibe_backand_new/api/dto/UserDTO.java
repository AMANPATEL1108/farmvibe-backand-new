package com.example.farmvibe_backand_new.api.dto;

import com.example.farmvibe_backand_new.api.model.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String username;
    private String email;

    public UserDTO(User user) {
        this.userId = user.getUser_id();
        this.username = user.getUsername();
        this.email = user.getUser_email();
    }
}
