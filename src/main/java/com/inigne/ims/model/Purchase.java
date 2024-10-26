package com.inigne.ims.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pid;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    private Double amount;

    @NotNull
    private Integer quantityPurchased;

    @NotNull
    private Date date;

    @NotNull
    private String supplierName;

    @Transient
    private Double totalAmount;

    @PostLoad
    @PostPersist
    @PostUpdate
    public void calculateTotalAmount() {
        this.totalAmount = this.amount * this.quantityPurchased;
    }
}