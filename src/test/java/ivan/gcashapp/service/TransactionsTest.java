package ivan.gcashapp.service;  // Match the directory structure

import ivan.gcashapp.TestConfig;
import ivan.gcashapp.entity.CashTransaction;
import ivan.gcashapp.dao.TransactionDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "app.cli.enabled=false")
@Import(TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class TransactionsTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionDao transactionDao;

    @Test
    void transactions_shouldBeDisplayedForAccount() {
        long accountId = 40L;
        LocalDateTime now = LocalDateTime.now();
        jdbcTemplate.update(
                "INSERT INTO cash_transaction (id, amount, name, account_id, date, transfer_to_id, transfer_from_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                400L, 150.0, "Deposit", accountId, Timestamp.valueOf(now), null, null);
        jdbcTemplate.update(
                "INSERT INTO cash_transaction (id, amount, name, account_id, date, transfer_to_id, transfer_from_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                401L, -20.0, "Purchase", accountId, Timestamp.valueOf(now), null, null);

        List<CashTransaction> txs = transactionDao.findByAccountId(accountId);

        assertFalse(txs.isEmpty());  // Changed from assertTrue to assertFalse
        boolean hasDeposit = txs.stream().anyMatch(t -> Double.valueOf(150.0).equals(t.getAmount()));
        boolean hasPurchase = txs.stream().anyMatch(t -> Double.valueOf(-20.0).equals(t.getAmount()));
        assertTrue(hasDeposit);
        assertTrue(hasPurchase);
    }
}
