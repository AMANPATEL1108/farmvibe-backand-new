package com.example.farmvibe_backand_new.api.serviceImpl;

import com.example.farmvibe_backand_new.api.dto.AddressDTO;
import com.example.farmvibe_backand_new.api.model.Address;
import com.example.farmvibe_backand_new.api.model.User;
import com.example.farmvibe_backand_new.api.repository.AddressRepository;
import com.example.farmvibe_backand_new.api.repository.UserRepository;
import com.example.farmvibe_backand_new.api.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address findAddressByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new RuntimeException("Invalid user ID");
        }
        return addressRepository.findByUserId(userId);
    }

    @Override
    public Address findAddressById(Long addressId) {
        if (addressId == null || addressId <= 0) {
            throw new RuntimeException("Invalid address ID");
        }
        return addressRepository.findById(addressId).orElse(null);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> getAllAddressesByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new RuntimeException("Invalid user ID");
        }
        return addressRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public Address createAddress(AddressDTO addressDTO) {
        // Validate required fields
        if (addressDTO.getUser_id() == null) {
            throw new RuntimeException("User ID is required");
        }

        User user = userRepository.findById(addressDTO.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + addressDTO.getUser_id()));

        Address address = new Address();
        address.setFirst_name(addressDTO.getFirst_name());
        address.setLast_name(addressDTO.getLast_name());
        address.setEmail(addressDTO.getEmail());
        address.setNumber(addressDTO.getNumber());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setArea(addressDTO.getArea());
        address.setHouse_number(addressDTO.getHouse_number());
        address.setPincode(addressDTO.getPincode());
        address.setUser(user);

        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public Address updateAddress(Long addressId, AddressDTO addressDTO) {
        if (addressId == null || addressId <= 0) {
            throw new RuntimeException("Invalid address ID");
        }

        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));

        // Update fields only if they are provided and not empty
        if (addressDTO.getFirst_name() != null && !addressDTO.getFirst_name().trim().isEmpty()) {
            existingAddress.setFirst_name(addressDTO.getFirst_name().trim());
        }
        if (addressDTO.getLast_name() != null && !addressDTO.getLast_name().trim().isEmpty()) {
            existingAddress.setLast_name(addressDTO.getLast_name().trim());
        }
        if (addressDTO.getEmail() != null && !addressDTO.getEmail().trim().isEmpty()) {
            existingAddress.setEmail(addressDTO.getEmail().trim());
        }
        if (addressDTO.getNumber() != null && !addressDTO.getNumber().trim().isEmpty()) {
            existingAddress.setNumber(addressDTO.getNumber().trim());
        }
        if (addressDTO.getStreet() != null && !addressDTO.getStreet().trim().isEmpty()) {
            existingAddress.setStreet(addressDTO.getStreet().trim());
        }
        if (addressDTO.getCity() != null && !addressDTO.getCity().trim().isEmpty()) {
            existingAddress.setCity(addressDTO.getCity().trim());
        }
        if (addressDTO.getArea() != null && !addressDTO.getArea().trim().isEmpty()) {
            existingAddress.setArea(addressDTO.getArea().trim());
        }
        if (addressDTO.getHouse_number() != null && !addressDTO.getHouse_number().trim().isEmpty()) {
            existingAddress.setHouse_number(addressDTO.getHouse_number().trim());
        }
        if (addressDTO.getPincode() != null && !addressDTO.getPincode().trim().isEmpty()) {
            existingAddress.setPincode(addressDTO.getPincode().trim());
        }

        return addressRepository.save(existingAddress);
    }

    @Override
    @Transactional
    public void deleteAddress(Long addressId) {
        if (addressId == null || addressId <= 0) {
            throw new RuntimeException("Invalid address ID");
        }

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));
        addressRepository.delete(address);
    }
}