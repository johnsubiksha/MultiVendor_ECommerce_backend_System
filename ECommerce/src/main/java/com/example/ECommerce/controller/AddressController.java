package com.example.ECommerce.controller;

import com.example.ECommerce.Model.Address;
import com.example.ECommerce.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public Address addAddress(@RequestBody Address address,
                              Authentication authentication) {

        address.setCustomerId(authentication.getName());

        return addressRepository.save(address);
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<Address> getAddresses(Authentication authentication) {

        return addressRepository.findByCustomerId(authentication.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable String id) {

        addressRepository.deleteById(id);
    }
}
