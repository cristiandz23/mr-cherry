package com.MrCherry.app.service;

import com.MrCherry.app.model.Address;
import com.MrCherry.app.repository.AddressRepository;
import com.MrCherry.app.service.servInterface.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address findByEntity(Address address) {
        return addressRepository.findBy(Example.of(address), q -> q.first()).orElse(null);
    }
}
