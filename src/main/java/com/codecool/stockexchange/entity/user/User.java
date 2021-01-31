package com.codecool.stockexchange.entity.user;

import javax.persistence.*;

import com.codecool.stockexchange.entity.trade.Order;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
    private Account account;

    @OneToMany( mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
    List<PortfolioItem> portfolio = new ArrayList<>();

    @OneToMany( mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
    List<Order> orders = new ArrayList<>();

    String firstName;
    String lastName;

}
