package com.MrCherry.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@NoArgsConstructor
@Data
public class AddressDto {

    private Long id;
    @NotEmpty @NotNull
    private String street;
    @NotEmpty @NotNull
    private String number;
    private String department;
    private String floor;
    private String zipCode;
    @NotEmpty @NotNull
    private String district;
    private String description;



}
