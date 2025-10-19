package ivan.gcashapp.dao;

import ivan.gcashapp.entity.CashTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CashTransaction save(CashTransaction cashTransaction) {
        String sql = "INSERT INTO transaction (amount, name, account_id, date, transfer_to_id, transfer_from_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, cashTransaction.getAmount(), cashTransaction.getName(), cashTransaction.getAccountId(),
                cashTransaction.getDate(), cashTransaction.getTransferToId(), cashTransaction.getTransferFromId());
        return cashTransaction;
    }

    public List<CashTransaction> findAll() {
        String sql = "SELECT * FROM cash_transaction";
        return jdbcTemplate.query(sql, new TransactionRowMapper());
    }

    public List<CashTransaction> findByAccountId(long accountId) {
        String sql = "SELECT * FROM cash_transaction WHERE account_id = ?";
        return jdbcTemplate.query(sql, new TransactionRowMapper(), accountId);
    }

    public Optional<CashTransaction> findById(long id) {
        String sql = "SELECT * FROM transaction WHERE id = ?";
        List<CashTransaction> cashTransactions = jdbcTemplate.query(sql, new TransactionRowMapper(), id);
        return cashTransactions.stream().findFirst();
    }

    private static class TransactionRowMapper implements RowMapper<CashTransaction> {
        @Override
        public CashTransaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CashTransaction.builder()
                    .id(rs.getLong("id"))
                    .amount(rs.getDouble("amount"))
                    .name(rs.getString("name"))
                    .accountId(rs.getLong("account_id"))
                    .date(rs.getObject("date", LocalDateTime.class))
                    .transferToId(rs.getLong("transfer_to_id"))
                    .transferFromId(rs.getLong("transfer_from_id"))
                    .build();
        }
    }
}