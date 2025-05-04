package com.MrCherry.app.model;

import com.MrCherry.app.model.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.EnumMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;
    private int stock;
    private String description;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = false)
    private List<Image> images;
    private boolean active;
    private LocalDate createdAt;

}
