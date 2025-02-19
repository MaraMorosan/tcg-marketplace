package com.tcgmarketplace.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDto(
        Integer id,
        Integer userId,
        LocalDateTime orderDate,
        String status,
        BigDecimal total
) {}
