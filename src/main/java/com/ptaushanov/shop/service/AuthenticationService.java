package com.ptaushanov.shop.service;

import com.ptaushanov.shop.dto.AuthenticationRequestDTO;
import com.ptaushanov.shop.dto.AuthenticationResponseDTO;
import com.ptaushanov.shop.dto.RegisterRequestDTO;
import com.ptaushanov.shop.model.User;
import com.ptaushanov.shop.model.UserRole;
import com.ptaushanov.shop.repository.UserRepository;
import com.ptaushanov.shop.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(UserRole.USER)
                .build();
        userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        return AuthenticationResponseDTO.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        String jwt = jwtService.generateToken(user);
        return AuthenticationResponseDTO.builder()
                .token(jwt)
                .build();
    }
}
