package com.MrCherry.app.dto;

import com.MrCherry.app.model.enums.ProductCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
@NoArgsConstructor
@Data
public class ProductDto {

    private Long id;
    @NotEmpty @NotBlank
    private String name;
    @NotEmpty @NotBlank  @Positive(message = "El precio no debe ser menor que 0")
    private BigDecimal price;
    @NotNull
    private ProductCategory productCategory;
    @Positive(message = "El STOCK debe ser MAYOR QUE CERO")
    private int stock;
    private String description;
    private List<ImageDto> images;
    private boolean active;

}
