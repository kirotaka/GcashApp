package ivan.gcashapp.utility;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserAuthenticationTest {

    @Autowired
    private UserAuthentication userAuthentication;

    @Test
    void testUserAuthenticationBean() {
        // Test that the UserAuthentication component is properly loaded
        assertNotNull(userAuthentication);
    }

    // Note: Full testing of UserAuthentication is challenging due to console input (Scanner).
    // For unit tests, consider refactoring to inject input sources or use mocks.
    // Integration tests would require simulating user input, which is complex.
}
