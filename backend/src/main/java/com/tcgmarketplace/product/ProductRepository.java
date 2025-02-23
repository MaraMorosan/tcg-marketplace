package com.tcgmarketplace.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.expansion.name) = LOWER(:expName)")
    List<Product> findByExpansionName(@Param("expName") String expansionName);

    @Query(value = "SELECT * FROM dev_tcg_marketplace.product WHERE LOWER(product_name) ILIKE CONCAT('%', :name, '%') LIMIT :limit", nativeQuery = true)
    List<Product> searchByName(@Param("name") String name, @Param("limit") int limit);

    @Query(value = "SELECT COUNT(*) FROM dev_tcg_marketplace.product WHERE LOWER(product_name) ILIKE CONCAT('%', :name, '%')", nativeQuery = true)
    int countByName(@Param("name") String name);

}
