package com.tcgmarketplace.card;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findCardByCollectorNumber(String collectorNumber);
    Optional<Card> findCardByName(String name);

    @Query("SELECT c FROM Card c WHERE c.tcgRarity.tcg.id = :tcgId")
    List<Card> findCardsByTcgId(@Param("tcgId") Integer tcgId);

    @Query("SELECT c FROM Card c WHERE c.tcgRarity.tcg.id = :tcgId AND LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Card> searchCardsByTcgAndName(@Param("tcgId") Integer tcgId, @Param("name") String name);
}
