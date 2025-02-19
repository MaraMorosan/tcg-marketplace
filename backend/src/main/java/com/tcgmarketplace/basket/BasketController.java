package com.tcgmarketplace.basket;

import com.tcgmarketplace.basket.dto.BasketDto;
import com.tcgmarketplace.basket.dto.CreateBasketDto;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping
    public List<BasketDto> getAllBaskets() {
        return basketService.getAllBaskets();
    }

    @GetMapping("/{id}")
    public BasketDto getBasketById(@PathVariable Integer id) {
        return basketService.getBasketById(id);
    }

    @PostMapping
    public BasketDto createBasket(@RequestBody CreateBasketDto dto) {
        return basketService.createBasket(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBasket(@PathVariable Integer id) {
        basketService.deleteBasket(id);
    }
}
