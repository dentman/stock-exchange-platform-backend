package com.codecool.stockexchange.entity.user;

import javax.persistence.*;

import com.codecool.stockexchange.entity.trade.Order;
import com.codecool.stockexchange.entity.trade.StockTransaction;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    private String firstName;
    private String lastName;
    @NonNull
    private String username;
    @NonNull
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @NonNull
    private List<Role> roles;


    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
    private Account account;

    @OneToMany( mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
    List<PortfolioItem> portfolio = new ArrayList<>();

    @OneToMany( mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
    List<Order> orders = new ArrayList<>();

    @OneToMany( mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<UserHistory> userHistoryList = new ArrayList<>();


    public Optional<PortfolioItem> getPortfolioItem(String symbol) {
        return getPortfolio()
                .stream()
                .filter(item -> item.getSymbol().equals(symbol))
                .findFirst();
    }


    public void changePortfolio(StockTransaction transaction) {
        PortfolioItem tradedStock;
        Optional<PortfolioItem> portfolioItemOptional = getPortfolioItem(transaction.getSymbol());

        if (portfolioItemOptional.isPresent()) {
            tradedStock = portfolioItemOptional.get();
            tradedStock.setAmount(tradedStock.getAmount() + transaction.getStockChange());
        }
        else {
            tradedStock = new PortfolioItem();
            tradedStock.setSymbol(transaction.getSymbol());
            tradedStock.setAmount(transaction.getStockChange());
            tradedStock.setUser(this);
            getPortfolio().add(tradedStock);
        }
    }

    public void createUserHistory(StockTransaction transaction){
        UserHistory userHistory = UserHistory.builder()
                .balanceAfter(account.getBalance())
                .stockChange(transaction.getStockChange())
                .transactionId(transaction.getId())
                .transactionTime(transaction.getTransactionTime())
                .stockPrice(transaction.getStockPrice())
                .user(this)
                .symbol(transaction.getSymbol())
                .stockCountAfter(getPortfolioItem(transaction.getSymbol()).map(PortfolioItem::getAmount).orElse(0))
                .balanceAfter(getAccount().getBalance())
                .build();
        userHistoryList.add(userHistory);
    }

}
