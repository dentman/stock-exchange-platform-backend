package com.codecool.stockexchange.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    //(mappedBy = "account", cascade = CascadeType.PERSIST)
    @OneToOne
    private User user;

    private BigDecimal balance;
    private String currency;


}
