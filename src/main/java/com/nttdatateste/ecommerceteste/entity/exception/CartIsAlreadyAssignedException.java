package com.nttdatateste.ecommerceteste.entity.exception;

import java.text.MessageFormat;

public class CartIsAlreadyAssignedException extends RuntimeException{
    public CartIsAlreadyAssignedException(final Long cartId, final Long userId) {
        super(MessageFormat.format("Cart: {0} is already assigned to user: {1}", cartId, userId));

    }
}
