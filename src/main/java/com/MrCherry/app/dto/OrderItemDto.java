package com.MrCherry.app.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class OrderItemDto {
    @NotEmpty @NotNull
    private Long productId;
    @NotEmpty @NotNull
    private int quantity;
}
