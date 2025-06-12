package com.scalar.blogapp.users;

import com.scalar.blogapp.security.JWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(UserDTO dto) {
        UserEntity newUser = UserEntity.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .build();
        return userRepository.save(newUser);
    }

    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity loginUser(String username, String rawPassword)
            throws UserNotFoundException, InvalidCredentialsException {

        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException(username);
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect password for user: " + username);
        }

        return user;
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String username) {
            super("User '" + username + "' not found");
        }
    }

    public static class InvalidCredentialsException extends Exception {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
}
