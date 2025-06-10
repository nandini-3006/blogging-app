package com.scalar.blogapp.users;
import com.scalar.blogapp.users.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    // ✅ Constructor name should match the class name
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserDTO ud) {
        var newUser = UserEntity.builder()
                .username(ud.getUsername())
                // .password(password) // TODO: encrypt password
                .email(ud.getEmail())
                .build();
        return userRepository.save(newUser);
    }

    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity loginUser(String username, String password) throws UserNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }
        return user;
    }

    // ✅ Move the exception class outside or make it static
    public static class UserNotFoundException extends IllegalAccessException {
        public UserNotFoundException(String username) {
            super("User '" + username + "' not found");
        }
    }
}
