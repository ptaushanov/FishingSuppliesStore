package com.ptaushanov.shop.service;

import com.ptaushanov.shop.dto.AuthenticationRequestDTO;
import com.ptaushanov.shop.dto.AuthenticationResponseDTO;
import com.ptaushanov.shop.dto.RegisterRequestDTO;
import com.ptaushanov.shop.mapper.RegistrationMapper;
import com.ptaushanov.shop.model.User;
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

    private final RegistrationMapper registrationMapper;

    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        // Encode password and set it to the request
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        // Map the request to a User object and save it to the database
        User user = registrationMapper.mapToUser(request);
        userRepository.save(user);

        // Generate a JWT token and return it
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
