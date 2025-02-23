package com.tcgmarketplace.basket;

import com.tcgmarketplace.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    Optional<Basket> findByUser(User user);
}
