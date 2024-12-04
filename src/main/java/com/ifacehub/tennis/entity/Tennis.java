package com.ifacehub.tennis.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class Tennis implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groups;
    private String category;
    private String subcategory;
    @Column(columnDefinition = "TEXT")
    private String imgUrl; // Image URL stored as text
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer duration;
    private Double price;
    private String status;
    private Double discount = 0.0;
    private LocalDateTime disbegindate; // Discount begin date
    private LocalDateTime disenddate; // Discount end date
    private Integer disquantity; // Quantity related to the discount
    private String phoneNumber;
//    private String address;
}
