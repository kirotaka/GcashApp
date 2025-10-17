package ivan.gcashapp.service;

import ivan.gcashapp.dao.BalanceDao;
import ivan.gcashapp.dao.TransactionDao;
import ivan.gcashapp.dao.UserDao;
import ivan.gcashapp.entity.Balance;
import ivan.gcashapp.entity.CashTransaction;
import ivan.gcashapp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CashTransferTest {

    @Autowired
    private CashTransfer cashTransfer;

    @Autowired
    private BalanceDao balanceDao;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private UserDao userDao;

    @Test
    void testCashTransferValid() {
        // Arrange
        User sender = User.builder().name("Sender").email("sender@example.com").number(11111111111L).pin("1111").balance(1000.0).build();
        User receiver = User.builder().name("Receiver").email("receiver@example.com").number(22222222222L).pin("2222").balance(500.0).build();
        userDao.save(sender);
        userDao.save(receiver);
        // Assuming IDs are 1 and 2
        Balance senderBalance = Balance.builder().amount(300.0).userId(1L).build();
        balanceDao.save(senderBalance);

        // Act
        cashTransfer.cashTransfer(1L, 2L, 100.0);

        // Assert
        List<Balance> senderBalances = balanceDao.findByUserId(1L);
        double senderTotal = senderBalances.stream().mapToDouble(Balance::getAmount).sum();
        assertEquals(200.0, senderTotal);

        List<Balance> receiverBalances = balanceDao.findByUserId(2L);
        double receiverTotal = receiverBalances.stream().mapToDouble(Balance::getAmount).sum();
        assertEquals(100.0, receiverTotal);

        List<CashTransaction> transactions = transactionDao.findByAccountId(1L);
        assertFalse(transactions.isEmpty());
        assertEquals(100.0, transactions.get(0).getAmount());
    }

    @Test
    void testCashTransferInsufficientBalance() {
        // Arrange
        User sender = User.builder().name("Sender2").email("sender2@example.com").number(33333333333L).pin("3333").balance(1000.0).build();
        User receiver = User.builder().name("Receiver2").email("receiver2@example.com").number(44444444444L).pin("4444").balance(500.0).build();
        userDao.save(sender);
        userDao.save(receiver);
        // Assuming IDs are 3 and 4
        Balance senderBalance = Balance.builder().amount(50.0).userId(3L).build();
        balanceDao.save(senderBalance);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cashTransfer.cashTransfer(3L, 4L, 100.0);
        });
        assertEquals("Insufficient balance. Current balance: 50.0", exception.getMessage());
    }
}
