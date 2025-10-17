package ivan.gcashapp.service;

import ivan.gcashapp.dao.BalanceDao;
import ivan.gcashapp.dao.TransactionDao;
import ivan.gcashapp.entity.Balance;
import ivan.gcashapp.entity.CashTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CashInTest {

    @Autowired
    private CashIn cashIn;

    @Autowired
    private BalanceDao balanceDao;

    @Autowired
    private TransactionDao transactionDao;

    @Test
    void testCashIn() {
        // Arrange
        long accountId = 1L;
        double amount = 150.0;

        // Act
        cashIn.cashin(accountId, amount);

        // Assert
        List<Balance> balances = balanceDao.findByUserId(accountId);
        assertFalse(balances.isEmpty());
        assertEquals(amount, balances.get(0).getAmount());

        List<CashTransaction> transactions = transactionDao.findByAccountId(accountId);
        assertFalse(transactions.isEmpty());
        assertEquals(amount, transactions.get(0).getAmount());
        assertEquals("Cash In", transactions.get(0).getName());
    }
}
