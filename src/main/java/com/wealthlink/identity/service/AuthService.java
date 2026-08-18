package com.wealthlink.identity.service;

import com.wealthlink.identity.dto.AppUserDto;
import com.wealthlink.identity.dto.LoginRequest;
import com.wealthlink.identity.dto.LoginResponse;
import com.wealthlink.identity.entity.AppUser;
import com.wealthlink.identity.repository.AppUserRepository;
import com.wealthlink.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AppUserRepository appUserRepository;

    public LoginResponse login(LoginRequest request) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Generate JWT token
        String jwtToken = jwtUtil.generateToken(request.getUsername());

        // Fetch user details for the response
        AppUser appUser = appUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        AppUserDto userDto = AppUserDto.builder()
                .id(appUser.getId())
                .username(appUser.getUsername())
                .email(appUser.getEmail())
                .status(appUser.getStatus().name())
                .createdAt(appUser.getCreatedAt())
                .build();

        // Build the LoginResponse
        return LoginResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(null) // Mocked or implement refresh token logic later
                .tokenType("Bearer")
                .expiresIn(3600)
                .user(userDto)
                .build();
    }
}
