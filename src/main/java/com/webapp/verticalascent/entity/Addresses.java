package com.webapp.verticalascent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object representation of addresses table.
 *
 * @author Nathan L
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Addresses {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private Boolean isBillingAddresses;
    
    @Column(nullable = false)
    private Boolean isDefaultAddresses;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

