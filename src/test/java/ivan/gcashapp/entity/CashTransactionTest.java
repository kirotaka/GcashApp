package ivan.gcashapp.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CashTransactionTest {

    @Test
    void testCashTransactionBuilder() {
        // Arrange
        LocalDateTime date = LocalDateTime.now();

        // Act
        CashTransaction transaction = CashTransaction.builder()
                .id(1L)
                .amount(150.0)
                .name("Test Transaction")
                .accountId(123L)
                .date(date)
                .transferToId(456L)
                .transferFromId(789L)
                .build();

        // Assert
        assertEquals(1L, transaction.getId());
        assertEquals(150.0, transaction.getAmount());
        assertEquals("Test Transaction", transaction.getName());
        assertEquals(123L, transaction.getAccountId());
        assertEquals(date, transaction.getDate());
        assertEquals(456L, transaction.getTransferToId());
        assertEquals(789L, transaction.getTransferFromId());
    }

    @Test
    void testCashTransactionGettersAndSetters() {
        // Arrange
        CashTransaction transaction = new CashTransaction();
        LocalDateTime date = LocalDateTime.now();

        // Act
        transaction.setId(2L);
        transaction.setAmount(200.0);
        transaction.setName("Another Transaction");
        transaction.setAccountId(321L);
        transaction.setDate(date);
        transaction.setTransferToId(654L);
        transaction.setTransferFromId(987L);

        // Assert
        assertEquals(2L, transaction.getId());
        assertEquals(200.0, transaction.getAmount());
        assertEquals("Another Transaction", transaction.getName());
        assertEquals(321L, transaction.getAccountId());
        assertEquals(date, transaction.getDate());
        assertEquals(654L, transaction.getTransferToId());
        assertEquals(987L, transaction.getTransferFromId());
    }

    @Test
    void testCashTransactionNoArgsConstructor() {
        // Act
        CashTransaction transaction = new CashTransaction();

        // Assert
        assertNotNull(transaction);
        assertEquals(0L, transaction.getId());
        assertEquals(0.0, transaction.getAmount());
        assertNull(transaction.getName());
        assertEquals(0L, transaction.getAccountId());
        assertNull(transaction.getDate());
        assertEquals(0L, transaction.getTransferToId());
        assertEquals(0L, transaction.getTransferFromId());
    }

    @Test
    void testCashTransactionAllArgsConstructor() {
        // Arrange
        LocalDateTime date = LocalDateTime.now();

        // Act
        CashTransaction transaction = new CashTransaction(3L, 250.0, "Full Transaction", 111L, date, 222L, 333L);

        // Assert
        assertEquals(3L, transaction.getId());
        assertEquals(250.0, transaction.getAmount());
        assertEquals("Full Transaction", transaction.getName());
        assertEquals(111L, transaction.getAccountId());
        assertEquals(date, transaction.getDate());
        assertEquals(222L, transaction.getTransferToId());
        assertEquals(333L, transaction.getTransferFromId());
    }
}
