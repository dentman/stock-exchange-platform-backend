package com.codecool.stockexchange.service;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Test
    public void saveNewUserThrowsUserExistsException() {
        User mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn("test@user.hu");

        User mockUserStored = mock(User.class);
        when(mockUserStored.getUsername()).thenReturn("test@user.hu");
        userRepository.save(mockUserStored);

        assertThrows(UserExistsException.class, () -> userService.saveNewUser(mockUser));

    }
}