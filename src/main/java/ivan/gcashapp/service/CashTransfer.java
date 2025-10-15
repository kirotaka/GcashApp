package ivan.gcashapp.service;

import ivan.gcashapp.dao.BalanceDao;
import ivan.gcashapp.dao.TransactionDao;
import ivan.gcashapp.dao.UserDao;
import ivan.gcashapp.entity.Balance;
import ivan.gcashapp.entity.CashTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CashTransfer {

    @Autowired
    private BalanceDao balanceDao;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private UserDao userDao;

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
        if (!userDao.existsById(toUserId)) {
            throw new IllegalArgumentException("Recipient account not found.");
        }

        // Check sufficient balance
        List<Balance> senderBalances = balanceDao.findByUserId(fromUserId);
        double currentBalance = senderBalances.stream().mapToDouble(Balance::getAmount).sum();
        if (currentBalance < amount) {
            throw new IllegalArgumentException("Insufficient balance. Current balance: " + currentBalance);
        }

        // Deduct from sender (negative amount)
        Balance deduct = Balance.builder()
                .amount(-amount)
                .userId(fromUserId)
                .build();
        balanceDao.save(deduct);

        // Add to receiver (positive amount)
        Balance add = Balance.builder()
                .amount(amount)
                .userId(toUserId)
                .build();
        balanceDao.save(add);

        // Log the transaction
        CashTransaction cashTransaction = CashTransaction.builder()
                .amount(amount)
                .name("Transfer")
                .accountId(fromUserId)
                .date(LocalDateTime.now())
                .transferFromId(fromUserId)
                .transferToId(toUserId)
                .build();
        transactionDao.save(cashTransaction);
    }
}