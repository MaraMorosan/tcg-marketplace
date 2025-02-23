package com.tcgmarketplace.basket.dto;

import com.tcgmarketplace.basket.item.dto.BasketItemDto;

import java.util.List;

public record BasketDto(
        Integer id,
        Integer userId,
        List<BasketItemDto> basketItems
) {}
