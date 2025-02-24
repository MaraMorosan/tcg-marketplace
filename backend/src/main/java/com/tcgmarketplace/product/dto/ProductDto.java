package com.tcgmarketplace.product.dto;

import com.tcgmarketplace.product.ProductType;
import java.time.LocalDateTime;

public record ProductDto(
        Integer id,
        String name,
        String imageUrl,
        ProductType productType,
        Integer expansionId,
        Integer cardId,
        Integer parentProductId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
