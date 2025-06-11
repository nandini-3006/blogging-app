package com.scalar.blogapp.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDTO> signupUser(@RequestBody UserDTO request) {
        UserEntity savedUser = userService.createUser(request);
        URI savedUserUri = URI.create("/users/" + savedUser.getId());

        return ResponseEntity.created(savedUserUri)
                .body(modelMapper.map(savedUser, UserResponseDTO.class));
    }

    @PostMapping("/login")
    ResponseEntity<UserResponseDTO> loginUser(@RequestBody LoginUserRequest request) throws UserService.UserNotFoundException {
        UserEntity savedUser = userService.loginUser(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(modelMapper.map(savedUser, UserResponseDTO.class));
    }
}

