package ivan.gcashapp.service;

import ivan.gcashapp.dao.TransactionDao;
import ivan.gcashapp.entity.CashTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TransactionsTest {

    @Autowired
    private Transactions transactions;

    @Autowired
    private TransactionDao transactionDao;

    @Test
    void testViewAll() {
        // Arrange
        CashTransaction transaction = CashTransaction.builder()
                .amount(50.0)
                .name("Test View")
                .accountId(1L)
                .date(LocalDateTime.now())
                .transferToId(2L)
                .transferFromId(1L)
                .build();
        transactionDao.save(transaction);

        // Act
        List<CashTransaction> allTransactions = transactions.viewAll();

        // Assert
        assertNotNull(allTransactions);
        assertFalse(allTransactions.isEmpty());
    }

    @Test
    void testViewUserAll() {
        // Arrange
        CashTransaction transaction = CashTransaction.builder()
                .amount(75.0)
                .name("User Transaction")
                .accountId(1L)
                .date(LocalDateTime.now())
                .transferToId(3L)
                .transferFromId(1L)
                .build();
        transactionDao.save(transaction);

        // Act
        List<CashTransaction> userTransactions = transactions.viewUserAll(1L);

        // Assert
        assertNotNull(userTransactions);
        assertFalse(userTransactions.isEmpty());
        assertEquals(75.0, userTransactions.get(0).getAmount());
    }

    @Test
    void testViewTransaction() {
        // Arrange
        CashTransaction transaction = CashTransaction.builder()
                .amount(25.0)
                .name("Single Transaction")
                .accountId(1L)
                .date(LocalDateTime.now())
                .transferToId(4L)
                .transferFromId(1L)
                .build();
        transactionDao.save(transaction);

        // Act
        CashTransaction viewed = transactions.viewTransaction(1L); // Assuming ID 1

        // Assert
        assertNotNull(viewed);
        assertEquals(25.0, viewed.getAmount());
    }
}
