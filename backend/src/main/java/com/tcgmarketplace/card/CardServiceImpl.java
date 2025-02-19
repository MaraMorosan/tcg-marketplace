package com.tcgmarketplace.card;

import com.tcgmarketplace.card.dto.CardDto;
import com.tcgmarketplace.card.dto.CreateCardDto;
import com.tcgmarketplace.card.dto.UpdateCardDto;
import com.tcgmarketplace.expansion.Expansion;
import com.tcgmarketplace.expansion.ExpansionRepository;
import com.tcgmarketplace.tcg.rarity.TcgRarity;
import com.tcgmarketplace.tcg.rarity.TcgRarityRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final ExpansionRepository expansionRepository;
    private final TcgRarityRepository tcgRarityRepository;

    public CardServiceImpl(CardRepository cardRepository,
                           ExpansionRepository expansionRepository,
                           TcgRarityRepository tcgRarityRepository) {
        this.cardRepository = cardRepository;
        this.expansionRepository = expansionRepository;
        this.tcgRarityRepository = tcgRarityRepository;
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
                card.getTcgRarity().getId(),
                card.getName(),
                card.getCollectorNumber(),
                card.getImageUrl()
        );
    }
}
