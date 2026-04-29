package org.example;

import java.util.Optional;
import java.util.Scanner;

public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final AuthService authService = new AuthService();

    public void start() {
        System.out.println("_____ NOTES ______");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> register();
                case "2" -> login();
                case "3" -> {
                    System.out.println("Thank you, good bye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choice: ");
    }

    private void register() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (authService.register(username, password)) {
            System.out.println("Account successfully created, you can now log in.");
        }
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Optional<User> userOpt = authService.login(username, password);
        if (userOpt.isPresent()) {
            System.out.println("Welcome  " + userOpt.get().getUsername() + "!");
            new UserMenu(scanner, userOpt.get()).start();
        } else {
            System.out.println("Invalid username or password.");
        }
    }
}
