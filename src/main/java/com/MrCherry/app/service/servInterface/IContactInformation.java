package com.MrCherry.app.service.servInterface;


import com.MrCherry.app.model.ContactInformation;
import org.springframework.stereotype.Repository;


public interface IContactInformation  {

    ContactInformation findByEntity(ContactInformation contactInformation);

}
