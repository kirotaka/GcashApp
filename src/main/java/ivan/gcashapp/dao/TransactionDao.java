package ivan.gcashapp.dao;

import ivan.gcashapp.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class TransactionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Transaction save(Transaction transaction) {
        String sql = "INSERT INTO transaction (amount, name, account_id, date, transfer_to_id, transfer_from_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, transaction.getAmount(), transaction.getName(), transaction.getAccountId(),
                transaction.getDate(), transaction.getTransferToId(), transaction.getTransferFromId());
        return transaction;
    }
}