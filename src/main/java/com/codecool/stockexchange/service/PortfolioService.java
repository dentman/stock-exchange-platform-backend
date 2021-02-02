package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.codecool.stockexchange.entity.user.PortfolioItem;
import com.codecool.stockexchange.entity.user.User;
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

    public List<PortfolioItem> getPortfolioItemsForUser(Long id){
        return portfolioItemRepository.findPortfolioItemsByUser_Id(id);
    }

    public List<StockTransaction> getTransactionsForUser(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> transactionRepository.getTransactionsForUser(user)).orElse(null);
    }

}
