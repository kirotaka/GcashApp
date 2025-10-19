package ivan.gcashapp;

import ivan.gcashapp.utility.UserAuthentication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TestConfig {

    @MockBean
    private UserAuthentication userAuthentication;
}
