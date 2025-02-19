package com.tcgmarketplace.listing.dto;

import com.tcgmarketplace.listing.Condition;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ListingDto(
        Integer id,
        Integer productId,
        Integer sellerId,
        Integer stock,
        BigDecimal price,
        Condition condition,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
