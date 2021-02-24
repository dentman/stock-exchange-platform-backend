package com.codecool.stockexchange.service;

import com.codecool.stockexchange.StockExchangeApplication;
import com.codecool.stockexchange.entity.stock.Stock;
import com.codecool.stockexchange.entity.stock.StockPrice;
import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderDirection;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import com.codecool.stockexchange.entity.user.Account;
import com.codecool.stockexchange.entity.user.PortfolioItem;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.repository.StockRepository;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@SpringBootTest(classes = StockExchangeApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TradingServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockRepository stockRepository;

    private TradingService tradingService;
    private User user;
    private Stock notOwnedStock;
    private Stock ownedStock;

    private final String notOwnedName = "SYMBOL";
    private final BigDecimal notOwnedPrice = BigDecimal.TEN;
    private final String ownedName = "OWNED";
    private final BigDecimal ownedPrice = BigDecimal.TEN;


    @BeforeAll
    public void init(){
        createUser();
        createStock();
        tradingService = new TradingService(stockRepository, userRepository);
    }

    private void createUser(){
        Account account = Account.builder().balance(BigDecimal.valueOf(10000)).currency("USD").build();
        PortfolioItem portfolioItem = PortfolioItem.builder().amount(1).symbol(ownedName).build();
        user = User.builder()
                .username("test@user.hu")
                .password("plainTextPassword")
                .account(account)
                .portfolio(Set.of(portfolioItem))
                .roles(List.of())
                .build();
        account.setUser(user);
        portfolioItem.setUser(user);
        userRepository.save(user);
    }

    private void createStock() {
        StockPrice notOwnedPrice = StockPrice.builder()
                .symbol(notOwnedName)
                .price(this.notOwnedPrice)
                .date(LocalDate.now())
                .stock(notOwnedStock)
                .build();
        StockPrice ownedPrice = StockPrice.builder()
                .symbol(ownedName)
                .price(this.ownedPrice)
                .date(LocalDate.now())
                .stock(ownedStock)
                .build();
        notOwnedStock = Stock.builder()
                .symbol(notOwnedName)
                .stockPrices(List.of(notOwnedPrice))
                .build();
        ownedStock = Stock.builder()
                .symbol(ownedName)
                .stockPrices(List.of(ownedPrice))
                .build();
        notOwnedPrice.setStock(notOwnedStock);
        ownedPrice.setStock(ownedStock);
        stockRepository.save(notOwnedStock);
        stockRepository.save(ownedStock);

    }

    @Test
    public void handleOrderSetsInsufficientStockStatus(){
        Order order = Order.builder()
                .direction(OrderDirection.SELL)
                .symbol(notOwnedName)
                .count(1)
                .status(OrderStatus.PENDING)
                .limitPrice(notOwnedPrice)
                .build();
        OrderStatus st = tradingService.handleOrder(order, user.getId());
        assertTrue(st.equals(OrderStatus.INSUFFICIENT_STOCK));
    }

    @Test
    public void handleOrderSetsCompletedStockStatusWhenBuying() {
        Order order = Order.builder()
                .direction(OrderDirection.BUY)
                .symbol(notOwnedName)
                .count(1)
                .status(OrderStatus.PENDING)
                .limitPrice(notOwnedPrice)
                .build();
        OrderStatus st = tradingService.handleOrder(order, user.getId());
        assertTrue(st.equals(OrderStatus.COMPLETED));
    }

    @Test
    public void handleOrderSetsCompletedStockStatusWhenSelling() {
        Order order = Order.builder()
                .direction(OrderDirection.SELL)
                .symbol(ownedName)
                .count(1)
                .status(OrderStatus.PENDING)
                .limitPrice(ownedPrice.subtract(BigDecimal.ONE))
                .build();
        OrderStatus st = tradingService.handleOrder(order, user.getId());
        System.out.println(st);
        assertTrue(st.equals(OrderStatus.COMPLETED));
    }

    @Test
    public void checkOrderThrowsNumberFormatException() {
        Order order = Order.builder()
                .direction(OrderDirection.SELL)
                .symbol(notOwnedName)
                .count(0)
                .status(OrderStatus.PENDING)
                .limitPrice(notOwnedPrice)
                .build();
        assertThrows(NumberFormatException.class, () -> tradingService.checkOrder(order));
    }

}