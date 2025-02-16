package com.tcgmarketplace.card;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findCardByCollectorNumber(String collectorNumber);
    Optional<Card> findCardByName(String name);

}
