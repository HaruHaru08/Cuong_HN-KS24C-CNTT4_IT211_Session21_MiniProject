package com.example.miniprj.service;

import com.example.miniprj.dto.AuthRequest;
import com.example.miniprj.dto.AuthResponse;
import com.example.miniprj.dto.RegisterRequest;
import com.example.miniprj.model.Role;
import com.example.miniprj.model.User;
import com.example.miniprj.repository.TokenBlacklistRepository;
import com.example.miniprj.repository.UserRepository;
import com.example.miniprj.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenBlacklistRepository tokenBlacklistRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_Success() {
        RegisterRequest request = RegisterRequest.builder()
                .username("testuser")
                .password("password")
                .fullName("Test User")
                .role(Role.PATIENT)
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(jwtService.generateToken(any())).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any())).thenReturn("refreshToken");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAuthenticate_Success() {
        AuthRequest request = new AuthRequest("testuser", "password");
        User user = User.builder().username("testuser").build();

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");

        AuthResponse response = authService.authenticate(request);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        verify(authenticationManager).authenticate(any());
    }
}
