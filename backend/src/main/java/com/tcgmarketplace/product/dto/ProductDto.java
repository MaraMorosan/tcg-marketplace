package com.tcgmarketplace.product.dto;

import com.tcgmarketplace.product.ProductType;
import java.time.LocalDateTime;

public record ProductDto(
        Integer id,
        String name,
        String imageUrl,
        String expansionName,
        ProductType productType,
        Integer expansionId,
        Integer cardId,
        String cardImageUrl,
        Integer parentProductId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
