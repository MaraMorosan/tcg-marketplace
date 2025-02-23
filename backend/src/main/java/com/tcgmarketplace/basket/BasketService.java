package com.tcgmarketplace.basket;

import com.tcgmarketplace.basket.dto.BasketDto;
import com.tcgmarketplace.basket.dto.CreateBasketDto;
import com.tcgmarketplace.basket.item.dto.BasketItemDto;
import com.tcgmarketplace.basket.item.dto.CreateBasketItemDto;
import com.tcgmarketplace.basket.item.dto.UpdateBasketItemDto;
import com.tcgmarketplace.user.User;

public interface BasketService {

    BasketDto getBasketByUser(User user);

    void removeBasketItem(Integer basketItemId);

    BasketItemDto updateBasketItem(Integer basketItemId, UpdateBasketItemDto dto);

    BasketItemDto addToBasket(User user, CreateBasketItemDto dto);

    BasketDto createBasket(CreateBasketDto dto);
}
