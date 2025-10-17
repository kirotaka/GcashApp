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
