package com.maliag.grimoireLink.features.monsters;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class MonsterNotFoundException extends ResourceNotFoundException {
    public MonsterNotFoundException(String message) {
        super(message);
    }
}
