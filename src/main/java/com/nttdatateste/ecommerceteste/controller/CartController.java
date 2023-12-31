package com.nttdatateste.ecommerceteste.controller;

import com.nttdatateste.ecommerceteste.entity.Cart;
import com.nttdatateste.ecommerceteste.entity.dto.CartDto;
import com.nttdatateste.ecommerceteste.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Get All Carts", description = "Get All Carts in DB")
    @GetMapping
    public ResponseEntity<List<CartDto>> getCarts() {
        List<Cart> carts = cartService.getCarts();
        List<CartDto> cartsDto = carts.stream().map(CartDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(cartsDto, HttpStatus.OK);
    }
    @Operation(summary = "Get Cart By ID", description = "Get Cart from ID in DB")
    @GetMapping(value = "{id}")
    public ResponseEntity<CartDto> getCart(@PathVariable final Long id) {
        Cart cart = cartService.getCart(id);
        return new ResponseEntity<>(CartDto.from(cart) , HttpStatus.OK);
    }


}
