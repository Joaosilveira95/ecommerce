package com.nttdatateste.ecommerceteste.entity;

import com.nttdatateste.ecommerceteste.entity.dto.CartDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "cart")
    private User user;

    private Long userId;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private List<CartItem> cartItems = new ArrayList<>();

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }


    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
    }

    public static Cart from(CartDto cartDto) {
        Cart cart = new Cart();
        cart.setUserId(cartDto.getUserId());
        cart.setCreatedDate(cartDto.getCreatedDate());
        cart.setUpdatedDate(cartDto.getUpdatedDate());
        return cart;
    }

}
