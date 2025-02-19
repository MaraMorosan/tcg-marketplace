package com.tcgmarketplace.listing.dto;

import com.tcgmarketplace.listing.Condition;
import java.math.BigDecimal;

public record CreateListingDto(
        Integer productId,
        Integer sellerId,
        Integer stock,
        BigDecimal price,
        Condition condition
) {}
