package com.codecool.stockexchange.repository;

import com.codecool.stockexchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
