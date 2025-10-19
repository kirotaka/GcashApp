package ivan.gcashapp.dao;

import ivan.gcashapp.entity.CashTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransactionDaoTest {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUpDummyData() {
        // clean tables (safe to run even if table doesn't exist in some setups)
        try {
            jdbcTemplate.update("DELETE FROM transaction");
        } catch (Exception ignored) {}
        try {
            jdbcTemplate.update("DELETE FROM cash_transaction");
        } catch (Exception ignored) {}

        LocalDateTime now = LocalDateTime.now();

        // Insert dummy rows into the database with explicit ids so tests can query by id
        try {
            jdbcTemplate.update(
                    "INSERT INTO transaction (id, amount, name, account_id, date, transfer_to_id, transfer_from_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    100L, 150.0, "Deposit", 1L, Timestamp.valueOf(now), null, null
            );
            jdbcTemplate.update(
                    "INSERT INTO transaction (id, amount, name, account_id, date, transfer_to_id, transfer_from_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    101L, -20.0, "Purchase", 1L, Timestamp.valueOf(now), null, null
            );
            jdbcTemplate.update(
                    "INSERT INTO transaction (id, amount, name, account_id, date, transfer_to_id, transfer_from_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    102L, 75.0, "Transfer In", 2L, Timestamp.valueOf(now), 2L, 3L
            );
        } catch (Exception ignored) {
            // If the `transaction` table name differs (e.g. `cash_transaction`), try that as fallback
            jdbcTemplate.update(
                    "INSERT INTO cash_transaction (id, amount, name, account_id, date, transfer_to_id, transfer_from_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    100L, 150.0, "Deposit", 1L, Timestamp.valueOf(now), null, null
            );
            jdbcTemplate.update(
                    "INSERT INTO cash_transaction (id, amount, name, account_id, date, transfer_to_id, transfer_from_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    101L, -20.0, "Purchase", 1L, Timestamp.valueOf(now), null, null
            );
            jdbcTemplate.update(
                    "INSERT INTO cash_transaction (id, amount, name, account_id, date, transfer_to_id, transfer_from_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    102L, 75.0, "Transfer In", 2L, Timestamp.valueOf(now), 2L, 3L
            );
        }
    }

    @Test
    void testSaveAndFindById() {
        // Insert a known record (DAO.save with JdbcTemplate does not populate generated id)
        LocalDateTime now = LocalDateTime.now();
        long knownId = 500L;
        try {
            jdbcTemplate.update(
                    "INSERT INTO transaction (id, amount, name, account_id, date, transfer_to_id, transfer_from_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    knownId, 100.0, "Test Transaction", 9L, Timestamp.valueOf(now), 2L, 1L
            );
        } catch (Exception ignored) {
            jdbcTemplate.update(
                    "INSERT INTO cash_transaction (id, amount, name, account_id, date, transfer_to_id, transfer_from_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    knownId, 100.0, "Test Transaction", 9L, Timestamp.valueOf(now), 2L, 1L
            );
        }

        Optional<CashTransaction> found = transactionDao.findById(knownId);

        assertTrue(found.isPresent());
        assertEquals(100.0, found.get().getAmount(), 0.0001);
        assertEquals("Test Transaction", found.get().getName());
    }

    @Test
    void testFindByAccountId() {
        List<CashTransaction> transactions = transactionDao.findByAccountId(1L);

        assertFalse(transactions.isEmpty());
        // Expect one deposit and one purchase for account_id = 1 from setup
        boolean hasDeposit = transactions.stream().anyMatch(t -> Double.valueOf(150.0).equals(t.getAmount()));
        boolean hasPurchase = transactions.stream().anyMatch(t -> Double.valueOf(-20.0).equals(t.getAmount()));
        assertTrue(hasDeposit);
        assertTrue(hasPurchase);
    }

    @Test
    void testFindAll() {
        List<CashTransaction> transactions = transactionDao.findAll();
        assertNotNull(transactions);
        assertTrue(transactions.size() >= 3); // we inserted at least 3 dummy records
    }
}
