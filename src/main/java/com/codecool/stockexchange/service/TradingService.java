package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.codecool.stockexchange.entity.user.Account;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.exception.trade.InvalidOrderStatusException;
import com.codecool.stockexchange.exception.trade.InvalidSymbolFormatException;
import com.codecool.stockexchange.exception.trade.SymbolNotFoundException;
import com.codecool.stockexchange.exception.user.InvalidUserException;
import com.codecool.stockexchange.repository.StockRepository;
import com.codecool.stockexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class TradingService {

    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    @Autowired
    public TradingService(StockRepository stockRepository, UserRepository userRepository) {
        this.stockRepository = stockRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderStatus handleOrder(Order order, Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(InvalidUserException::new);
        BigDecimal stockPrice = stockRepository.findBySymbol(order.getSymbol())
                .map(Stock::getCurrentPrice)
                .orElseThrow(() -> new SymbolNotFoundException(order.getSymbol()));
        order.setUser(user);
        user.getOrders().add(order);

        try {
            switch (order.getDirection()) {
                case BUY:
                    buyStock(order, stockPrice);
                    break;
                case SELL:
                    sellStock(order, stockPrice);
                    break;
            }
            return order.getStatus();
        } catch (Exception e){
            return OrderStatus.DATABASE_PROBLEM;
        }
    }

    private void handleTransaction(Order order, BigDecimal stockPrice) {
        StockTransaction transaction = createTransaction(order, stockPrice);
        order.setStockTransaction(transaction);
        User user = order.getUser();
        user.changePortfolio(transaction);
        user.getAccount().transferOrderFunding(transaction);
        user.createUserHistory(transaction);
        order.setStatus(OrderStatus.COMPLETED);
    }

    private void buyStock(Order order, BigDecimal stockPrice) {
        if (order.getLimitPrice().compareTo(stockPrice) >= 0) {
            Account account = order.getUser().getAccount();
            BigDecimal requiredBalance = stockPrice.multiply(BigDecimal.valueOf(order.getCount()));
            if (account.getBalance().compareTo(requiredBalance) >= 0) {
                handleTransaction(order, stockPrice);
            } else {
                order.setStatus(OrderStatus.INSUFFICIENT_FUND);
            }
        }
        else {
            order.setStatus(OrderStatus.LIMIT_PRICE_MISMATCH);
        }
    }

    private void sellStock(Order order, BigDecimal stockPrice) {
        if (order.getLimitPrice().compareTo(stockPrice) <= 0) {
            order.getUser().getPortfolioItem(order.getSymbol())
                    .ifPresentOrElse((p) -> {
                        if (order.getCount() <= p.getAmount()) handleTransaction(order, stockPrice);
                        else order.setStatus(OrderStatus.INSUFFICIENT_STOCK);
                    }, () -> order.setStatus(OrderStatus.INSUFFICIENT_STOCK));
        }
        else {
            order.setStatus(OrderStatus.LIMIT_PRICE_MISMATCH);
        }
    }

    public void checkOrder(Order order) {
        String symbol = order.getSymbol();

        if (symbol == null || symbol.equals("") || symbol.chars().anyMatch(c -> !Character.isUpperCase((char) c))) {
            throw new InvalidSymbolFormatException();
        } else if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new InvalidOrderStatusException();
        } else if (Arrays.stream(OrderStatus.values())
                .noneMatch(orderStatus -> orderStatus.equals(order.getStatus()))) {
            throw new InvalidOrderStatusException();
        } else if (order.getCount() <= 0 || order.getLimitPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NumberFormatException("Order count and price must be positive numbers!");
        }
    }

    private StockTransaction createTransaction(Order order, BigDecimal stockPrice) {
        StockTransaction transaction = StockTransaction.builder()
                .order(order)
                .symbol(order.getSymbol())
                .transactionTime(LocalDateTime.now())
                .stockPrice(stockPrice)
                .build();

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
        return transaction;
    }
}
