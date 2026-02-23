package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.User;


public interface UserRepo extends JpaRepository<User,String> {
    User findByUsername(String username);
}
