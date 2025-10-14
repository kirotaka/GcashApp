package ivan.gcashapp.service;

import ivan.gcashapp.entity.Balance;
import ivan.gcashapp.entity.Transaction;
import ivan.gcashapp.repository.BalanceRepository;
import ivan.gcashapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CashIn {

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void cashin(long accountId, double amount) {
        // Add to balance (positive amount for cash in)
        Balance balance = Balance.builder()
                .amount(amount)
                .userId(accountId)
                .build();
        balanceRepository.save(balance);

        // Log the transaction
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .name("Cash In")
                .accountId(accountId)
                .date(LocalDateTime.now())
                .transferToId(accountId)
                .transferFromId(0)  // 0 for external source
                .build();
        transactionRepository.save(transaction);
    }
}