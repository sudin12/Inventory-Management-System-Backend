package com.inigne.ims.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tid;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Product product;

    @NotNull
    private Integer quantitySold;
    private Double totalAmount;
    private Double tax;
    private Double discount;
    private Date date;

    // Add the name field
    @NotNull
    private String name;
}