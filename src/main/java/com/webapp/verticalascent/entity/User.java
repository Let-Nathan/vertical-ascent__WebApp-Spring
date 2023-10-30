package com.webapp.verticalascent.entity;

import jakarta.persistence.*;

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
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String mobilePhone;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false)
    private Timestamp inscriptionDate;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Addresses> addresses;
}

