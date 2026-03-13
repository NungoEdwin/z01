package com.ecommerce.user.controller;

import com.ecommerce.user.dto.UserProfileDTO;
import com.ecommerce.user.model.User;
import com.ecommerce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @GetMapping("/profile/buyer/{userId}")
    public ResponseEntity<UserProfileDTO> getBuyerProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getBuyerProfile(userId));
    }

    @GetMapping("/profile/seller/{userId}")
    public ResponseEntity<UserProfileDTO> getSellerProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getSellerProfile(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findByEmail(""));
    }
}
