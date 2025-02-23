package com.tcgmarketplace.basket;

import com.tcgmarketplace.basket.dto.BasketDto;
import com.tcgmarketplace.basket.dto.CreateBasketDto;
import com.tcgmarketplace.basket.item.dto.BasketItemDto;
import com.tcgmarketplace.basket.item.dto.CreateBasketItemDto;
import com.tcgmarketplace.basket.item.dto.UpdateBasketItemDto;
import com.tcgmarketplace.basket.item.BasketItem;
import com.tcgmarketplace.basket.item.BasketItemRepository;
import com.tcgmarketplace.listing.Listing;
import com.tcgmarketplace.listing.ListingRepository;
import com.tcgmarketplace.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final ListingRepository listingRepository;
    private final BasketItemRepository basketItemRepository;

    public BasketServiceImpl(BasketRepository basketRepository, ListingRepository listingRepository,
                         BasketItemRepository basketItemRepository) {
        this.basketRepository = basketRepository;
        this.listingRepository = listingRepository;
        this.basketItemRepository = basketItemRepository;
    }

    @Override
    public BasketDto getBasketByUser(User user) {
        Basket basket = getBasket(user);

        List<BasketItemDto> items = basket.getBasketItems().stream()
                .map(item -> new BasketItemDto(
                        item.getId(),
                        basket.getId(),
                        item.getListing().getId(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        return new BasketDto(basket.getId(), basket.getUser().getId(), items);
    }

    private Basket getBasket(User user) {
        return basketRepository.findByUser(user)
                .orElseGet(() -> {
                    Basket newBasket = new Basket();
                    newBasket.setUser(user);
                    newBasket.setBasketItems(new ArrayList<>());
                    return basketRepository.save(newBasket);
                });
    }

    public BasketDto createBasket(CreateBasketDto dto) {
        User user = basketRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found")).getUser();

        if (basketRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("User already has a basket.");
        }

        Basket basket = getBasket(user);

        List<BasketItemDto> items = basket.getBasketItems().stream()
                .map(item -> new BasketItemDto(
                        item.getId(),
                        basket.getId(),
                        item.getListing().getId(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        basket.setUser(user);
        basketRepository.save(basket);

        return new BasketDto(basket.getId(), basket.getUser().getId(), items);
    }

    public BasketItemDto addToBasket(User user, CreateBasketItemDto dto) {
        Basket basket = basketRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User does not have a basket."));

        Listing listing = listingRepository.findById(dto.listingId())
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        if (listing.getSeller().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot add your own listing to the basket.");
        }

        Optional<BasketItem> existingItem = basket.getBasketItems().stream()
                .filter(item -> item.getListing().getId().equals(listing.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + dto.quantity());
            basketItemRepository.save(existingItem.get());
            return mapToBasketItemDto(existingItem.get());
        } else {
            BasketItem basketItem = new BasketItem();
            basketItem.setBasket(basket);
            basketItem.setListing(listing);
            basketItem.setQuantity(dto.quantity());
            basketItemRepository.save(basketItem);
            return mapToBasketItemDto(basketItem);
        }
    }



    public BasketItemDto updateBasketItem(Integer basketItemId, UpdateBasketItemDto dto) {
        BasketItem basketItem = basketItemRepository.findById(basketItemId)
                .orElseThrow(() -> new RuntimeException("Basket item not found"));

        basketItem.setQuantity(dto.quantity());
        basketItemRepository.save(basketItem);

        return mapToBasketItemDto(basketItem);
    }

    public void removeBasketItem(Integer basketItemId) {
        BasketItem basketItem = basketItemRepository.findById(basketItemId)
                .orElseThrow(() -> new RuntimeException("Basket item not found"));

        basketItemRepository.delete(basketItem);
    }

    private BasketItemDto mapToBasketItemDto(BasketItem basketItem) {
        return new BasketItemDto(
                basketItem.getId(),
                basketItem.getBasket().getId(),
                basketItem.getListing().getId(),
                basketItem.getQuantity()
        );
    }
}
