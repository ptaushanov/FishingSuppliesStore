package com.ptaushanov.shop.controller;

import com.ptaushanov.shop.dto.user.UserRequestDTO;
import com.ptaushanov.shop.dto.user.UserResponseDTO;
import com.ptaushanov.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Page<UserResponseDTO> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sort", defaultValue = "id,asc") String sortString,
            @RequestParam(name = "filter", defaultValue = "") String filterString
    ) {
        return userService.getAllUsers(page, size, sortString, filterString);
    }

    @GetMapping(path = "/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO request) {
        return userService.createUser(request);
    }

    @PutMapping(path = "/{id}")
    public UserResponseDTO updateUserById(@PathVariable Long id, @RequestBody UserRequestDTO request) {
        return userService.updateUserById(id, request);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
