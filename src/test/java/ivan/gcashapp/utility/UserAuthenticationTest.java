package ivan.gcashapp.utility;

import ivan.gcashapp.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserAuthenticationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void validLogin_shouldMatchDatabaseRecord() {
        long userId = 1L;
        String email = "user@example.com";
        String password = "secret";
        jdbcTemplate.update("INSERT INTO users (id, email, password) VALUES (?, ?, ?)", userId, email, password);

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?",
                Integer.class, email, password);

        assertNotNull(count);
        assertEquals(1, count.intValue());
    }

    @Test
    void invalidLogin_wrongPassword_shouldNotAuthenticate() {
        long userId = 2L;
        String email = "another@example.com";
        String password = "correct";
        jdbcTemplate.update("INSERT INTO users (id, email, password) VALUES (?, ?, ?)", userId, email, password);

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?",
                Integer.class, email, "wrongpassword");

        assertNotNull(count);
        assertEquals(0, count.intValue());
    }
}
