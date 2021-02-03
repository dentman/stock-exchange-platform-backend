package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.repository.TransactionRepository;
import com.codecool.stockexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    public List<StockTransaction> getTransactionsForUserPerSymbol(Long id, String symbol){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> transactionRepository.getTransactionsForUserForSymbol(user, symbol)).orElse(null);
    }

}
