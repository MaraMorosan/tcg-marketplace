package com.tcgmarketplace.basket.item.dto;

public record BasketItemDto(
        Integer id,
        Integer basketId,
        Integer listingId,
        Integer quantity
) {}
