package com.codecool.stockexchange.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradingServiceTest {


    @MockBean
    UserRepository userRepository;

    @MockBean
    StockRepository stockRepository;

    @Autowired
    TradingService tradingService;

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
    }

    @BeforeEach
    public void mockMethods(){
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(stockRepository.findBySymbol(ownedName)).thenReturn(Optional.of(ownedStock));
        when(stockRepository.findBySymbol(notOwnedName)).thenReturn(Optional.of(notOwnedStock));
    }

    private void createUser(){
        Account account = Account.builder().balance(BigDecimal.valueOf(100)).currency("USD").build();
        PortfolioItem portfolioItem = PortfolioItem.builder().amount(1).symbol(ownedName).build();
        Set<PortfolioItem> portfolio = new HashSet();
        portfolio.add(portfolioItem);
        user = User.builder()
                .id(Long.valueOf(1))
                .username("test@user.hu")
                .password("plainTextPassword")
                .account(account)
                .portfolio(portfolio)
                .roles(List.of())
                .build();
        user.setOrders(new HashSet<>());
        user.setUserHistoryList(new HashSet<>());
        account.setUser(user);
        portfolioItem.setUser(user);
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
    }

    @Test
    public void handleOrderSetsInsufficientStockStatus(){
        Order order = Order.builder()
                .direction(OrderDirection.SELL)
                .symbol(notOwnedName)
                .count(10)
                .status(OrderStatus.PENDING)
                .limitPrice(notOwnedPrice)
                .build();
        OrderStatus st = tradingService.handleOrder(order, user.getId());
        System.out.println(st);
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
        System.out.println(st);
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
