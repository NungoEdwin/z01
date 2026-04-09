package com.ecommerce.user.dto;

import com.ecommerce.user.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    
    private String token;
    
    private String userId;
    
    private String email;
    
    private String firstName;
    
    private String lastName;
    
    private UserRole role;
    
    private String avatarUrl;
}
