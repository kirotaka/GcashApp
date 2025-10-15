package ivan.gcashapp.utility;

import ivan.gcashapp.dao.UserDao;
import ivan.gcashapp.entity.CashTransaction;
import ivan.gcashapp.entity.User;
import ivan.gcashapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class UserAuthentication {

    @Autowired
    private UserService userService;

    @Autowired
    private CheckBalance checkBalance;

    @Autowired
    private CashIn cashIn;

    @Autowired
    private CashTransfer cashTransfer;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Transactions transactions;

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
                System.out.println("2. Check Balance");
                System.out.println("3. Cash In");
                System.out.println("4. Cash Transfer");
                System.out.println("5. View Transactions");
                System.out.println("6. Logout");
                System.out.println("7. Exit");
                System.out.print("Choose an option: ");
                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            changePin();
                            askContinue();
                            break;
                        case 2:
                            checkBalance();
                            askContinue();
                            break;
                        case 3:
                            cashIn();
                            askContinue();
                            break;
                        case 4:
                            cashTransfer();
                            askContinue();
                            break;
                        case 5:
                            viewTransactions();
                            askContinue();
                            break;
                        case 6:
                            logout();
                            break;
                        case 7:
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

    private void askContinue() {
        System.out.print("Do you want to perform another transaction? (y/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equals("y")) {
            while (true) {
                System.out.println("1. Check Balance");
                System.out.println("2. Cash In");
                System.out.println("3. Cash Transfer");
                System.out.println("4. View Transactions");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                try {
                    int choice2 = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice2) {
                        case 1:
                            checkBalance();
                            break;
                        case 2:
                            cashIn();
                            break;
                        case 3:
                            cashTransfer();
                            break;
                        case 4:
                            viewTransactions();
                            break;
                        case 5:
                            logout();
                            return;
                        default:
                            System.out.println("Invalid option. Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();
                }
            }
        } else {

        }
    }

    private void register() {
        System.out.println("Registration:");
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Number (11 digits, e.g., 09123456789): ");
        String numStr = scanner.nextLine().trim();
        System.out.print("PIN (4 digits): ");
        String pin = scanner.nextLine().trim();
        try {
            User user = userService.register(name, email, numStr, pin);
            System.out.println("Registration successful! Your User ID is: " + user.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private void login() {
        System.out.println("Login:");
        System.out.print("Enter your Email or Phone Number: ");
        String emailOrNumber = scanner.nextLine().trim();
        System.out.print("PIN: ");
        String pin = scanner.nextLine().trim();
        try {
            loggedInUserId = userService.login(emailOrNumber, pin);
            System.out.println("Login successful! Welcome.");
        } catch (IllegalArgumentException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private void changePin() {
        System.out.println("Change PIN:");
        System.out.print("Old PIN: ");
        String oldPin = scanner.nextLine().trim();
        System.out.print("New PIN (4 digits): ");
        String newPin = scanner.nextLine().trim();
        try {
            userService.changePin(loggedInUserId, oldPin, newPin);
            System.out.println("PIN changed successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("PIN change failed: " + e.getMessage());
        }
    }

    private void checkBalance() {
        try {
            double balance = checkBalance.checkBalance(loggedInUserId);
            System.out.println("Your current balance is: " + balance);
        } catch (Exception e) {
            System.out.println("Error checking balance: " + e.getMessage());
        }
    }

    private void cashIn() {
        System.out.print("Enter amount to cash in: ");
        try {
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            if (amount <= 0) {
                System.out.println("Amount must be positive.");
                return;
            }
            cashIn.cashin(loggedInUserId, amount);
            System.out.println("Cash in successful!");
        } catch (Exception e) {
            System.out.println("Invalid amount. Please enter a number.");
            scanner.nextLine();
        }
    }

    private void cashTransfer() {
        System.out.print("Enter recipient's phone number: ");
        String recipientNumStr = scanner.nextLine().trim();
        long recipientId;
        try {
            long recipientNum = Long.parseLong(recipientNumStr);
            Optional<User> recipientOpt = userDao.findByNumber(recipientNum);
            if (recipientOpt.isEmpty()) {
                System.out.println("Recipient not found. Please check the phone number.");
                return;
            }
            recipientId = recipientOpt.get().getId();
        } catch (NumberFormatException e) {
            System.out.println("Invalid phone number format.");
            return;
        }

        System.out.print("Enter amount to transfer: ");
        try {
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            if (amount <= 0) {
                System.out.println("Transfer amount must be positive.");
                return;
            }
            cashTransfer.cashTransfer(loggedInUserId, recipientId, amount);
            System.out.println("Transfer successful!");
        } catch (IllegalArgumentException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid amount. Please enter a number.");
            scanner.nextLine();
        }
    }

    private void viewTransactions() {
        try {
            List<CashTransaction> trans = transactions.viewUserAll(loggedInUserId);
            if (trans.isEmpty()) {
                System.out.println("No transactions found.");
            } else {
                for (CashTransaction t : trans) {
                    System.out.println("ID: " + t.getId() + ", Amount: " + t.getAmount() + ", Name: " + t.getName() + ", Date: " + t.getDate());
                }
            }
        } catch (Exception e) {
            System.out.println("Error viewing transactions: " + e.getMessage());
        }
    }

    private void logout() {
        userService.logout(loggedInUserId);
        loggedInUserId = null;
    }
}