package com.MrCherry.app.dto;

import com.MrCherry.app.model.Image;
import com.MrCherry.app.model.enums.ProductCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

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
