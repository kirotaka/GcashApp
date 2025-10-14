package ivan.gcashapp.service;

import ivan.gcashapp.entity.Balance;
import ivan.gcashapp.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckBalance {

    @Autowired
    private BalanceRepository balanceRepository;

    public double checkBalance(long userId) {
        List<Balance> balances = balanceRepository.findByUserId(userId);
        return balances.stream().mapToDouble(Balance::getAmount).sum();
    }
}