package com.nttdatateste.ecommerceteste.entity.exception;

import java.text.MessageFormat;

public class CartItemIsAlreadyAssignedException  extends RuntimeException{
    public CartItemIsAlreadyAssignedException(final Long itemId, final Long cartId) {
        super(MessageFormat.format("CartItem: {0} is already assigned to cart: {1}", itemId, cartId));

    }
}
