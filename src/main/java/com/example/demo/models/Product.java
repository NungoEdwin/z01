package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;
    private String description;
    private Double price;
    private String userId;


}
