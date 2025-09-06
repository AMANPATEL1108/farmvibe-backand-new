package com.example.farmvibe_backand_new.api.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "user_details")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "mobile_number", unique = true)
    private String username;


    @Column(name = "user_firstname")
    private String user_firstName;

    @Column(name = "user_lastname")
    private String user_lastName;


    @Column(name = "user_email")
    private String user_email;

    @Column(name = "user_password")
    private String user_password;


    @Column(name = "profile_image_url")
    private String profileImageUrl;


    @Column(name = "role")
    private String role;


    @PrePersist
    protected void onCreate() {
        if (this.role == null) {
            this.role = "USER";
        }
    }

    public String getFullName() {
        return user_firstName + " " + user_lastName;
    }

}
