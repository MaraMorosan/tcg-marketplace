package com.tcgmarketplace.basket;

import com.tcgmarketplace.basket.dto.BasketDto;
import com.tcgmarketplace.basket.dto.CreateBasketDto;
import java.util.List;

public interface BasketService {
    List<BasketDto> getAllBaskets();
    BasketDto getBasketById(Integer id);
    BasketDto createBasket(CreateBasketDto dto);
    void deleteBasket(Integer id);
}
