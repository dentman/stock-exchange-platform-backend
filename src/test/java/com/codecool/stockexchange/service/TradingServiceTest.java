package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradingServiceTest {

    @Autowired
    TradingService tradingService;

    @Test
    public void checkOrderThrowsNumberFormatException() {
        List<Order> mockOrders = createMockOrdersForNumberFormatExceptionTest();
        mockOrders.forEach(order -> {
            assertThrows(NumberFormatException.class,
                    () -> tradingService.checkOrder(order));
        });
    }

    private List<Order> createMockOrdersForNumberFormatExceptionTest(){
        List<Order> mockOrders = new LinkedList<>();
        Order countZero = mock(Order.class);
        when(countZero.getCount()).thenReturn(0);
        when(countZero.getLimitPrice()).thenReturn(BigDecimal.ONE);
        when(countZero.getSymbol()).thenReturn("UPPER");
        when(countZero.getStatus()).thenReturn(OrderStatus.PENDING);
        mockOrders.add(countZero);

        Order priceZero = mock(Order.class);
        when(priceZero.getCount()).thenReturn(1);
        when(priceZero.getLimitPrice()).thenReturn(BigDecimal.ZERO);
        when(priceZero.getSymbol()).thenReturn("UPPER");
        when(priceZero.getStatus()).thenReturn(OrderStatus.PENDING);
        mockOrders.add(priceZero);

        return mockOrders;
    }
}