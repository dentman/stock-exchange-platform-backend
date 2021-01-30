package com.codecool.stockexchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.codecool.stockexchange.apimodel.ChartDataPoint;
import com.codecool.stockexchange.apimodel.NewsItemAPI;
import com.codecool.stockexchange.entity.Account;
import com.codecool.stockexchange.entity.StockInfo;
import com.codecool.stockexchange.entity.StockPrice;
import com.codecool.stockexchange.entity.User;
import com.codecool.stockexchange.repository.AccountRepository;
import com.codecool.stockexchange.repository.StockInfoRepository;
import com.codecool.stockexchange.repository.UserRepository;
import com.codecool.stockexchange.service.ExternalApiService;

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
    StockInfoRepository stockInfoRepository;

    @Autowired
    ExternalApiService apiService;

    @Autowired
    Symbol symbol;

    public static void main(String[] args) {
        SpringApplication.run(StockexchangeApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
//            System.out.println("start to fetch data");
//            getApiStockInfos();
//            System.out.println("finished fetching data");
        };
    }

    public void getApiStockInfos() {
        Set<String> stocks = symbol.getStocklist().keySet();

        List<StockInfo> stockInfos = new ArrayList<>();

        for (String stock : stocks) {
            StockInfo stockInfo = new StockInfo(apiService.getQuoteBySymbol(stock));
            stockInfos.add(stockInfo);
            ChartDataPoint[] stockPrices = apiService.getChartDataBySymbol(stock);
            for (ChartDataPoint chartDataPoint : stockPrices) {
                StockPrice stockPrice = new StockPrice(chartDataPoint);
                stockPrice.setStockInfo(stockInfo);
                stockInfo.addStockPrice(stockPrice);
            }
            List<NewsItemAPI> newsItemAPIList = apiService.getNewsBySymbol(stock);
            newsItemAPIList.forEach(n -> {
                com.codecool.stockexchange.entity.NewsItem item = new com.codecool.stockexchange.entity.NewsItem(n);
                item.setStockInfo(stockInfo);
                stockInfo.addNewsItem(item);
            });
        }

        stockInfoRepository.saveAll(stockInfos);
    }

    public void createSampleUser() {
        Account account1 = Account.builder().balance(10000.0).currency("USD").build();
        User user1 = User.builder().firstName("vm").lastName("ember").account(account1).build();
        userRepository.save(user1);
    }

}
