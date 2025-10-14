package ivan.gcashapp.service;

import ivan.gcashapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ivan.gcashapp.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(String name, String email, String numStr, String pin) {
        // Validation
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (!email.contains("@") || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (numStr == null || numStr.length() != 11 || !numStr.startsWith("09")) {
            throw new IllegalArgumentException("Number must be 11 digits starting with 09");
        }
        if (pin == null || pin.length() != 4 || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("PIN must be a 4-digit number");
        }
        long number;
        try {
            number = Long.parseLong(numStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format");
        }
        // Check if email or number already exists
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (userRepository.findByNumber(number) != null) {
            throw new IllegalArgumentException("Number already registered");
        }

        User user = User.builder()
                .name(name.trim())
                .email(email.trim().toLowerCase())
                .number(number)
                .pin(pin)
                .build();
        return userRepository.save(user);
    }

    public Long login(String emailOrNumber, String pin) {
        User user = null;
        try {
            long number = Long.parseLong(emailOrNumber);
            user = userRepository.findByNumber(number);
        } catch (NumberFormatException e) {
            user = userRepository.findByEmail(emailOrNumber.trim().toLowerCase());
        }
        if (user != null && user.getPin().equals(pin)) {
            return user.getId();
        }
        // Handle login issues
        if (user == null) {
            throw new IllegalArgumentException("User not found. Please check your email/number or register if new.");
        } else {
            throw new IllegalArgumentException("Incorrect PIN. If forgotten, contact support to reset.");
        }
    }

    public void changePin(Long userId, String oldPin, String newPin) {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isPresent()) {
            User user = opt.get();
            if (user.getPin().equals(oldPin)) {
                if (newPin == null || newPin.length() != 4 || !newPin.matches("\\d{4}")) {
                    throw new IllegalArgumentException("New PIN must be a 4-digit number");
                }
                if (newPin.equals(oldPin)) {
                    throw new IllegalArgumentException("New PIN must be different from old PIN");
                }
                user.setPin(newPin);
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Old PIN is incorrect");
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public void logout(Long userId) {
        // In a real app, invalidate session/token; here just confirm
        System.out.println("User " + userId + " has been logged out successfully.");
    }
}