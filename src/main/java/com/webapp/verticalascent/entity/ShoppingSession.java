package com.webapp.verticalascent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Object representation of shopping session table.
 * Used to store user cart product, perform order.
 *
 * @author Nathan L
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shopping_session")
public class ShoppingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expirationDate;
    
    @Column(nullable = false)
    private BigDecimal totalPrice;
    
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isActive;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "shopping_session")
    private List<CartProduct> cartProducts;
}

