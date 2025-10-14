package ivan.gcashapp.dao;

import ivan.gcashapp.entity.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BalanceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Balance> findByUserId(long userId) {
        String sql = "SELECT * FROM balance WHERE user_id = ?";
        return jdbcTemplate.query(sql, new BalanceRowMapper(), userId);
    }

    public Balance save(Balance balance) {
        String sql = "INSERT INTO balance (amount, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, balance.getAmount(), balance.getUserId());
        return balance;
    }

    private static class BalanceRowMapper implements RowMapper<Balance> {
        @Override
        public Balance mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Balance.builder()
                    .id(rs.getLong("id"))
                    .amount(rs.getDouble("amount"))
                    .userId(rs.getLong("user_id"))
                    .build();
        }
    }
}