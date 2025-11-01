package com.example.farmvibe_backand_new.api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "user_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long address_id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    private String email;

    private String number;

    private String street;

    private String city;

    private String area;

    @Column(name = "house_number")
    private String house_number;

    private String pincode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_date", updatable = false)
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = ZonedDateTime.now();
        this.updatedDate = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = ZonedDateTime.now();
    }
}