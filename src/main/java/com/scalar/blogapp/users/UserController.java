package com.scalar.blogapp.users;

import com.scalar.blogapp.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.net.URI;

// Assuming these DTOs and Request classes are defined elsewhere in your project
// import com.scalar.blogapp.users.dto.UserDTO;
// import com.scalar.blogapp.users.dto.LoginUserRequest;
// import com.scalar.blogapp.users.dto.UserResponseDTO;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, JWTService jwtService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDTO> signupUser(@RequestBody UserDTO request) {
        // Assuming createUser in UserService now takes UserDTO and returns UserEntity
        UserEntity savedUser = userService.createUser(request);
        URI savedUserUri = URI.create("/users/" + savedUser.getId());

        UserResponseDTO userResponse = modelMapper.map(savedUser, UserResponseDTO.class);
        // Corrected: Pass username to createJwt, as per JWTService definition
        userResponse.setToken(jwtService.createJwt(savedUser.getUsername()));

        return ResponseEntity.created(savedUserUri)
                .body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserRequest request) {
        try {
            UserEntity loggedInUser = userService.loginUser(request.getUsername(), request.getPassword());
            UserResponseDTO userResponse = modelMapper.map(loggedInUser, UserResponseDTO.class);
            // Corrected: Pass username to createJwt, as per JWTService definition
            userResponse.setToken(jwtService.createJwt(loggedInUser.getUsername()));

            return ResponseEntity.ok(userResponse);
        } catch (UserService.UserNotFoundException | UserService.InvalidCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}