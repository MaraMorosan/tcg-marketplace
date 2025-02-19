package com.tcgmarketplace.basket.item.dto;

public record CreateBasketItemDto(
        Integer basketId,
        Integer listingId,
        Integer quantity
) {}
