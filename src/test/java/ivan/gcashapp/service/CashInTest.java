// java
package ivan.gcashapp.service;

import ivan.gcashapp.TestConfig;
import ivan.gcashapp.entity.Balance;
import ivan.gcashapp.dao.BalanceDao;
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
public class CashInTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BalanceDao balanceDao;

    @Test
    void cashin_shouldIncreaseBalance() {
        // Arrange
        long userId = 20L;
        double initial = 50.0;
        double deposit = 25.0;
        jdbcTemplate.update("INSERT INTO balance (id, amount, user_id) VALUES (?, ?, ?)", 200L, initial, userId);

        // Act: simulate cashin
        jdbcTemplate.update("UPDATE balance SET amount = amount + ? WHERE user_id = ?", deposit, userId);
        List<Balance> balances = balanceDao.findByUserId(userId);

        // Assert
        assertFalse(balances.isEmpty());
        assertEquals(initial + deposit, balances.get(0).getAmount(), 0.0001);
    }
}
