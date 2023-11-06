package com.webapp.verticalascent.entity;

import jakarta.persistence.CascadeType;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Object representation of user table.
 *
 * @author Nathan L
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String mobilePhone;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(nullable = false)
    private Timestamp inscriptionDate;
    
    @Column(nullable = false)
    private Boolean isActive = false;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Addresses> addresses;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}

