package com.webapp.verticalascent.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object representation of Cart product table.
 * Used to store product information and link it to the user's session.
 *
 * @author Nathan L
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart_product")
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    
    @Column
    @Temporal(TemporalType.DATE)
    private Date modifiedAt;
    
    @Column(nullable = false)
    private BigDecimal totalPrice;
    
    @Column(nullable = false)
    private int quantity;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "session_id")
    private ShoppingSession shoppingSession;
    
    @PrePersist
    protected void onCreat() {
        createdAt = new Date();
    }
}

