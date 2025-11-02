// LocationServiceImpl.java
package com.example.farmvibe_backand_new.api.serviceImpl;

import com.example.farmvibe_backand_new.api.dto.LocationDTO;
import com.example.farmvibe_backand_new.api.model.Location;
import com.example.farmvibe_backand_new.api.repository.LocationRepository;
import com.example.farmvibe_backand_new.api.service.LocationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<LocationDTO> getAllLocations() {
        try {
            List<Location> locations = locationRepository.findAllByOrderByCityAsc();
            if (locations.isEmpty()) {
                initializeDefaultLocations();
                locations = locationRepository.findAllByOrderByCityAsc();
            }
            return locations.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch locations: " + e.getMessage(), e);
        }
    }

    @Override
    public LocationDTO getLocationById(Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + locationId));
        return convertToDTO(location);
    }

    @Override
    public LocationDTO getLocationByCity(String city) {
        Location location = locationRepository.findByCity(city)
                .orElseThrow(() -> new RuntimeException("Location not found for city: " + city));
        return convertToDTO(location);
    }

    @Override
    public List<String> getAllCities() {
        List<Location> locations = locationRepository.findAllByOrderByCityAsc();
        if (locations.isEmpty()) {
            initializeDefaultLocations();
            locations = locationRepository.findAllByOrderByCityAsc();
        }
        return locations.stream()
                .map(Location::getCity)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAreasByCity(String city) {
        Location location = locationRepository.findByCity(city)
                .orElseThrow(() -> new RuntimeException("City not found: " + city));

        try {
            return objectMapper.readValue(location.getAreas(), new TypeReference<List<String>>() {});
        } catch (Exception e) {
            // Fallback to comma-separated parsing
            return Arrays.asList(location.getAreas().split("\\s*,\\s*"));
        }
    }

    @Override
    @Transactional
    public LocationDTO createLocation(LocationDTO locationDTO) {
        if (locationRepository.existsByCity(locationDTO.getCity())) {
            throw new RuntimeException("City already exists: " + locationDTO.getCity());
        }

        try {
            Location location = new Location();
            location.setCity(locationDTO.getCity());
            location.setAreas(objectMapper.writeValueAsString(locationDTO.getAreas()));

            Location savedLocation = locationRepository.save(location);
            return convertToDTO(savedLocation);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create location: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public LocationDTO updateLocation(Long locationId, LocationDTO locationDTO) {
        Location existingLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + locationId));

        try {
            existingLocation.setCity(locationDTO.getCity());
            existingLocation.setAreas(objectMapper.writeValueAsString(locationDTO.getAreas()));

            Location updatedLocation = locationRepository.save(existingLocation);
            return convertToDTO(updatedLocation);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update location: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deleteLocation(Long locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new RuntimeException("Location not found with id: " + locationId);
        }
        locationRepository.deleteById(locationId);
    }

    @Override
    @Transactional
    public void initializeDefaultLocations() {
        if (locationRepository.count() == 0) {
            List<Location> defaultLocations = Arrays.asList(
                    createLocationEntity("Ahmedabad", Arrays.asList("Maninagar", "Satellite", "Bopal", "Navrangpura", "Vastrapur", "SG Highway")),
                    createLocationEntity("Surat", Arrays.asList("Adajan", "Vesu", "Varachha", "Athwa", "Piplod", "City Light")),
                    createLocationEntity("Vadodara", Arrays.asList("Alkapuri", "Gotri", "Fatehgunj", "Akota", "Maneja", "Waghodia")),
                    createLocationEntity("Mumbai", Arrays.asList("Andheri", "Bandra", "Dadar", "Powai", "Juhu", "Colaba")),
                    createLocationEntity("Delhi", Arrays.asList("Connaught Place", "Karol Bagh", "Dwarka", "Rohini", "Pitampura", "Saket"))
            );

            locationRepository.saveAll(defaultLocations);
        }
    }

    private Location createLocationEntity(String city, List<String> areas) {
        Location location = new Location();
        location.setCity(city);
        try {
            location.setAreas(objectMapper.writeValueAsString(areas));
        } catch (Exception e) {
            // Fallback to comma-separated
            location.setAreas(String.join(",", areas));
        }
        return location;
    }

    private LocationDTO convertToDTO(Location location) {
        try {
            List<String> areas = objectMapper.readValue(location.getAreas(), new TypeReference<List<String>>() {});
            return new LocationDTO(location.getLocationId(), location.getCity(), areas);
        } catch (Exception e) {
            // Fallback to comma-separated parsing
            List<String> areas = Arrays.asList(location.getAreas().split("\\s*,\\s*"));
            return new LocationDTO(location.getLocationId(), location.getCity(), areas);
        }
    }
}