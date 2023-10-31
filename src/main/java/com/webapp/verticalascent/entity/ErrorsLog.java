package com.webapp.verticalascent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

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
@Table(name = "errors_logs")
public class ErrorsLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Timestamp hours;
    
    @Column
    private String errorMessage;
    
    @Column
    private String errorType;
    
}

