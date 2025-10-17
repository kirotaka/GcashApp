package ivan.gcashapp.dao;

import ivan.gcashapp.entity.CashTransactionTest;
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
public class TransactionDaoTest {


    @Test
    void testSaveAndFindById() {
        // Arrange
        CashTransactionTest transaction = CashTransactionTest.builder()
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
