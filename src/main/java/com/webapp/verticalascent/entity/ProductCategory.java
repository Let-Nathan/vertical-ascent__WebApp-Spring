package com.webapp.verticalascent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object representation of product table.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;
    
    @Column
    private int quantity;
    
    @Column(nullable = false)
    private double price;
    
    @Column(nullable = false)
    private boolean isAvailable;
    
    @Column(nullable = false)
    private String image;
}

