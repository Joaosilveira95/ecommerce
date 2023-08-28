package com.nttdatateste.ecommerceteste.service;

import com.nttdatateste.ecommerceteste.entity.Cart;
import com.nttdatateste.ecommerceteste.entity.User;
import com.nttdatateste.ecommerceteste.entity.exception.CartNotFoundException;
import com.nttdatateste.ecommerceteste.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart addCart(Cart cart, User user) {
        cart.setUser(user); // Associate the cart with the user
        user.setCart(cart);
        return cartRepository.save(cart);
    }

    public List<Cart> getCarts() {
        return StreamSupport
                .stream(cartRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow(() ->
                new CartNotFoundException(id));
    }

    public Cart deleteCart(Long id) {
        Cart cart = getCart(id);
        cartRepository.delete(cart);
        return cart;
    }

    @Transactional
    public Cart editCart(Long id, Cart cart) {
        Cart cartToEdit = getCart(id);
        cartToEdit.setUserId(cart.getUserId());
        return cartToEdit;
    }

}
