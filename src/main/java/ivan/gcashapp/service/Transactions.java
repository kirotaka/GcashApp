package ivan.gcashapp.service;

import ivan.gcashapp.dao.TransactionDao;
import ivan.gcashapp.entity.CashTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Transactions {

    @Autowired
    private TransactionDao transactionDao;

    public List<CashTransaction> viewAll() {
        return transactionDao.findAll();
    }

    public List<CashTransaction> viewUserAll(long userId) {
        return transactionDao.findByAccountId(userId);
    }

    public CashTransaction viewTransaction(long transactionId) {
        return transactionDao.findById(transactionId).orElse(null);
    }
}