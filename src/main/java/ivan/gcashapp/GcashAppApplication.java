package ivan.gcashapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ivan.gcashapp.utility.UserAuthentication;


@SpringBootApplication
public class GcashAppApplication implements CommandLineRunner {

    @Autowired
    private UserAuthentication userAuthentication;

    public static void main(String[] args) {
        SpringApplication.run(GcashAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userAuthentication.start();
    }
}