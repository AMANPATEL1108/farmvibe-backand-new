package com.example.farmvibe_backand_new.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String username;
    private String user_firstName;
    private String user_lastName;
    private String user_email;
    private String user_password;
    private String profileImageUrl;
    private String role;
}
