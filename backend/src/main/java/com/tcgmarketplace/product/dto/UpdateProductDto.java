package com.tcgmarketplace.product.dto;

import com.tcgmarketplace.product.ProductType;

public record UpdateProductDto(
        String name,
        String imageUrl,
        ProductType productType,
        Integer expansionId,
        Integer cardId,
        Integer parentProductId
) {}
