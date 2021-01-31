package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.trade.OrderItem;
import com.codecool.stockexchange.entity.trade.Status;
import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.codecool.stockexchange.entity.user.Account;
import com.codecool.stockexchange.entity.user.PortfolioItem;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.repository.AccountRepository;
import com.codecool.stockexchange.repository.StockInfoRepository;
import com.codecool.stockexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TradingService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    StockInfoRepository stockInfoRepository;

    @Transactional
    public Status handleOrder(OrderItem order) {
        // validate: whatever it means
        // check if status is pending
        // check price of symbol vs limitPrice-> succ/fail
        // BUY: check user account balance vs amount*price -> succ/fail
        // SELL: check user stock balance vs amount -> succ/fail

        BigDecimal stockPrice = stockInfoRepository.findFirstBySymbol("GM").getCurrentPrice();
        User user = userRepository.findAll().get(0);
        user.getOrders().add(order);
        order.setUser(user);

        switch (order.getDirection()) {
            case BUY:
                if (checkAvailableFunds()) {
                    handleTransaction(order, user);
                }
                break;
            case SELL:
                if (checkAvailableStocks()) {
                    handleTransaction(order, user);
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        return Status.CHECK_COMPLETE;
    }

    private Status handleTransaction(OrderItem order, User user) {
        StockTransaction stockTransaction = new StockTransaction();
        stockTransaction.setOrder(order);
        order.setStockTransaction(stockTransaction);

        Account account = user.getAccount();
        account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(100)));
        PortfolioItem portfolioItemToChange;

        List<PortfolioItem> portfolioItems = user.getPortfolio();
        Optional<PortfolioItem> portfolioItemOptional = portfolioItems.stream().filter(item -> item.getSymbol().equals(order.getSymbol())).findFirst();
        if (portfolioItemOptional.isPresent()) {
            portfolioItemToChange = portfolioItemOptional.get();
            portfolioItemToChange.setAmount(portfolioItemToChange.getAmount() + 8);
        }
        else {
            portfolioItemToChange = new PortfolioItem();
            portfolioItemToChange.setSymbol(order.getSymbol());
            portfolioItemToChange.setAmount(order.getCount());
            portfolioItemToChange.setUser(user);
            user.getPortfolio().add(portfolioItemToChange);
        }
        order.setStatus(Status.COMPLETED);
        return order.getStatus();
    }

    private boolean checkAvailableFunds() {
        return true;
    }

    private boolean checkAvailableStocks() {
        return true;
    }
}
