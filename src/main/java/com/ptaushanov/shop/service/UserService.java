package com.ptaushanov.shop.service;

import com.ptaushanov.shop.dto.user.UserRequestDTO;
import com.ptaushanov.shop.dto.user.UserResponseDTO;
import com.ptaushanov.shop.model.User;
import com.ptaushanov.shop.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserResponseDTO createUser(UserRequestDTO request) {
        // Encode password and set it to the request
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        // Map the request to a User object and save it to the database
        User user = modelMapper.map(request, User.class);
        return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User with id " + id + " does not exist")
        );
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Transactional
    public UserResponseDTO updateUserById(Long id, UserRequestDTO request) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User with id " + id + " does not exist")
        );
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
