package com.codecool.stockexchange;

import java.math.BigDecimal;
import java.util.List;


import com.codecool.stockexchange.entity.StockBaseData;
import com.codecool.stockexchange.entity.user.Account;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.repository.StockBaseDataRepository;
import com.codecool.stockexchange.repository.UserRepository;

import com.codecool.stockexchange.service.update.StockUpdateService;
import com.codecool.stockexchange.util.TextReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    StockBaseDataRepository stockBaseDataRepository;

    @Value("${stocklist.csv.path}")
    String path;

    public static void main(String[] args) {
        SpringApplication.run(StockExchangeApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
//            setStockFromCsvToDatabase();
//            updateApiStocks();
//            createSampleUser();
        };
    }

    /***
     * This method will only fetch data if db is not up to date for given symbol
     */
    public void updateApiStocks(){
        List<StockBaseData> stocks = stockBaseDataRepository.findAll();
        for (StockBaseData stock : stocks){
            updateService.saveOrUpdate(stock.getSymbol());
        }
    }

    private void setStockFromCsvToDatabase(){
        TextReader reader = new TextReader(path);
        List<StockBaseData> data = reader.readLines();
        data.forEach(d -> stockBaseDataRepository.save(d));
    }


    public void createSampleUser() {
        Account account1 = Account.builder().balance(BigDecimal.valueOf(10000)).currency("USD").build();
        User user1 = User.builder().firstName("test").lastName("person").account(account1).build();
        account1.setUser(user1);
        userRepository.save(user1);
    }

}
