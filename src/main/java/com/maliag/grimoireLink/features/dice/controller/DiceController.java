package com.maliag.grimoireLink.features.dice.controller;

import com.maliag.grimoireLink.features.dice.service.DiceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dice")
public class DiceController {

    private final DiceService diceService;

    public DiceController(DiceService diceService) {
        this.diceService = diceService;
    }

    @GetMapping("/roll")
    public int roll(@RequestParam int sides) {
        return diceService.rollDie(sides);
    }
}