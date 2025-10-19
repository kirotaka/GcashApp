// java
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
public class CheckBalanceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BalanceDao balanceDao;

    @Test
    void checkBalance_byUserId_shouldMatchDatabase() {
        // Arrange
        long userId = 10L;
        double amount = 1234.56;
        jdbcTemplate.update("INSERT INTO balance (id, amount, user_id) VALUES (?, ?, ?)", 100L, amount, userId);

        // Act
        List<Balance> balances = balanceDao.findByUserId(userId);

        // Assert
        assertFalse(balances.isEmpty());
        assertEquals(amount, balances.get(0).getAmount());
        assertEquals(userId, balances.get(0).getUserId());
    }
}
