package com.MrCherry.app.mapper;

import com.MrCherry.app.dto.ContactInformationDto;
import com.MrCherry.app.model.ContactInformation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactInformationMapper {

    ContactInformation toContactInformation(ContactInformationDto contactInformationDto);

    ContactInformationDto toDto(ContactInformation contactInformation);

}
