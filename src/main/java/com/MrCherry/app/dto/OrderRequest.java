package com.MrCherry.app.dto;

import com.MrCherry.app.model.enums.DeliveryType;


import javax.validation.constraints.NotNull;

import java.util.List;

public class OrderRequest {

    @NotNull
    private List<OrderItemDto> orderItems;
    @NotNull
    private DeliveryType deliveryType;
    private ContactInformationDto contactInformation;
    @NotNull
    private AddressDto deliveryAddress;
    private UserResponse user;

}
