package ivan.gcashapp.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceTest {

    @Test
    void testBalanceBuilder() {
        // Arrange & Act
        Balance balance = Balance.builder()
                .id(1L)
                .amount(500.0)
                .userId(123L)
                .build();

        // Assert
        assertEquals(1L, balance.getId());
        assertEquals(500.0, balance.getAmount());
        assertEquals(123L, balance.getUserId());
    }

    @Test
    void testBalanceGettersAndSetters() {
        // Arrange
        Balance balance = new Balance();

        // Act
        balance.setId(2L);
        balance.setAmount(300.0);
        balance.setUserId(456L);

        // Assert
        assertEquals(2L, balance.getId());
        assertEquals(300.0, balance.getAmount());
        assertEquals(456L, balance.getUserId());
    }

    @Test
    void testBalanceNoArgsConstructor() {
        // Act
        Balance balance = new Balance();

        // Assert
        assertNotNull(balance);
        assertEquals(0L, balance.getId());
        assertEquals(0.0, balance.getAmount());
        assertEquals(0L, balance.getUserId());
    }

    @Test
    void testBalanceAllArgsConstructor() {
        // Act
        Balance balance = new Balance(3L, 400.0, 789L);

        // Assert
        assertEquals(3L, balance.getId());
        assertEquals(400.0, balance.getAmount());
        assertEquals(789L, balance.getUserId());
    }
}
