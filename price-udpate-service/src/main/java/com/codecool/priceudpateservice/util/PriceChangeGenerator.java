package com.codecool.priceudpateservice.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PriceChangeGenerator {

    public double generateChange() {
        Random random = new Random();
        int direction = random.nextInt(100) < 52 ? 1 : -1;
        double change = (double) random.nextInt(5) / 1000 * direction;
        return change;
    }
}
