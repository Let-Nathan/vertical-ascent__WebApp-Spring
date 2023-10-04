package com.webapp.verticalascent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Todo --> Add relation into product
 * Object representation of product table.
 *
 * @author Nathan L
 * @version 1.0
 *
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
    
    @Column(nullable = false)
    private double price;
    
    @Column(nullable = false)
    private boolean isAvailable;
    
}
