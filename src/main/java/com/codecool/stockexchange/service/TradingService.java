package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderStatus;
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
import java.util.Optional;

@Service
public class TradingService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    StockInfoRepository stockInfoRepository;

    @Transactional
    public OrderStatus handleOrder(Order order) {
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
                        createTransaction(order, user, stockPrice);
                    } else {
                        order.setStatus(OrderStatus.INSUFFICIENT_FUND);
                    }
                }
                else {
                    order.setStatus(OrderStatus.LIMIT_PRICE_MISMATCH);
                }
                break;
            case SELL:
                if (order.getLimitPrice().compareTo(stockPrice) <= 0) {
                    if (checkAvailableStocks()) {
                        createTransaction(order, user, stockPrice);
                    } else {
                        order.setStatus(OrderStatus.INSUFFICIENT_STOCK);
                    }
                }
                else {
                    order.setStatus(OrderStatus.LIMIT_PRICE_MISMATCH);
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        return order.getStatus();
    }

    private void createTransaction(Order order, User user, BigDecimal stockPrice) {
        StockTransaction transaction = new StockTransaction();
        transaction.setOrder(order);
        order.setStockTransaction(transaction);
        transaction.setSymbol(order.getSymbol());
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setStockPrice(stockPrice);
        switch (order.getDirection()) {
            case BUY:
                transaction.setAccountBalanceChange(stockPrice.multiply(BigDecimal.valueOf(-1 * order.getCount())));
                transaction.setStockChange(order.getCount());
                break;
            case SELL:
                transaction.setAccountBalanceChange(stockPrice.multiply(BigDecimal.valueOf(order.getCount())));
                transaction.setStockChange(-1 * order.getCount());
                break;
        }
        changePortfolio(user, transaction);
        transferOrderFunding(user, transaction);
        order.setStatus(OrderStatus.COMPLETED);
    }

    private void changePortfolio(User user, StockTransaction transaction) {
        PortfolioItem tradedPortfolioItem;
        Optional<PortfolioItem> portfolioItemOptional = user.getPortfolio()
                .stream()
                .filter(item -> item.getSymbol().equals(transaction.getSymbol()))
                .findFirst();

        if (portfolioItemOptional.isPresent()) {
            tradedPortfolioItem = portfolioItemOptional.get();
            tradedPortfolioItem.setAmount(tradedPortfolioItem.getAmount() + transaction.getStockChange());
        }
        else {
            tradedPortfolioItem = new PortfolioItem();
            tradedPortfolioItem.setSymbol(transaction.getSymbol());
            tradedPortfolioItem.setAmount(transaction.getStockChange());
            tradedPortfolioItem.setUser(user);
            user.getPortfolio().add(tradedPortfolioItem);
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
