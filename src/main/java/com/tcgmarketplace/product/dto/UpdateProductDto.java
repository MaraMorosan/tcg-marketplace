package com.tcgmarketplace.product.dto;

import com.tcgmarketplace.product.ProductType;

public record UpdateProductDto(
        String name,
        ProductType productType,
        Integer expansionId,
        Integer cardId,
        Integer parentProductId
) {}
