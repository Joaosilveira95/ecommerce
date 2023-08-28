package com.nttdatateste.ecommerceteste.repository;


import com.nttdatateste.ecommerceteste.entity.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
}