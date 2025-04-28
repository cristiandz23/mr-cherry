package com.MrCherry.app.model;

import com.MrCherry.app.model.enums.ProductCategory;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private int stock;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

}
