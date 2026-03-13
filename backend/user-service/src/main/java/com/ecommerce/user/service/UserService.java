package com.ecommerce.user.service;

import com.ecommerce.user.dto.UserProfileDTO;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RestTemplate restTemplate;

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserProfileDTO getBuyerProfile(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Map<String, Object> stats = restTemplate.getForObject(
            "http://localhost:8083/api/orders/buyer-stats/" + userId, Map.class);
        
        UserProfileDTO profile = new UserProfileDTO();
        profile.setUserId(userId);
        profile.setEmail(user.getEmail());
        profile.setFirstName(user.getFirstName());
        profile.setLastName(user.getLastName());
        profile.setRole(user.getRole().name());
        
        if (stats != null) {
            profile.setTotalSpent((Double) stats.get("totalSpent"));
            profile.setBestProducts((java.util.List) stats.get("bestProducts"));
            profile.setMostBuyingProducts((java.util.List) stats.get("mostBuyingProducts"));
        }
        
        return profile;
    }

    public UserProfileDTO getSellerProfile(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Map<String, Object> stats = restTemplate.getForObject(
            "http://localhost:8083/api/orders/seller-stats/" + userId, Map.class);
        
        UserProfileDTO profile = new UserProfileDTO();
        profile.setUserId(userId);
        profile.setEmail(user.getEmail());
        profile.setFirstName(user.getFirstName());
        profile.setLastName(user.getLastName());
        profile.setRole(user.getRole().name());
        
        if (stats != null) {
            profile.setTotalEarned((Double) stats.get("totalEarned"));
            profile.setBestSellingProducts((java.util.List) stats.get("bestSellingProducts"));
        }
        
        return profile;
    }
}
