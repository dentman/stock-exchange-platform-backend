package com.codecool.stockexchange;

import java.util.Set;

import com.codecool.stockexchange.entity.user.Account;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.repository.UserRepository;

import com.codecool.stockexchange.service.StockInfoDbUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class StockexchangeApplication {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StockInfoDbUpdateService updateService;

    @Autowired
    Symbol symbol;

    public static void main(String[] args) {
        SpringApplication.run(StockexchangeApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
            //updateApiStockInfos();
        };
    }

    /***
     * This method will only fetch data if db is not up to date for given symbol
     */
    public void updateApiStockInfos(){
        Set<String> stocks = symbol.getStocklist().keySet();
        for (String stock : stocks){
            updateService.saveOrUpdate(stock);
        }
    }


    public void createSampleUser() {
        Account account1 = Account.builder().balance(10000.0).currency("USD").build();
        User user1 = User.builder().firstName("vm").lastName("ember").account(account1).build();
        userRepository.save(user1);
    }

}
