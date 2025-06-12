package com.scalar.blogapp.users;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String bio;
    private String image;
    private String token;
}
