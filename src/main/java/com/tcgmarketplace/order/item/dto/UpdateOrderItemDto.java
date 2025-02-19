package com.tcgmarketplace.order.item.dto;

import java.math.BigDecimal;

public record UpdateOrderItemDto(
        Integer quantity,
        BigDecimal price
) {}