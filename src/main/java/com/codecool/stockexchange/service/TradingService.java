package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.exception.trade.InvalidOrderStatusException;
import com.codecool.stockexchange.exception.trade.InvalidSymbolFormatException;
import com.codecool.stockexchange.exception.trade.SymbolNotFoundException;
import com.codecool.stockexchange.exception.user.InvalidUserException;
import com.codecool.stockexchange.repository.StockRepository;
import com.codecool.stockexchange.repository.StockTransactionRepository;
import com.codecool.stockexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class TradingService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Transactional
    public OrderStatus handleOrder(Order order, Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(InvalidUserException::new);
        BigDecimal stockPrice = stockRepository.findBySymbol(order.getSymbol())
                .map(Stock::getCurrentPrice)
                .orElseThrow(() -> new SymbolNotFoundException(order.getSymbol()));

        order.setUser(user);
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
        StockTransaction transaction = stockTransactionRepository.saveAndFlush(order.createTransaction(stockPrice));
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
        } else {
            return;
        }
    }
}
