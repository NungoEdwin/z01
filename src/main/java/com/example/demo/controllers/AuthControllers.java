package com.example.demo.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.controllers.Controllers.Product;

public class AuthControllers {
    @PostMapping("/products")
    public Product CreateProduct(){
    return new Product();
    }
    @PutMapping("/products/{id}")
    public Product UpdateProduct(){
        return new Product();
    }
    @DeleteMapping("/products/{id}")
    public Product DeleteProduct(){
        return new Product();
    }
    @GetMapping
    public Product GetUsers(){
        return new Product();
    }
    
}
