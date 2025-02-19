package com.tcgmarketplace.basket.dto;

import java.time.LocalDateTime;

public record BasketDto(
        Integer id,
        Integer userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
