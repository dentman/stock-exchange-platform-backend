package com.codecool.stockexchange.service;

import com.codecool.stockexchange.StockExchangeApplication;
import com.codecool.stockexchange.entity.user.Role;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.exception.user.UserExistsException;
import com.codecool.stockexchange.repository.UserRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@SpringBootTest(classes = StockExchangeApplication.class)
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Test
    public void saveNewUserThrowsUserExistsException() {
        String username = "test@user.hu";
        userRepository.save(User.builder()
                                .username(username)
                                .password("string")
                                .roles(List.of(Role.ROLE_USER))
                                .build());
        User mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn(username);

        assertThrows(UserExistsException.class, () -> userService.saveNewUser(mockUser));

    }
}