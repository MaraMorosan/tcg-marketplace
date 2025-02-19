package com.tcgmarketplace.listing.dto;

import com.tcgmarketplace.listing.Condition;
import java.math.BigDecimal;

public record UpdateListingDto(
        Integer stock,
        BigDecimal price,
        Condition condition
) {}
