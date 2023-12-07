package com.webapp.verticalascent.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    
    @Column(nullable = false)
    private BigDecimal totalPrice;
    
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isActive;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(nullable = false, unique = true)
    private String sessionID;
    
    @OneToMany(mappedBy = "shoppingSession")
    private List<CartProduct> cartProducts;
    
    @PrePersist
    protected void onCreat() {
        Date currentDate = new Date();
        createdAt = currentDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        expirationDate = calendar.getTime();
      
    }
}
