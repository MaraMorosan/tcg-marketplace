package com.tcgmarketplace.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateOrderDto(
        Integer id,
        LocalDateTime orderDate,
        String status,
        BigDecimal total
) {}
