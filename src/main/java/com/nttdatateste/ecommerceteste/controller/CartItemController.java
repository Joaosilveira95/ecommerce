package com.nttdatateste.ecommerceteste.controller;

import com.nttdatateste.ecommerceteste.entity.Cart;
import com.nttdatateste.ecommerceteste.entity.CartItem;
import com.nttdatateste.ecommerceteste.entity.dto.CartDto;
import com.nttdatateste.ecommerceteste.entity.dto.CartItemDto;
import com.nttdatateste.ecommerceteste.service.CartItemService;
import com.nttdatateste.ecommerceteste.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/carts/{cartId}/cartItems")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService, ItemService itemService) {
        this.cartItemService = cartItemService;
    }

    @Operation(summary = "Get CartItem By ID", description = "Get the Cart Item from ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<CartItemDto> getCartItem(@PathVariable final Long id) {
        CartItem cartItem = cartItemService.getCartItem(id);
        return new ResponseEntity<>(CartItemDto.from(cartItem), HttpStatus.OK);
    }

    @Operation(summary = "Add Items to Cart using Cart ID", description = "Add Items to CartItem")
    @PostMapping(value = "/add")
    public ResponseEntity<CartDto> addCartItemToCart(@PathVariable final Long cartId, @RequestBody final CartItemDto cartItemDto) {
        Cart cart = cartItemService.addCartItemToCart(cartId, cartItemDto);
        return new ResponseEntity<>(CartDto.from(cart), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{cartItemId}")
    public ResponseEntity<CartDto> deleteItemFromCart(@PathVariable final Long cartId, @PathVariable final Long cartItemId) {
        Cart cart = cartItemService.removeCartItemFromCart(cartId, cartItemId);
        return new ResponseEntity<>(CartDto.from(cart), HttpStatus.OK);
    }
}
