package com.nttdatateste.ecommerceteste.entity.dto;

import com.nttdatateste.ecommerceteste.entity.CartItem;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class CartItemDto {
    private Long   id;
    private int    itemQuantity;
    private double itemTotalPrice;
    private double itemDiscountedPrice;

    private String itemName;
    private String itemShortDescription;
    private String itemImageUrl;
    private double itemSalePrice;
    private double itemDiscount;



    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    private Long itemId;


    private PlainCartDto plainCartDto;

    public static CartItemDto from(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItem.getId());
        cartItemDto.setCreatedDate(cartItem.getCreatedDate());
        cartItemDto.setUpdatedDate(cartItem.getUpdatedDate());
        cartItemDto.setItemQuantity(cartItem.getItemQuantity());
        cartItemDto.setItemTotalPrice(cartItem.getItemQuantity() * cartItem.getItem().getSalePrice());


        double discountedPrice = (1 - cartItem.getItem().getDiscount()) * cartItemDto.getItemTotalPrice();
        cartItemDto.setItemDiscountedPrice(discountedPrice);

        cartItemDto.setItemId(cartItem.getItem().getId());
        cartItemDto.setItemName(cartItem.getItem().getName());
        cartItemDto.setItemShortDescription(cartItem.getItem().getShortDescription());
        cartItemDto.setItemImageUrl(cartItem.getItem().getImageUrl());
        cartItemDto.setItemSalePrice(cartItem.getItem().getSalePrice());
        cartItemDto.setItemDiscount(cartItem.getItem().getDiscount());

        if(Objects.nonNull(cartItem.getCart())) {
            cartItemDto.setPlainCartDto(PlainCartDto.from(cartItem.getCart()));
        }
        return cartItemDto;
    }
}
