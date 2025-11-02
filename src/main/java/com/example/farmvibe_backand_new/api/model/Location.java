// Location.java
package com.example.farmvibe_backand_new.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(name = "city", nullable = false, unique = true)
    private String city;

    @Column(name = "areas", columnDefinition = "TEXT")
    private String areas; // Store as JSON string or comma-separated

    // Constructor without ID for creating new locations
    public Location(String city, String areas) {
        this.city = city;
        this.areas = areas;
    }
}