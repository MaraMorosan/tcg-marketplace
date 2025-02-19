package com.tcgmarketplace.basket;

import com.tcgmarketplace.basket.dto.BasketDto;
import com.tcgmarketplace.basket.dto.CreateBasketDto;
import com.tcgmarketplace.user.User;
import com.tcgmarketplace.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;

    public BasketServiceImpl(BasketRepository basketRepository, UserRepository userRepository) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BasketDto> getAllBaskets() {
        return basketRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public BasketDto getBasketById(Integer id) {
        Basket basket = basketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Basket not found"));
        return toDto(basket);
    }

    @Override
    public BasketDto createBasket(CreateBasketDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Basket basket = new Basket();
        basket.setUser(user);
        basketRepository.save(basket);
        return toDto(basket);
    }

    @Override
    public void deleteBasket(Integer id) {
        if (!basketRepository.existsById(id)) {
            throw new RuntimeException("Basket not found");
        }
        basketRepository.deleteById(id);
    }

    private BasketDto toDto(Basket basket) {
        return new BasketDto(
                basket.getId(),
                basket.getUser().getId(),
                basket.getCreatedAt(),
                basket.getUpdatedAt()
        );
    }
}
