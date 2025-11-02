// LocationService.java
package com.example.farmvibe_backand_new.api.service;

import com.example.farmvibe_backand_new.api.dto.LocationDTO;
import java.util.List;

public interface LocationService {

    List<LocationDTO> getAllLocations();

    LocationDTO getLocationById(Long locationId);

    LocationDTO getLocationByCity(String city);

    List<String> getAllCities();

    List<String> getAreasByCity(String city);

    LocationDTO createLocation(LocationDTO locationDTO);

    LocationDTO updateLocation(Long locationId, LocationDTO locationDTO);

    void deleteLocation(Long locationId);

    void initializeDefaultLocations();
}