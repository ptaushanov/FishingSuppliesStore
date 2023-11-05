package com.ptaushanov.shop.service;

import com.ptaushanov.shop.dto.user.UserRequestDTO;
import com.ptaushanov.shop.dto.user.UserResponseDTO;
import com.ptaushanov.shop.model.User;
import com.ptaushanov.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Secured("ROLE_ADMIN")
    public UserResponseDTO createUser(UserRequestDTO request) {
        // Encode password and set it to the request
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        // Map the request to a User object and save it to the database
        User user = modelMapper.map(request, User.class);
        return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
    }
}
