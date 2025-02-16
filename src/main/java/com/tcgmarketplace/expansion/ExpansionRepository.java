package com.tcgmarketplace.expansion;

import com.tcgmarketplace.tcg.Tcg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpansionRepository extends JpaRepository<Expansion, Integer> {
    Optional<Expansion> getExpansionByName(String name);
    Optional<Expansion> findByTcgAndCode(Tcg tcg, String code);

    boolean existsByName(String name);
}
