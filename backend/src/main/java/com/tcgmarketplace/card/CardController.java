package com.tcgmarketplace.card;

import com.tcgmarketplace.card.dto.CardDto;
import com.tcgmarketplace.card.dto.CreateCardDto;
import com.tcgmarketplace.card.dto.UpdateCardDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<CardDto> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/{id}")
    public CardDto getCardById(@PathVariable Integer id) {
        return cardService.getCardById(id);
    }

    @GetMapping("/search/{name}")
    public CardDto findCardByName(@PathVariable String name) {
        return cardService.findCardByName(name);
    }

    @GetMapping("/search/{collectorNumber}")
    public CardDto findCardByCollectorNumber( @PathVariable String collectorNumber) {
        return cardService.findCardByCollectorNumber(collectorNumber);
    }

    @GetMapping("/tcg/{tcgId}")
    public List<CardDto> getCardsByTcg(@PathVariable Integer tcgId) {
        return cardService.getCardsByTcg(tcgId);
    }

    @GetMapping("/tcg/{tcgId}/search")
    public List<CardDto> searchCardsByTcgAndName(@PathVariable Integer tcgId, @RequestParam String name) {
        return cardService.searchCardsByTcgAndName(tcgId, name);
    }

    @PostMapping
    public CardDto createCard(@RequestBody CreateCardDto dto) {
        return cardService.createCard(dto);
    }

    @PutMapping("/{id}")
    public CardDto updateCard(@PathVariable Integer id, @RequestBody UpdateCardDto dto) {
        return cardService.updateCard(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Integer id) {
        cardService.deleteCard(id);
    }

}
