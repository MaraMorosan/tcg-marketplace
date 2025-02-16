package com.tcgmarketplace.product.dto;

import com.tcgmarketplace.product.ProductType;

public record CreateProductDto(
        String name,
        ProductType productType,
        Integer expansionId,
        Integer cardId,
        Integer parentProductId
) {}
