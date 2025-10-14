package ivan.gcashapp.repository;

import ivan.gcashapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByNumber(long number);
}