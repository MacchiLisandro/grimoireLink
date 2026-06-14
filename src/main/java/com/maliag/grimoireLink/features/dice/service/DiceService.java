package com.maliag.grimoireLink.features.dice.service;

import com.maliag.grimoireLink.features.dice.exception.DiceException;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
public class DiceService {

    private final SecureRandom random = new SecureRandom();

    public int rollDie(int sides) {
        if (sides <= 0) {
            throw new DiceException("the dice must be one ");
        }
        return random.nextInt(sides) + 1;
    }
}