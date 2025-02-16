package com.tcgmarketplace.card;

import com.tcgmarketplace.card.dto.CardDto;
import com.tcgmarketplace.card.dto.CreateCardDto;
import com.tcgmarketplace.card.dto.UpdateCardDto;

import java.util.List;

public interface CardService {
    List<CardDto> getAllCards();

    CardDto getCardById(Integer id);

    CardDto findCardByCollectorNumber(String collectorNumber);

    CardDto findCardByName(String name);

    CardDto createCard(CreateCardDto dto);

    CardDto updateCard(Integer id, UpdateCardDto dto);

    void deleteCard(Integer id);
}
