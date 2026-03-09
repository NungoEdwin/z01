package com.ecommerce.user.service;

import com.ecommerce.user.dto.*;
import com.ecommerce.user.exception.InvalidCredentialsException;
import com.ecommerce.user.exception.UserAlreadyExistsException;
import com.ecommerce.user.kafka.UserEventProducer;
import com.ecommerce.user.model.User;
import com.ecommerce.user.model.UserRole;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserEventProducer userEventProducer;

    @InjectMocks
    private UserService userService;

    private RegisterRequest registerRequest;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .email("test@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .role(UserRole.CLIENT)
                .build();

        user = User.builder()
                .id("userId123")
                .email("test@example.com")
                .password("encodedPassword")
                .firstName("John")
                .lastName("Doe")
                .role(UserRole.CLIENT)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void register_Success() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtUtil.generateToken(anyString(), anyString(), anyString())).thenReturn("token123");

        AuthResponse response = userService.register(registerRequest);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
        assertEquals(user.getEmail(), response.getEmail());
        verify(userRepository).save(any(User.class));
        verify(userEventProducer).publishUserRegisteredEvent(any(User.class));
    }

    @Test
    void register_UserAlreadyExists_ThrowsException() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.register(registerRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_Success() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.generateToken(anyString(), anyString(), anyString())).thenReturn("token123");

        AuthResponse response = userService.login(loginRequest);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
        assertEquals(user.getEmail(), response.getEmail());
    }

    @Test
    void login_InvalidPassword_ThrowsException() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@example.com")
                .password("wrongPassword")
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> userService.login(loginRequest));
    }

    @Test
    void getProfile_Success() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        UserProfileResponse response = userService.getProfile("userId123");

        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getEmail(), response.getEmail());
    }

    @Test
    void updateProfile_Success() {
        UpdateProfileRequest updateRequest = UpdateProfileRequest.builder()
                .firstName("Jane")
                .lastName("Smith")
                .build();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserProfileResponse response = userService.updateProfile("userId123", updateRequest);

        assertNotNull(response);
        verify(userRepository).save(any(User.class));
        verify(userEventProducer).publishUserUpdatedEvent(any(User.class));
    }
}
