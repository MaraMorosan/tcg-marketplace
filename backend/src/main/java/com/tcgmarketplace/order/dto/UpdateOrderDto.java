package com.tcgmarketplace.order.dto;

import java.math.BigDecimal;

public record UpdateOrderDto(
        String status,
        BigDecimal total
) {}
