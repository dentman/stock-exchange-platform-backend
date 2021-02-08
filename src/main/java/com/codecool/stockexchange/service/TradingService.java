package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.exception.user.InvalidUserException;
import com.codecool.stockexchange.repository.StockRepository;
import com.codecool.stockexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TradingService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public OrderStatus handleOrder(Order order, Long user_id) {
        Optional<User> userOptional = userRepository.findById(user_id);
        User user = userOptional.orElseThrow(() -> new InvalidUserException());
        order.setUser(user);
        BigDecimal stockPrice = stockRepository.findFirstBySymbol(order.getSymbol()).getCurrentPrice();
        user.getOrders().add(order);

        switch (order.getDirection()) {
            case BUY:
                buyStock(order, stockPrice);
                break;
            case SELL:
                sellStock(order, stockPrice);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return order.getStatus();
    }

    private void handleTransaction(Order order, BigDecimal stockPrice) {
        StockTransaction transaction = order.createTransaction(stockPrice);
        User user = order.getUser();
        user.changePortfolio(transaction);
        user.getAccount().transferOrderFunding(transaction);
        user.createUserHistory(transaction);
        order.setStatus(OrderStatus.COMPLETED);
    }

    private void buyStock(Order order, BigDecimal stockPrice) {
        if (order.getLimitPrice().compareTo(stockPrice) >= 0) {
            if (order.getUser().getAccount().checkAvailableFunds(order, stockPrice)) {
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
            if (order.getUser().checkAvailableStocks(order)) {
                handleTransaction(order, stockPrice);
            } else {
                order.setStatus(OrderStatus.INSUFFICIENT_STOCK);
            }
        }
        else {
            order.setStatus(OrderStatus.LIMIT_PRICE_MISMATCH);
        }
    }
}
