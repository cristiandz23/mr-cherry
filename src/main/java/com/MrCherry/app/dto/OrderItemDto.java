package com.MrCherry.app.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderItemDto {
    @NotEmpty @NotNull
    private Long productId;
    @NotEmpty @NotNull
    private int quantity;

}
