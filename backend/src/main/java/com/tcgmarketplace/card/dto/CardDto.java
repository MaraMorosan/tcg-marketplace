package com.tcgmarketplace.card.dto;

public record CardDto(
        Integer id,
        Integer expansionId,
        String expansionName,
        Integer tcgRarityId,
        String rarityName,
        String name,
        String collectorNumber,
        String imageUrl
) {}
