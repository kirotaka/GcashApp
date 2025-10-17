package ivan.gcashapp.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserBuilder() {
        // Act
        User user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .number(12345678901L)
                .pin("1234")
                .balance(1000.0)
                .build();

        // Assert
        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals(12345678901L, user.getNumber());
        assertEquals("1234", user.getPin());
        assertEquals(1000.0, user.getBalance());
    }

    @Test
    void testUserGettersAndSetters() {
        // Arrange
        User user = new User();

        // Act
        user.setId(2L);
        user.setName("Jane Doe");
        user.setEmail("jane@example.com");
        user.setNumber(98765432109L);
        user.setPin("5678");
        user.setBalance(500.0);

        // Assert
        assertEquals(2L, user.getId());
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals(98765432109L, user.getNumber());
        assertEquals("5678", user.getPin());
        assertEquals(500.0, user.getBalance());
    }

    @Test
    void testUserNoArgsConstructor() {
        // Act
        User user = new User();

        // Assert
        assertNotNull(user);
        assertEquals(0L, user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertEquals(0L, user.getNumber());
        assertNull(user.getPin());
        assertEquals(0.0, user.getBalance());
    }

    @Test
    void testUserAllArgsConstructor() {
        // Act
        User user = new User(3L, "Alice", "alice@example.com", 55566677788L, "9999", 2000.0);

        // Assert
        assertEquals(3L, user.getId());
        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals(55566677788L, user.getNumber());
        assertEquals("9999", user.getPin());
        assertEquals(2000.0, user.getBalance());
    }
}
