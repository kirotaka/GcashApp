package ivan.gcashapp.dao;

import ivan.gcashapp.TestConfig;
import ivan.gcashapp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void testSaveAndFindById() {
        // Arrange
        User user = User.builder()
                .name("Test User")
                .email("test@example.com")
                .number(12345678901L)
                .pin("1234")
                .balance(1000.0)
                .build();

        // Act
        User saved = userDao.save(user);
        Optional<User> found = userDao.findById(1L); // Assuming auto-increment starts from 1

        // Assert
        assertNotNull(saved);
        assertTrue(found.isPresent());
        assertEquals("Test User", found.get().getName());
    }

    @Test
    void testFindByEmail() {
        // Arrange
        User user = User.builder()
                .name("Email User")
                .email("email@example.com")
                .number(12345678902L)
                .pin("5678")
                .balance(500.0)
                .build();
        userDao.save(user);

        // Act
        Optional<User> found = userDao.findByEmail("email@example.com");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Email User", found.get().getName());
    }

    @Test
    void testFindByNumber() {
        // Arrange
        User user = User.builder()
                .name("Number User")
                .email("number@example.com")
                .number(12345678903L)
                .pin("9012")
                .balance(200.0)
                .build();
        userDao.save(user);

        // Act
        Optional<User> found = userDao.findByNumber(12345678903L);

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Number User", found.get().getName());
    }

    @Test
    void testExistsById() {
        // Arrange
        User user = User.builder()
                .name("Exists User")
                .email("exists@example.com")
                .number(12345678904L)
                .pin("3456")
                .balance(300.0)
                .build();
        userDao.save(user);

        // Act
        boolean exists = userDao.existsById(1L); // Assuming ID 1

        // Assert
        assertTrue(exists);
    }
}
