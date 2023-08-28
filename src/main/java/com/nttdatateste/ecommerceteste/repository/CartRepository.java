package com.nttdatateste.ecommerceteste.repository;

import com.nttdatateste.ecommerceteste.entity.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
}
