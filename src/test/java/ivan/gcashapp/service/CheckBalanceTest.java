package ivan.gcashapp.service;

import ivan.gcashapp.entity.Balance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CheckBalanceTest {

    @Autowired
    private CheckBalance checkBalance;

    @Autowired
    private ivan.gcashapp.dao.BalanceDao balanceDao; // To set up data

    @Test
    void testCheckBalance() {
        // Arrange
        long userId = 1L;
        Balance balance1 = Balance.builder().amount(300.0).userId(userId).build();
        Balance balance2 = Balance.builder().amount(200.0).userId(userId).build();
        balanceDao.save(balance1);
        balanceDao.save(balance2);

        // Act
        double totalBalance = checkBalance.checkBalance(userId);

        // Assert
        assertEquals(500.0, totalBalance);
    }
}

import ivan.gcashapp.entity.CashTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TransactionDaoTest {

    @Autowired
    private TransactionDao transactionDao;

    @Test
    void testSaveAndFindById() {
        // Arrange
        CashTransaction transaction = CashTransaction.builder()
                .amount(100.0)
                .name("Test Transaction")
                .accountId(1L)
                .date(LocalDateTime.now())
                .transferToId(2L)
                .transferFromId(1L)
                .build();

        // Act
        CashTransaction saved = transactionDao.save(transaction);
        Optional<CashTransaction> found = transactionDao.findById(1L); // Assuming ID starts from 1

        // Assert
        assertNotNull(saved);
        assertTrue(found.isPresent());
        assertEquals(100.0, found.get().getAmount());
    }

    @Test
    void testFindByAccountId() {
        // Arrange
        CashTransaction transaction = CashTransaction.builder()
                .amount(200.0)
                .name("Another Transaction")
                .accountId(1L)
                .date(LocalDateTime.now())
                .transferToId(3L)
                .transferFromId(1L)
                .build();
        transactionDao.save(transaction);

        // Act
        List<CashTransaction> transactions = transactionDao.findByAccountId(1L);

        // Assert
        assertFalse(transactions.isEmpty());
        assertEquals(200.0, transactions.get(0).getAmount());
    }

    @Test
    void testFindAll() {
        // Act
        List<CashTransaction> transactions = transactionDao.findAll();

        // Assert
        assertNotNull(transactions);
        // Assuming some data exists, but in test, might be empty
    }
}
