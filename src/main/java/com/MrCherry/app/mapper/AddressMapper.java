package com.MrCherry.app.mapper;

import com.MrCherry.app.dto.AddressDto;
import com.MrCherry.app.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toAddress(AddressDto addressDto);

    AddressDto toDto(Address address);

}
