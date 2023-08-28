package com.nttdatateste.ecommerceteste.service;

import com.nttdatateste.ecommerceteste.entity.Cart;
import com.nttdatateste.ecommerceteste.entity.CartItem;
import com.nttdatateste.ecommerceteste.entity.Item;
import com.nttdatateste.ecommerceteste.entity.dto.CartItemDto;
import com.nttdatateste.ecommerceteste.entity.exception.CartItemNotFoundException;
import com.nttdatateste.ecommerceteste.repository.CartItemRepository;
import com.nttdatateste.ecommerceteste.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final ItemService itemService;
    private final CartRepository cartRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, CartService cartService, ItemService itemService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
        this.itemService = itemService;
        this.cartRepository = cartRepository;
    }

    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItems() {
        return StreamSupport
                .stream(cartItemRepository.findAll().spliterator(), false )
                .collect(Collectors.toList());
    }

    public CartItem getCartItem(Long id) {
        return cartItemRepository.findById(id).orElseThrow( () -> new CartItemNotFoundException(id));
    }

    @Transactional
    public Cart addCartItemToCart(Long cartId, CartItemDto cartItemDto) {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = CartItem.from(cartItemDto);
        cartItem.setCart(cart);

        Item item = itemService.getItem(cartItemDto.getItemId());

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(items -> items.getItem().getId().equals(cartItemDto.getItemId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            existingCartItem.get().setItemQuantity(existingCartItem.get().getItemQuantity() + 1);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setItem(item);
            newCartItem.setItemQuantity(1);
            newCartItem.setCart(cart);
            cart.getCartItems().add(newCartItem);
        }

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeCartItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = cartService.getCart(cartId);


        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(items -> items.getItem().getId().equals(cartItemId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            if (existingCartItem.get().getItemQuantity() > 1) {
                existingCartItem.get().setItemQuantity(existingCartItem.get().getItemQuantity() - 1);
            } else {
                cart.removeCartItem(existingCartItem.get());
            }
        }

        return cartRepository.save(cart);
    }
}
