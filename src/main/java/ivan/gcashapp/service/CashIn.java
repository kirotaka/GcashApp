package ivan.gcashapp.service;

import ivan.gcashapp.dao.BalanceDao;
import ivan.gcashapp.dao.TransactionDao;
import ivan.gcashapp.entity.Balance;
import ivan.gcashapp.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CashIn {

    @Autowired
    private BalanceDao balanceDao;

    @Autowired
    private TransactionDao transactionDao;

    public void cashin(long accountId, double amount) {
        // Add to balance (positive amount for cash in)
        Balance balance = Balance.builder()
                .amount(amount)
                .userId(accountId)
                .build();
        balanceDao.save(balance);

        // Log the transaction
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .name("Cash In")
                .accountId(accountId)
                .date(LocalDateTime.now())
                .transferToId(accountId)
                .transferFromId(0)  // 0 for external source
                .build();
        transactionDao.save(transaction);
    }
}