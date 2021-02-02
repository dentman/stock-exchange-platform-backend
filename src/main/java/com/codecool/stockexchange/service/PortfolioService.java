package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.codecool.stockexchange.entity.user.Account;
import com.codecool.stockexchange.entity.user.PortfolioItem;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.repository.AccountRepository;
import com.codecool.stockexchange.repository.PortfolioItemRepository;
import com.codecool.stockexchange.repository.TransactionRepository;
import com.codecool.stockexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PortfolioItemRepository portfolioItemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    public List<PortfolioItem> getPortfolioItemsForUser(Long id){
        return portfolioItemRepository.findPortfolioItemsByUser_Id(id);
    }

    public List<StockTransaction> getTransactionsForUser(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> transactionRepository.getTransactionsForUser(user)).orElse(null);
    }

    public List<StockTransaction> getTransactionsForUserPerSymbol(Long id, String symbol){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> transactionRepository.getTransactionsForUserForSymbol(user, symbol)).orElse(null);
    }

    public Account getAccountForUser(Long user_id) {
        Optional<User> userOptional = userRepository.findById(user_id);
        return userOptional.map(user -> accountRepository.findByUser(user)).orElse(null);
    }
}
