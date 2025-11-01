
package com.example.farmvibe_backand_new.api.controller.userController;

import com.example.farmvibe_backand_new.api.dto.AddressDTO;
import com.example.farmvibe_backand_new.api.model.Address;
import com.example.farmvibe_backand_new.api.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user/address-details")
public class AddressController {

    @Autowired
    private AddressService addressService;

    private AddressDTO toDto(Address address) {
        if (address == null) return null;
        AddressDTO dto = new AddressDTO();
        BeanUtils.copyProperties(address, dto);
        if (address.getUser() != null) {
            // assume AddressDTO has setUserId(Long id)
            try {
                dto.getClass().getMethod("setUserId", Long.class)
                        .invoke(dto, address.getUser().getUser_id());
            } catch (Exception ignored) { }
        }
        return dto;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAddresses() {
        try {
            List<Address> addresses = addressService.getAllAddresses();
            List<AddressDTO> dtos = addresses.stream().map(this::toDto).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch addresses");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable Long addressId) {
        try {
            Address address = addressService.findAddressById(addressId);
            if (address != null) {
                return ResponseEntity.ok(toDto(address));
            }
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Address not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch address");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllAddressesByUserId(@PathVariable Long userId) {
        try {
            List<Address> addresses = addressService.getAllAddressesByUserId(userId);
            List<AddressDTO> dtos = addresses.stream().map(this::toDto).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch addresses");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAddress(@RequestBody AddressDTO addressDTO) {
        try {
            Address address = addressService.createAddress(addressDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Address created successfully");
            response.put("data", toDto(address));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to create address");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/update/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
        try {
            Address updatedAddress = addressService.updateAddress(addressId, addressDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Address updated successfully");
            response.put("data", toDto(updatedAddress));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to update address");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        try {
            addressService.deleteAddress(addressId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Address deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to delete address");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
