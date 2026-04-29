package org.example;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService() {
        this.userRepository = new UserRepository();
    }

    public boolean register(String username, String password) {
        if (username == null || username.isBlank()) {
            System.out.println("Username cannot be empty.");
            return false;
        }
        if (password == null || password.length() < 6) {
            System.out.println("Password must be at least 6 characters.");
            return false;
        }
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        boolean saved = userRepository.save(username.trim(), hash);
        if (!saved) {
            System.out.println("Username already exists.");
        }
        return saved;
    }

    public Optional<User> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username.trim());
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        User user = userOpt.get();
        if (BCrypt.checkpw(password, user.getPasswordHash())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
