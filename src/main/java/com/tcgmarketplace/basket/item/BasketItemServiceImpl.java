package com.tcgmarketplace.basket.item;

import com.tcgmarketplace.basket.Basket;
import com.tcgmarketplace.basket.BasketRepository;
import com.tcgmarketplace.basket.item.dto.BasketItemDto;
import com.tcgmarketplace.basket.item.dto.CreateBasketItemDto;
import com.tcgmarketplace.basket.item.dto.UpdateBasketItemDto;
import com.tcgmarketplace.listing.Listing;
import com.tcgmarketplace.listing.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class BasketItemServiceImpl implements BasketItemService {

    private final BasketItemRepository basketItemRepository;
    private final BasketRepository basketRepository;
    private final ListingRepository listingRepository;

    public BasketItemServiceImpl(BasketItemRepository basketItemRepository,
                                 BasketRepository basketRepository,
                                 ListingRepository listingRepository) {
        this.basketItemRepository = basketItemRepository;
        this.basketRepository = basketRepository;
        this.listingRepository = listingRepository;
    }

    @Override
    public List<BasketItemDto> getAllBasketItems() {
        return basketItemRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public BasketItemDto getBasketItemById(Integer id) {
        BasketItem item = basketItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BasketItem not found"));
        return toDto(item);
    }

    @Override
    public BasketItemDto createBasketItem(CreateBasketItemDto dto) {
        Basket basket = basketRepository.findById(dto.basketId())
                .orElseThrow(() -> new RuntimeException("Basket not found"));
        Listing listing = listingRepository.findById(dto.listingId())
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        BasketItem item = new BasketItem();
        item.setBasket(basket);
        item.setListing(listing);
        item.setQuantity(dto.quantity());

        basketItemRepository.save(item);
        return toDto(item);
    }

    @Override
    public BasketItemDto updateBasketItem(Integer id, UpdateBasketItemDto dto) {
        BasketItem item = basketItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BasketItem not found"));
        item.setQuantity(dto.quantity());
        basketItemRepository.save(item);
        return toDto(item);
    }

    @Override
    public void deleteBasketItem(Integer id) {
        if (!basketItemRepository.existsById(id)) {
            throw new RuntimeException("BasketItem not found");
        }
        basketItemRepository.deleteById(id);
    }

    private BasketItemDto toDto(BasketItem item) {
        return new BasketItemDto(
                item.getId(),
                item.getBasket().getId(),
                item.getListing().getId(),
                item.getQuantity()
        );
    }
}
