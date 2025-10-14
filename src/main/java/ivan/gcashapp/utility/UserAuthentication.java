package utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

import java.util.Scanner;

@Component
public class UserAuthentication {

    @Autowired
    private UserService userService;

    private Scanner scanner = new Scanner(System.in);
    private Long loggedInUserId = null;

    public void start() {
        System.out.println("Welcome to GCash App");
        while (true) {
            if (loggedInUserId == null) {
                System.out.println("\n1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    switch (choice) {
                        case 1:
                            register();
                            break;
                        case 2:
                            login();
                            break;
                        case 3:
                            System.out.println("Exiting...");
                            return;
                        default:
                            System.out.println("Invalid option. Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // Clear invalid input
                }
            } else {
                System.out.println("\nLogged in as User ID: " + loggedInUserId);
                System.out.println("1. Change PIN");
                System.out.println("2. Logout");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            changePin();
                            break;
                        case 2:
                            logout();
                            break;
                        case 3:
                            System.out.println("Exiting...");
                            return;
                        default:
                            System.out.println("Invalid option. Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();
                }
            }
        }
    }

    private void register() {
        System.out.println("Registration:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Number (11 digits, e.g., 09123456789): ");
        String number = scanner.nextLine();
        System.out.print("PIN (4 digits): ");
        String pin = scanner.nextLine();
        try {
            User user = userService.register(name, email, number, pin);
            System.out.println("Registration successful! Your User ID is: " + user.get());
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private void login() {
        System.out.println("Login:");
        System.out.print("Email or Number: ");
        String emailOrNumber = scanner.nextLine();
        System.out.print("PIN: ");
        String pin = scanner.nextLine();
        try {
            Long id = userService.login(emailOrNumber, pin);
            loggedInUserId = id;
            System.out.println("Login successful! Welcome.");
        } catch (IllegalArgumentException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private void changePin() {
        System.out.println("Change PIN:");
        System.out.print("Old PIN: ");
        String oldPin = scanner.nextLine();
        System.out.print("New PIN (4 digits): ");
        String newPin = scanner.nextLine();
        try {
            userService.changePin(loggedInUserId, oldPin, newPin);
            System.out.println("PIN changed successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("PIN change failed: " + e.getMessage());
        }
    }

    private void logout() {
        userService.logout(loggedInUserId);
        loggedInUserId = null;
    }
}