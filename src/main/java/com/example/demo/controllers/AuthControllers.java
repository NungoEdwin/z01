package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Product;
import com.example.demo.security.JwtUtil;
import com.example.demo.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllers {
    @Autowired
    ProductService productService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtService;

    // public AuthController(AuthenticationManager authManager, JwtUtil jwtService) {
    //     this.authManager = authManager;
    //     this.jwtService = jwtService;
    // }
     @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(), request.password())
        );

        return jwtService.generateToken((UserDetails) auth.getPrincipal());
    }

    @PostMapping("/products")
    public void CreateProduct(@RequestBody Product entity){
     productService.createProduct(entity);
    }
    @PutMapping("/products/{id}")
    public Product UpdateProduct(@RequestBody Product entity,@PathVariable String id){
        return productService.update(entity, id);
    }
    @DeleteMapping("/products/{id}")
    public void DeleteProduct(@PathVariable String id){
         productService.deleteProduct(id);
    }
    @GetMapping("/products/{id}")
    public Product GetProduct(@PathVariable String id){
        return productService.getProduct(id);
    }
    
    public record AuthRequest(
            String username,
            String password
    ) {}
}

