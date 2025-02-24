package com.tcgmarketplace.product.dto;

import com.tcgmarketplace.product.ProductType;

public record CreateProductDto(
        String name,
        String imageUrl,
        String expansionName,
        ProductType productType,
        Integer expansionId,
        Integer cardId,
        Integer parentProductId
) {}
