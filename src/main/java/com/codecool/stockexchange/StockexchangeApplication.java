package com.codecool.stockexchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.codecool.stockexchange.entity.Account;
import com.codecool.stockexchange.entity.StockInfo;
import com.codecool.stockexchange.entity.User;
import com.codecool.stockexchange.repository.AccountRepository;
import com.codecool.stockexchange.repository.UserRepository;
import com.codecool.stockexchange.service.ApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class StockexchangeApplication {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApiService apiService;

    @Autowired
    Symbol symbol;

    public static void main(String[] args) {
        SpringApplication.run(StockexchangeApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
            Account account1 = Account.builder().balance(10000.0).currency("USD").build();
            User user1 = User.builder().firstName("vm").lastName("ember").account(account1).build();
            userRepository.save(user1);

            Set<String> stocks = symbol.getStocklist().keySet();

            List<StockInfo> stockInfos = new ArrayList<>();

        };
    }

}
