package com.tcgmarketplace.card.dto;

public record UpdateCardDto(
        String name,
        String collectorNumber,
        String imageUrl
) {}
