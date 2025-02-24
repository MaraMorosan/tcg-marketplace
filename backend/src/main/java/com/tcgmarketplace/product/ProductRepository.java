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

    @Query(value = "SELECT * FROM dev_tcg_marketplace.product WHERE parent_product_id IS NULL", nativeQuery = true)
    List<Product> findAllCategories();

    @Query(value = """
    SELECT\s
      p.product_id,
      p.product_name,
      p.product_type,
      p.expansion_id,
      e.name AS "expansionName",
      p.card_id,
      p.parent_product_id,
      p.created_at,
      p.updated_at,
      CASE\s
        WHEN UPPER(p.product_type) = 'SINGLE' THEN c.image_url\s
        ELSE p.image_url\s
      END AS image_url
    FROM dev_tcg_marketplace.product p
    LEFT JOIN dev_tcg_marketplace.expansion e ON p.expansion_id = e.expansion_id
    LEFT JOIN dev_tcg_marketplace.card c ON p.card_id = c.card_id
    LEFT JOIN dev_tcg_marketplace.tcg_rarity tr ON c.tcg_rarity_id = tr.tcg_rarity_id
    LEFT JOIN dev_tcg_marketplace.rarity r ON tr.rarity_id = r.rarity_id
    WHERE\s
      p.parent_product_id IS NOT NULL
      AND (
          (:productType IS NULL OR TRIM(:productType) = '' OR LOWER(:productType) = 'all')
          OR UPPER(p.product_type) = UPPER(:productType)
      )
      AND (
          (:expansionName IS NULL OR TRIM(:expansionName) = '' OR LOWER(:expansionName) = 'all')
          OR LOWER(e.name) = LOWER(:expansionName)
      )
      AND (
          (:rarityName IS NULL OR TRIM(:rarityName) = '' OR LOWER(:rarityName) = 'all')
          OR LOWER(r.name) = LOWER(:rarityName)
      )
      AND (
          (:searchTerm IS NULL OR TRIM(:searchTerm) = '')
          OR LOWER(p.product_name) LIKE CONCAT('%', LOWER(:searchTerm), '%')
      )
    ORDER BY
      CASE WHEN :sortBy = 'nameAsc' THEN p.product_name END ASC,
      CASE WHEN :sortBy = 'nameDesc' THEN p.product_name END DESC
   \s""", nativeQuery = true)
    List<Product> filterProducts(
            @Param("productType") String productType,
            @Param("expansionName") String expansionName,
            @Param("rarityName") String rarityName,
            @Param("searchTerm") String searchTerm,
            @Param("sortBy") String sortBy
    );

}
