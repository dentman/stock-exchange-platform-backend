package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.trade.OrderItem;
import com.codecool.stockexchange.entity.trade.Status;
import org.springframework.stereotype.Service;

@Service
public class TradingService {
    public Status handleOrder(OrderItem order) {
        // validate: whatever it means
        // check if status is pending
        // check price of symbol vs limitPrice-> succ/fail
        // BUY: check user account balance vs amount*price -> succ/fail
        // SELL: check user stock balance vs amount -> succ/fail


        System.out.println(order);
        return handleTransaction(order);
    }

    private Status handleTransaction(OrderItem order) {
        // -> create Transaction
        // modify db:
        // portfolio_item (new item/update):
        // account (update)
        return Status.COMPLETED;
    }


}
