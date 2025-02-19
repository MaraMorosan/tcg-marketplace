package com.tcgmarketplace.basket.item;

import com.tcgmarketplace.basket.item.dto.BasketItemDto;
import com.tcgmarketplace.basket.item.dto.CreateBasketItemDto;
import com.tcgmarketplace.basket.item.dto.UpdateBasketItemDto;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/basket-item")
public class BasketItemController {

    private final BasketItemService basketItemService;

    public BasketItemController(BasketItemService basketItemService) {
        this.basketItemService = basketItemService;
    }

    @GetMapping
    public List<BasketItemDto> getAllBasketItems() {
        return basketItemService.getAllBasketItems();
    }

    @GetMapping("/{id}")
    public BasketItemDto getBasketItemById(@PathVariable Integer id) {
        return basketItemService.getBasketItemById(id);
    }

    @PostMapping
    public BasketItemDto createBasketItem(@RequestBody CreateBasketItemDto dto) {
        return basketItemService.createBasketItem(dto);
    }

    @PutMapping("/{id}")
    public BasketItemDto updateBasketItem(@PathVariable Integer id, @RequestBody UpdateBasketItemDto dto) {
        return basketItemService.updateBasketItem(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBasketItem(@PathVariable Integer id) {
        basketItemService.deleteBasketItem(id);
    }
}
