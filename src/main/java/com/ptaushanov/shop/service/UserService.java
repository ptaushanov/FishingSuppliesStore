package com.ptaushanov.shop.service;

import com.ptaushanov.shop.dto.user.UserRequestDTO;
import com.ptaushanov.shop.dto.user.UserResponseDTO;
import com.ptaushanov.shop.model.User;
import com.ptaushanov.shop.repository.UserRepository;
import com.ptaushanov.shop.util.FilterSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.ptaushanov.shop.util.PageableHelpers.createPageable;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Page<UserResponseDTO> getAllUsers(int page, int size, String sortString, String filterString) {
        Pageable pageable = createPageable(page, size, sortString);
        Specification<User> specification = FilterSpecification.filterQuery(filterString);

        Page<User> userPage = userRepository.findAll(specification, pageable);
        return userPage.map(user -> modelMapper.map(user, UserResponseDTO.class));
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User with id " + id + " does not exist")
        );
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public UserResponseDTO createUser(UserRequestDTO request) {
        // Encode password and set it to the request
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        // Map the request to a User object and save it to the database
        User user = modelMapper.map(request, User.class);
        return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
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
        user.setRole(request.getRole());
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
