package ivan.gcashapp.service;

import ivan.gcashapp.dao.BalanceDao;
import ivan.gcashapp.entity.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckBalance {

    @Autowired
    private BalanceDao balanceDao;

    public double checkBalance(long userId) {
        List<Balance> balances = balanceDao.findByUserId(userId);
        return balances.stream().mapToDouble(Balance::getAmount).sum();
    }
}