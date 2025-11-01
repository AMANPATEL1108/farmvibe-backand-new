package com.example.farmvibe_backand_new.api.service;

import com.example.farmvibe_backand_new.api.dto.AddressDTO;
import com.example.farmvibe_backand_new.api.model.Address;
import java.util.List;

public interface AddressService {
    Address findAddressByUserId(Long userId);
    Address findAddressById(Long addressId);
    List<Address> getAllAddresses();
    List<Address> getAllAddressesByUserId(Long userId);
    Address createAddress(AddressDTO addressDTO);
    Address updateAddress(Long addressId, AddressDTO addressDTO);
    void deleteAddress(Long addressId);
}