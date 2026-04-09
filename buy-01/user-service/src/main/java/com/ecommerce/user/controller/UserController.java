package com.ecommerce.user.controller;

import com.ecommerce.user.dto.UpdateProfileRequest;
import com.ecommerce.user.dto.UserProfileResponse;
import com.ecommerce.user.model.UserRole;
import com.ecommerce.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileResponse> getProfile(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        UserProfileResponse profile = userService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileResponse> updateProfile(
            HttpServletRequest request,
            @Valid @RequestBody UpdateProfileRequest updateRequest
    ) {
        String userId = (String) request.getAttribute("userId");
        UserProfileResponse profile = userService.updateProfile(userId, updateRequest);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/avatar")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Map<String, String>> uploadAvatar(
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String userId = (String) request.getAttribute("userId");
        String userRole = (String) request.getAttribute("userRole");

        if (!UserRole.SELLER.name().equals(userRole)) {
            return ResponseEntity.status(403).body(Map.of("error", "Only sellers can upload avatars"));
        }

        String avatarUrl = userService.uploadAvatar(userId, file);
        return ResponseEntity.ok(Map.of("avatarUrl", avatarUrl));
    }
}
