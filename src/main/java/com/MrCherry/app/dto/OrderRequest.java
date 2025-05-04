package com.MrCherry.app.dto;

import com.MrCherry.app.model.enums.DeliveryType;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;

import java.util.List;
@NoArgsConstructor
@Data
public class OrderRequest {

    @NotNull
    private List<OrderItemDto> orderItems;
    @NotNull
    private DeliveryType deliveryType;
    private ContactInformationDto contactInformation;
    private AddressDto deliveryAddress;
    private UserResponse user;

}
