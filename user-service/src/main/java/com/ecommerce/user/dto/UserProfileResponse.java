package com.ecommerce.user.dto;

import com.ecommerce.user.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    
    private String id;
    
    private String email;
    
    private String firstName;
    
    private String lastName;
    
    private UserRole role;
    
    private String avatarUrl;
    
    private LocalDateTime createdAt;
}
