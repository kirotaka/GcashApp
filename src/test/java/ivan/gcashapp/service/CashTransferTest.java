package ivan.gcashapp.dao;

import ivan.gcashapp.TestConfig;
import ivan.gcashapp.entity.Balance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CashTransferTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BalanceDao balanceDao;

    @Test
    void transfer_shouldDebitAndCreditBothUsers() {
        long fromUser = 30L;
        long toUser = 31L;
        double fromInitial = 200.0;
        double toInitial = 75.0;
        double transfer = 50.0;
        jdbcTemplate.update("INSERT INTO balance (id, amount, user_id) VALUES (?, ?, ?)", 300L, fromInitial, fromUser);
        jdbcTemplate.update("INSERT INTO balance (id, amount, user_id) VALUES (?, ?, ?)", 301L, toInitial, toUser);

        jdbcTemplate.update("UPDATE balance SET amount = amount - ? WHERE user_id = ?", transfer, fromUser);
        jdbcTemplate.update("UPDATE balance SET amount = amount + ? WHERE user_id = ?", transfer, toUser);

        List<Balance> fromBalances = balanceDao.findByUserId(fromUser);
        List<Balance> toBalances = balanceDao.findByUserId(toUser);

        assertFalse(fromBalances.isEmpty());
        assertFalse(toBalances.isEmpty());
        assertEquals(fromInitial - transfer, fromBalances.get(0).getAmount(), 0.0001);
        assertEquals(toInitial + transfer, toBalances.get(0).getAmount(), 0.0001);
    }
}
