package ivan.gcashapp.dao;

import ivan.gcashapp.entity.Balance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BalanceDaoTest {

    @Autowired
    private BalanceDao balanceDao;

    @Test
    void testSaveAndFindByUserId() {
        // Arrange
        long userId = 1L;
        Balance balance = Balance.builder()
                .amount(500.0)
                .userId(userId)
                .build();

        // Act
        Balance savedBalance = balanceDao.save(balance);
        List<Balance> balances = balanceDao.findByUserId(userId);

        // Assert
        assertNotNull(savedBalance);
        assertFalse(balances.isEmpty());
        assertEquals(500.0, balances.get(0).getAmount());
        assertEquals(userId, balances.get(0).getUserId());
    }
}
