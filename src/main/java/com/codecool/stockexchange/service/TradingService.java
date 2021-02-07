package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TradingService {

    @Autowired
    private StockRepository stockRepository;

    @Transactional
    public OrderStatus handleOrder(Order order) {

        BigDecimal stockPrice = stockRepository.findFirstBySymbol(order.getSymbol()).getCurrentPrice();
        order.getUser().getOrders().add(order);

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
