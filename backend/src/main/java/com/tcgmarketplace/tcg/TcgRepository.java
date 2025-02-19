package com.tcgmarketplace.tcg;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TcgRepository extends JpaRepository<Tcg, Integer> {
    Optional<Tcg> findByName(String name);

    boolean existsByName(String name);
}
