package com.codecool.stockexchange.service;

import com.codecool.stockexchange.StockExchangeApplication;
import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.entity.stock.StockPrice;
import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderDirection;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.repository.StockRepository;
import com.codecool.stockexchange.repository.StockTransactionRepository;
import com.codecool.stockexchange.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@SpringBootTest(classes = StockExchangeApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TradingServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    StockTransactionRepository stockTransactionRepository;
    @Autowired
    StockRepository stockRepository;

    TradingService tradingService;
    User user;
    Stock stock;

    @BeforeAll
    public void init(){
        user = User.builder()
                .username("test@user.hu")
                .password("plainTextPassword")
                .roles(List.of())
                .build();
        userRepository.save(user);
        StockPrice price = StockPrice.builder()
                .symbol("SYMBOL")
                .price(BigDecimal.valueOf(10.00))
                .date(LocalDate.now())
                .stock(stock)
                .build();
        stock = Stock.builder()
                .symbol("SYMBOL")
                .stockPrices(List.of(price))
                .build();
        price.setStock(stock);
        stockRepository.save(stock);
        tradingService = new TradingService(stockRepository,
                                            userRepository,
                                            stockTransactionRepository);
    }

    @Test
    public void handleOrderSetsInsufficientStockStatus(){
        Order order = Order.builder()
                .direction(OrderDirection.SELL)
                .symbol("SYMBOL")
                .count(1)
                .status(OrderStatus.PENDING)
                .limitPrice(BigDecimal.valueOf(10.00))
                .build();
        OrderStatus st = tradingService.handleOrder(order, user.getId());
        assertTrue(st.equals(OrderStatus.INSUFFICIENT_STOCK));
    }

    @Test
    public void checkOrderThrowsNumberFormatException() {
        Order order = Order.builder()
                .direction(OrderDirection.SELL)
                .symbol("SYMBOL")
                .count(0)
                .status(OrderStatus.PENDING)
                .limitPrice(BigDecimal.valueOf(10.00))
                .build();
        assertThrows(NumberFormatException.class, () -> tradingService.checkOrder(order));
    }

}