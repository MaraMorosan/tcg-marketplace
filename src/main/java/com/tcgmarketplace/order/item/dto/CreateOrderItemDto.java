package com.tcgmarketplace.order.item.dto;

import java.math.BigDecimal;

public record CreateOrderItemDto(
        Integer orderId,
        Integer listingId,
        Integer quantity,
        BigDecimal price
) {}
