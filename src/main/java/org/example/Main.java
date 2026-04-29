package org.example;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.getInstance();
        new ConsoleMenu().start();
    }
}
