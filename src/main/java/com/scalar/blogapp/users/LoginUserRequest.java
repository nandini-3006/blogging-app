package com.scalar.blogapp.users;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@Getter
public class LoginUserRequest {
    private String username;
    private String email;
    private String password;
}
