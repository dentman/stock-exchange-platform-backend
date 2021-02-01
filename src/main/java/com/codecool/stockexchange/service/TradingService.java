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
                    if (checkAvailableFunds(order, user, stockPrice)) {
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
                    if (checkAvailableStocks(order, user)) {
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
        PortfolioItem tradedStock;
        Optional<PortfolioItem> portfolioItemOptional = getPortfolioItem(user, transaction.getSymbol());

        if (portfolioItemOptional.isPresent()) {
            tradedStock = portfolioItemOptional.get();
            tradedStock.setAmount(tradedStock.getAmount() + transaction.getStockChange());
        }
        else {
            tradedStock = new PortfolioItem();
            tradedStock.setSymbol(transaction.getSymbol());
            tradedStock.setAmount(transaction.getStockChange());
            tradedStock.setUser(user);
            user.getPortfolio().add(tradedStock);
        }
    }

    private void transferOrderFunding(User user, StockTransaction transaction) {
        Account account = user.getAccount();
        account.setBalance(account.getBalance().add(transaction.getAccountBalanceChange()));
    }

    private boolean checkAvailableFunds(Order order, User user, BigDecimal stockPrice) {
        BigDecimal requiredBalance = stockPrice.multiply(BigDecimal.valueOf(order.getCount()));
        return user.getAccount().getBalance().compareTo(requiredBalance) >= 0;
    }

    private boolean checkAvailableStocks(Order order, User user) {
        Optional<PortfolioItem> portfolioItemOptional = getPortfolioItem(user, order.getSymbol());
        if (portfolioItemOptional.isPresent()) {
            return order.getCount() <= portfolioItemOptional.get().getAmount();
        }
        else {
            return false;
        }
    }

    private Optional<PortfolioItem> getPortfolioItem(User user, String symbol) {
        return user.getPortfolio()
                .stream()
                .filter(item -> item.getSymbol().equals(symbol))
                .findFirst();
    }
}
