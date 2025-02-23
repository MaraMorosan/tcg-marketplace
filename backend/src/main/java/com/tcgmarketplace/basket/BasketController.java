package com.tcgmarketplace.basket;

import com.tcgmarketplace.basket.dto.BasketDto;
import com.tcgmarketplace.basket.dto.CreateBasketDto;
import com.tcgmarketplace.basket.item.dto.BasketItemDto;
import com.tcgmarketplace.basket.item.dto.CreateBasketItemDto;
import com.tcgmarketplace.basket.item.dto.UpdateBasketItemDto;
import com.tcgmarketplace.user.User;
import com.tcgmarketplace.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    private final BasketService basketService;
    private final UserRepository userRepository;

    public BasketController(BasketService basketService, UserRepository userRepository) {
        this.basketService = basketService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<BasketDto> getUserBasket(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(basketService.getBasketByUser(user));
    }

    @PostMapping
    public ResponseEntity<BasketDto> createBasket(@RequestBody CreateBasketDto dto) {
        return ResponseEntity.ok(basketService.createBasket(dto));
    }

    @PostMapping("/add")
    public ResponseEntity<BasketItemDto> addToBasket(@AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestBody CreateBasketItemDto dto) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(basketService.addToBasket(user, dto));
    }

    @PutMapping("/update/{basketItemId}")
    public ResponseEntity<BasketItemDto> updateBasketItem(@PathVariable Integer basketItemId,
                                                          @RequestBody UpdateBasketItemDto dto) {
        return ResponseEntity.ok(basketService.updateBasketItem(basketItemId, dto));
    }

    @DeleteMapping("/remove/{basketItemId}")
    public ResponseEntity<Void> removeBasketItem(@PathVariable Integer basketItemId) {
        basketService.removeBasketItem(basketItemId);
        return ResponseEntity.noContent().build();
    }
}
