package org.example;

public class Note {
    private int id;
    private int userId;
    private String title;
    private String content;
    private String createdAt;

    public Note(int id, int userId, String title, String content, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCreatedAt() { return createdAt; }
}
