package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Product;
import com.example.demo.models.User;

public interface UserRepo extends JpaRepository<Product,String> {
    Optional<User> findByUsername(String username);
}
