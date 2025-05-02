package com.MrCherry.app.dto;

import com.MrCherry.app.model.enums.ProductCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
@NoArgsConstructor
@Data
public class ProductDto {

    private Long id;
    @NotEmpty @NotBlank
    private String name;
    @NotEmpty @NotBlank
    private BigDecimal price;
    @NotNull
    private ProductCategory productCategory;
    private int stock;
    private String description;
    private List<String> images;
    private boolean active;

}
