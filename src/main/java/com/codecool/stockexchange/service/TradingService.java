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
import java.time.LocalDateTime;
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
        // TODO: validate: whatever it means
        // TODO: check if status is pending
        // TODO: handle more exceptions

        BigDecimal stockPrice = stockInfoRepository.findFirstBySymbol(order.getSymbol()).getCurrentPrice();
        User user = userRepository.findAll().get(0);
        user.getOrders().add(order);
        order.setUser(user);

        switch (order.getDirection()) {
            case BUY:
                if (order.getLimitPrice().compareTo(stockPrice) >= 0) {
                    if (checkAvailableFunds()) {
                        handleTransaction(order, user, stockPrice);
                    } else {
                        order.setStatus(Status.INSUFFICIENT_FUND);
                    }
                }
                else {
                    order.setStatus(Status.LIMIT_PRICE_MISMATCH);
                }
                break;
            case SELL:
                if (order.getLimitPrice().compareTo(stockPrice) <= 0) {
                    if (checkAvailableStocks()) {
                        handleTransaction(order, user, stockPrice);
                    } else {
                        order.setStatus(Status.INSUFFICIENT_STOCK);
                    }
                }
                else {
                    order.setStatus(Status.LIMIT_PRICE_MISMATCH);
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        return order.getStatus();
    }

    private Status handleTransaction(OrderItem order, User user, BigDecimal stockPrice) {
        StockTransaction transaction = new StockTransaction();
        transaction.setOrder(order);
        order.setStockTransaction(transaction);
        transaction.setSymbol(order.getSymbol());
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setTradeStockPrice(stockPrice);
        switch (order.getDirection()) {
            case BUY:
                transaction.setAccountBalanceChange(stockPrice.multiply(BigDecimal.valueOf(-1 * order.getCount())));
                transaction.setPortfolioItemChange(order.getCount());
                break;
            case SELL:
                transaction.setAccountBalanceChange(stockPrice.multiply(BigDecimal.valueOf(order.getCount())));
                transaction.setPortfolioItemChange(-1 * order.getCount());
                break;
        }
        changePortfolio(user, transaction);
        transferOrderFunding(user, transaction);
        order.setStatus(Status.COMPLETED);
        return order.getStatus();
    }

    private void changePortfolio(User user, StockTransaction transaction) {
        PortfolioItem tradedPotfolioItem;
        Optional<PortfolioItem> portfolioItemOptional = user.getPortfolio()
                .stream()
                .filter(item -> item.getSymbol().equals(transaction.getSymbol()))
                .findFirst();

        if (portfolioItemOptional.isPresent()) {
            tradedPotfolioItem = portfolioItemOptional.get();
            tradedPotfolioItem.setAmount(tradedPotfolioItem.getAmount() + transaction.getPortfolioItemChange());
        }
        else {
            tradedPotfolioItem = new PortfolioItem();
            tradedPotfolioItem.setSymbol(transaction.getSymbol());
            tradedPotfolioItem.setAmount(transaction.getPortfolioItemChange());
            tradedPotfolioItem.setUser(user);
            user.getPortfolio().add(tradedPotfolioItem);
        }
    }

    private void transferOrderFunding(User user, StockTransaction transaction) {
        Account account = user.getAccount();
        account.setBalance(account.getBalance().add(transaction.getAccountBalanceChange()));
    }

    private boolean checkAvailableFunds() {
        return true;
    }

    private boolean checkAvailableStocks() {
        return true;
    }
}
