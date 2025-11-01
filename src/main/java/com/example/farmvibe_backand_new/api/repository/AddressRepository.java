package com.example.farmvibe_backand_new.api.repository;

import com.example.farmvibe_backand_new.api.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.user.user_id = :userId")
    Address findByUserId(Long userId);

    @Query("SELECT a FROM Address a WHERE a.user.user_id = :userId")
    List<Address> findAllByUserId(Long userId);
}