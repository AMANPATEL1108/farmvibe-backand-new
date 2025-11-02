// LocationRepository.java
package com.example.farmvibe_backand_new.api.repository;

import com.example.farmvibe_backand_new.api.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByCity(String city);

    List<Location> findAllByOrderByCityAsc();

    boolean existsByCity(String city);

    @Query("SELECT l FROM Location l WHERE LOWER(l.city) LIKE LOWER(CONCAT('%', :cityName, '%'))")
    List<Location> findByCityContainingIgnoreCase(String cityName);
}