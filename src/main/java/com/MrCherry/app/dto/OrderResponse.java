package com.MrCherry.app.dto;

import com.MrCherry.app.model.*;
import com.MrCherry.app.model.enums.DeliveryType;
import com.MrCherry.app.model.enums.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderResponse {

    private Long id;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItems;
    private BigDecimal amount;
    private DeliveryType deliveryType;
    private ContactInformationDto contactInformation;
    private AddressDto deliveryAddress;
    private UserResponse user;
    private PaymentDto payment;

}
