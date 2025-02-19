package com.tcgmarketplace.card.dto;

public record CardDto(
        Integer id,
        Integer expansionId,
        Integer tcgRarityId,
        String name,
        String collectorNumber,
        String imageUrl
) {}
