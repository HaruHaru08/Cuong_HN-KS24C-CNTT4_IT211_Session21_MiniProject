package com.example.miniprj.service;

import com.example.miniprj.dto.AuthRequest;
import com.example.miniprj.dto.AuthResponse;
import com.example.miniprj.dto.RegisterRequest;
import com.example.miniprj.model.TokenBlacklist;
import com.example.miniprj.model.User;
import com.example.miniprj.repository.TokenBlacklistRepository;
import com.example.miniprj.repository.UserRepository;
import com.example.miniprj.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(request.getRole())
                .active(true)
                .build();
        userRepository.save(user);
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        log.info("User registered successfully: {}", user.getUsername());
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        log.info("User authenticated successfully: {}", user.getUsername());
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            TokenBlacklist blacklist = TokenBlacklist.builder()
                    .token(jwt)
                    .expiryTime(LocalDateTime.now().plusDays(1)) // Simple expiry for blacklist
                    .build();
            tokenBlacklistRepository.save(blacklist);
            log.info("Token revoked successfully");
        }
    }

    public AuthResponse refreshToken(String refreshToken) {
        final String username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                return AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }
        throw new RuntimeException("Invalid Refresh Token");
    }
}
