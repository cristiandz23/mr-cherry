package com.MrCherry.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
@NoArgsConstructor
@Data
public class ContactInformationDto {
    @NotEmpty @NotNull
    private String name;
    private String lastName;
    @NotEmpty @NotNull
    private String phone;
    private List<AddressDto> address;

}
