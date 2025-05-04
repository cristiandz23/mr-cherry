package com.MrCherry.app.dto;

import com.MrCherry.app.model.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
public class OrderResponse {

    private Long id;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItems;
    private ContactInformationDto contactInformation;
    private AddressDto deliveryAddress;
    private UserResponse user;
    private PaymentRequest payment;


}
