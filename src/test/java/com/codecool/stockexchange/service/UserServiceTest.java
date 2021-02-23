package com.codecool.stockexchange.service;

import com.codecool.stockexchange.StockExchangeApplication;
import com.codecool.stockexchange.entity.user.Role;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.exception.user.UserExistsException;
import com.codecool.stockexchange.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@SpringBootTest(classes = StockExchangeApplication.class)
class UserServiceTest {

    @Autowired
    private UserService userService;
    private String username = "test@user.hu";


    @Test
    @Order(1)
    public void saveNewUserReturnsUser(){
        User toSave = User.builder()
                .username(username)
                .password("plainTextPassword")
                .roles(List.of())
                .build();
        User saved = userService.saveNewUser(toSave);
        assertNotNull(saved.getId());
        assertEquals(Role.ROLE_USER ,saved.getRoles().get(0));
        assertTrue(saved.getAccount().getBalance().equals(BigDecimal.valueOf(10000)));
    }

    @Test
    @Order(2)
    public void saveNewUserThrowsUserExistsException() {
        User mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn(username);

        assertThrows(UserExistsException.class, () -> userService.saveNewUser(mockUser));
    }
}