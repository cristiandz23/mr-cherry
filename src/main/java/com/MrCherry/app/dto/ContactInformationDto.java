package com.MrCherry.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ContactInformationDto {
    @NotEmpty @NotNull
    private String name;
    private String lastName;
    @NotEmpty @NotNull
    private String phone;
    private List<AddressDto> address;

}
