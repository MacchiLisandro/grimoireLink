package com.maliag.grimoireLink.features.itemsXCharacter.exceptions;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class ItemNotFoundException extends ResourceNotFoundException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}