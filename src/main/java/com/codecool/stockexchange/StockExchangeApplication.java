package com.codecool.stockexchange;

import java.math.BigDecimal;
import java.util.Set;


import com.codecool.stockexchange.entity.user.Account;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.repository.UserRepository;

import com.codecool.stockexchange.service.update.StockUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockExchangeApplication {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StockUpdateService updateService;

    @Autowired
    Symbol symbol;

    public static void main(String[] args) {
        SpringApplication.run(StockExchangeApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
            updateApiStocks();
            createSampleUser();
        };
    }

    /***
     * This method will only fetch data if db is not up to date for given symbol
     */
    public void updateApiStocks(){
        Set<String> stocks = symbol.getStocklist().keySet();
        for (String stock : stocks){
            updateService.saveOrUpdate(stock);
        }
    }


    public void createSampleUser() {
        Account account1 = Account.builder().balance(BigDecimal.valueOf(10000)).currency("USD").build();
        User user1 = User.builder().firstName("vm").lastName("ember").account(account1).build();
        account1.setUser(user1);
        userRepository.save(user1);
    }

}
