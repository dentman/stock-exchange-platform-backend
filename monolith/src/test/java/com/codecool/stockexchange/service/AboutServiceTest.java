package com.codecool.stockexchange.service;

import com.codecool.stockexchange.exception.resource.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AboutServiceTest {

    @Autowired
    AboutPageService aboutPageService;

    @Test
    public void returnMarkdownTextThrowsResourceNotFoundException(){
        String nonExistentLanguage = "nonExistent";
        assertThrows(ResourceNotFoundException.class,
                () -> aboutPageService.returnMarkdownText(nonExistentLanguage));
    }

}
