package com.example.demo.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.services.UserService;

public class UserController {
    List<User> users = new ArrayList<User>();
        private final UserService service;
        
        public UserController(UserService service ) {
            this.service = service;
        }
        
        @GetMapping("/users")
        public List<User> getUser() {
            return service.all();
        }

        @PostMapping("users")
        public ResponseEntity<?>  postMethodName(@RequestBody User entity) {
            if (entity == null) {
                return ResponseEntity.badRequest().body("Invalid input: 'entity' cannot be null.");
            }

            User user = service.save(entity);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user.");
            }
            return ResponseEntity.ok("User added successfully");
        } 
}
