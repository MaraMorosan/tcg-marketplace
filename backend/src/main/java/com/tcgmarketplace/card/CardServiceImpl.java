package com.tcgmarketplace.card;

import com.tcgmarketplace.card.dto.CardDto;
import com.tcgmarketplace.card.dto.CreateCardDto;
import com.tcgmarketplace.card.dto.UpdateCardDto;
import com.tcgmarketplace.expansion.Expansion;
import com.tcgmarketplace.expansion.ExpansionRepository;
import com.tcgmarketplace.product.Product;
import com.tcgmarketplace.product.ProductRepository;
import com.tcgmarketplace.product.ProductType;
import com.tcgmarketplace.tcg.rarity.TcgRarity;
import com.tcgmarketplace.tcg.rarity.TcgRarityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final ExpansionRepository expansionRepository;
    private final TcgRarityRepository tcgRarityRepository;
    private final ProductRepository productRepository;

    public CardServiceImpl(CardRepository cardRepository,
                           ExpansionRepository expansionRepository,
                           TcgRarityRepository tcgRarityRepository,
                           ProductRepository productRepository) {
        this.cardRepository = cardRepository;
        this.expansionRepository = expansionRepository;
        this.tcgRarityRepository = tcgRarityRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CardDto> getAllCards() {
        return cardRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public CardDto getCardById(Integer id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        return toDto(card);
    }

    @Override
    public CardDto findCardByCollectorNumber(String collectorNumber) {
        Card card = cardRepository.findCardByCollectorNumber(collectorNumber)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        return toDto(card);
    }

    @Override
    public CardDto findCardByName(String name) {
        Card card = cardRepository.findCardByName(name)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        return toDto(card);
    }

    @Override
    public List<CardDto> getCardsByTcg(Integer tcgId) {
        return cardRepository.findCardsByTcgId(tcgId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CardDto> searchCardsByTcgAndName(Integer tcgId, String name) {
        return cardRepository.searchCardsByTcgAndName(tcgId, name).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CardDto createCard(CreateCardDto dto) {
        Expansion expansion = expansionRepository.findById(dto.expansionId())
                .orElseThrow(() -> new RuntimeException("Expansion not found"));

        TcgRarity tcgRarity = tcgRarityRepository.findById(dto.tcgRarityId())
                .orElseThrow(() -> new RuntimeException("TcgRarity not found"));

        Card card = new Card();
        card.setExpansion(expansion);
        card.setTcgRarity(tcgRarity);
        card.setName(dto.name());
        card.setCollectorNumber(dto.collectorNumber());
        card.setImageUrl(dto.imageUrl());

        cardRepository.save(card);

        Product parentProduct = productRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Parent product (Singles) not found"));

        Product product = new Product();
        product.setName(card.getName());
        product.setProductType(ProductType.SINGLE);
        product.setExpansion(expansion);
        product.setCard(card);
        product.setParentProduct(parentProduct);

        productRepository.save(product);

        return toDto(card);
    }

    @Override
    public CardDto updateCard(Integer id, UpdateCardDto dto) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setName(dto.name());
        card.setCollectorNumber(dto.collectorNumber());
        card.setImageUrl(dto.imageUrl());

        cardRepository.save(card);
        return toDto(card);
    }

    @Override
    public void deleteCard(Integer id) {
        if (!cardRepository.existsById(id)) {
            throw new RuntimeException("Card not found");
        }
        cardRepository.deleteById(id);
    }

    private CardDto toDto(Card card) {
        return new CardDto(
                card.getId(),
                card.getExpansion().getId(),
                card.getExpansion().getName(),
                card.getTcgRarity().getId(),
                card.getTcgRarity().getRarity().getName(),
                card.getName(),
                card.getCollectorNumber(),
                card.getImageUrl()
        );
    }
}
