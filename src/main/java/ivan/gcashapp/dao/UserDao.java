package ivan.gcashapp.dao;

import ivan.gcashapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findById(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), id);
        return users.stream().findFirst();
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), email);
        return users.stream().findFirst();
    }

    public Optional<User> findByNumber(long number) {
        String sql = "SELECT * FROM users WHERE number = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), number);
        return users.stream().findFirst();
    }

    public boolean existsById(long id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    public User save(User user) {
        String sql = "INSERT INTO users (name, email, number, pin, balance) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getNumber(), user.getPin(), user.getBalance());
        // For simplicity, assume ID is auto-generated; you can retrieve last insert ID if needed
        return user;
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .number(rs.getLong("number"))
                    .pin(rs.getString("pin"))
                    .balance(rs.getDouble("balance"))
                    .build();
        }
    }
}