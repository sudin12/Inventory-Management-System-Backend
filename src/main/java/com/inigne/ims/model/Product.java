package com.inigne.ims.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @PositiveOrZero
    private Double price;

    @PositiveOrZero
    private Integer stockQuantity;

    @PositiveOrZero
    private Integer reorderLevel;

}
