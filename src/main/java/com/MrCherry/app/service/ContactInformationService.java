package com.MrCherry.app.service;

import com.MrCherry.app.model.ContactInformation;
import com.MrCherry.app.repository.ContactInformationRepository;
import com.MrCherry.app.service.servInterface.IContactInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ContactInformationService implements IContactInformation {
    @Autowired
    private ContactInformationRepository contactInformationRepository;

    @Override
    public ContactInformation findByEntity(ContactInformation contactInformation) {

        return contactInformationRepository.findBy(Example.of(contactInformation), q -> q.first()).orElse(null);
    }
}
