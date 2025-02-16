package com.tcgmarketplace.card.dto;

public record CreateCardDto(
        Integer expansionId,
        Integer tcgRarityId,
        String name,
        String collectorNumber,
        String imageUrl
) {}
