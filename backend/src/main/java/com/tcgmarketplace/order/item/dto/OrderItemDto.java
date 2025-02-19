package com.tcgmarketplace.order.item.dto;

import java.math.BigDecimal;

public record OrderItemDto(
        Integer id,
        Integer orderId,
        Integer listingId,
        Integer quantity,
        BigDecimal price,
        BigDecimal subtotal
) {}
