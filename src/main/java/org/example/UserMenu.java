package org.example;

import java.util.List;
import java.util.Scanner;

public class UserMenu {

    private final Scanner scanner;
    private final User user;
    private final NoteRepository noteRepository;

    public UserMenu(Scanner scanner, User user) {
        this.scanner = scanner;
        this.user = user;
        this.noteRepository = new NoteRepository();
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> viewNotes();
                case "2" -> createNote();
                case "3" -> running = false;
                default -> System.out.println("Invalid choice, try again.");
            }
        }
        System.out.println("Logged out.");
    }

    private void printMenu() {
        System.out.println("\n--- User Menu (" + user.getUsername() + ") ---");
        System.out.println("1. View my notes");
        System.out.println("2. Create note");
        System.out.println("3. Logout");
        System.out.print("Choice: ");
    }

    private void viewNotes() {
        List<Note> notes = noteRepository.findByUserId(user.getId());
        if (notes.isEmpty()) {
            System.out.println("You have no notes yet.");
            return;
        }
        System.out.println("\n--- Your Notes ---");
        for (Note note : notes) {
            System.out.println("Title:   " + note.getTitle());
            System.out.println("Content: " + note.getContent());
            System.out.println("Created: " + note.getCreatedAt());
            System.out.println("----------");
        }
    }

    private void createNote() {
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        if (title.isBlank()) {
            System.out.println("Title cannot be empty.");
            return;
        }
        System.out.print("Content: ");
        String content = scanner.nextLine().trim();

        if (noteRepository.save(user.getId(), title, content)) {
            System.out.println("Note saved!");
        } else {
            System.out.println("Failed to save note.");
        }
    }
}
