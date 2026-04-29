package org.example;

import java.sql.*;
import java.util.Optional;

public class UserRepository {

    private final Connection conn;

    public UserRepository() {
        this.conn = DatabaseManager.getInstance().getConnection();
    }

    public boolean save(String username, String passwordHash) {
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT id, username, password_hash FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password_hash")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error finding user: " + e.getMessage());
        }
        return Optional.empty();
    }
}
