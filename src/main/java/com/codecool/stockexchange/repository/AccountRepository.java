package com.codecool.stockexchange.repository;

import com.codecool.stockexchange.entity.user.Account;
import com.codecool.stockexchange.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUser(User user);
}
