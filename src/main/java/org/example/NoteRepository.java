package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    private final Connection conn;

    public NoteRepository() {
        this.conn = DatabaseManager.getInstance().getConnection();
    }

    public boolean save(int userId, String title, String content) {
        String sql = "INSERT INTO notes (user_id, title, content) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.setString(3, content);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error saving message: " + e.getMessage());
            return false;
        }
    }

    public List<Note> findByUserId(int userId) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT id, user_id, title, content, created_at FROM notes WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                notes.add(new Note(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime().toString()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting notes: " + e.getMessage());
        }
        return notes;
    }
}
