package com.example.farmvibe_backand_new.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long address_id;
    private String first_name;
    private String last_name;
    private String email;
    private String number;
    private String street;
    private String city;
    private String area;
    private String house_number;
    private String pincode;
    private Long user_id;
}