package ivan.gcashapp.service;

import ivan.gcashapp.entity.Balance;
import ivan.gcashapp.entity.Transaction;
import ivan.gcashapp.entity.User;
import ivan.gcashapp.repository.BalanceRepository;
import ivan.gcashapp.repository.TransactionRepository;
import ivan.gcashapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CashTransfer {

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public void cashTransfer(long fromUserId, long toUserId, double amount) {
        // Restriction: Amount must be positive
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }

        // Restriction: Cannot transfer to self
        if (fromUserId == toUserId) {
            throw new IllegalArgumentException("Cannot transfer to your own account.");
        }

        // Check if recipient exists
        if (!userRepository.existsById(toUserId)) {
            throw new IllegalArgumentException("Recipient account not found.");
        }

        // Check sufficient balance
        List<Balance> senderBalances = balanceRepository.findByUserId(fromUserId);
        double currentBalance = senderBalances.stream().mapToDouble(Balance::getAmount).sum();
        if (currentBalance < amount) {
            throw new IllegalArgumentException("Insufficient balance. Current balance: " + currentBalance);
        }

        // Deduct from sender (negative amount)
        Balance deduct = Balance.builder()
                .amount(-amount)
                .userId(fromUserId)
                .build();
        balanceRepository.save(deduct);

        // Add to receiver (positive amount)
        Balance add = Balance.builder()
                .amount(amount)
                .userId(toUserId)
                .build();
        balanceRepository.save(add);

        // Log the transaction
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .name("Transfer")
                .accountId(fromUserId)
                .date(LocalDateTime.now())
                .transferFromId(fromUserId)
                .transferToId(toUserId)
                .build();
        transactionRepository.save(transaction);
    }
}