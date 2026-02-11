package org.example;

public class Main {
    public static void main(String[] args) {
        // Start the application with the Login Form
        System.out.println("=== User Authentication System ===");
        System.out.println("Starting application...");
        System.out.println("\nDummy Test Account:");
        System.out.println("Email: test@example.com");
        System.out.println("Password: Test@123");
        System.out.println("\nNote: You can also register a new account!");

        // Launch the Login Form
        new LoginFormAWT();
    }
}