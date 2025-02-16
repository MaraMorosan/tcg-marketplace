package com.tcgmarketplace.rarity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RarityRepository extends JpaRepository<Rarity, Integer> {
    boolean existsByCode(String code);
}
