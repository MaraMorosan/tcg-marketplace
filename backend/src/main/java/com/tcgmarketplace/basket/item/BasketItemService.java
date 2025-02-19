package com.tcgmarketplace.basket.item;

import com.tcgmarketplace.basket.item.dto.BasketItemDto;
import com.tcgmarketplace.basket.item.dto.CreateBasketItemDto;
import com.tcgmarketplace.basket.item.dto.UpdateBasketItemDto;
import java.util.List;

public interface BasketItemService {
    List<BasketItemDto> getAllBasketItems();
    BasketItemDto getBasketItemById(Integer id);
    BasketItemDto createBasketItem(CreateBasketItemDto dto);
    BasketItemDto updateBasketItem(Integer id, UpdateBasketItemDto dto);
    void deleteBasketItem(Integer id);
}
