package com.tcgmarketplace.tcg.rarity;

import com.tcgmarketplace.tcg.rarity.dto.CreateTcgRarityDto;
import com.tcgmarketplace.tcg.rarity.dto.TcgRarityDto;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tcg-rarity")
public class TcgRarityController {

    private final TcgRarityService tcgRarityService;

    public TcgRarityController(TcgRarityService tcgRarityService) {
        this.tcgRarityService = tcgRarityService;
    }

    @GetMapping
    public List<TcgRarityDto> getAllTcgRarities() {
        return tcgRarityService.getAllTcgRarities();
    }

    @GetMapping("/{id}")
    public TcgRarityDto getTcgRarityById(@PathVariable Integer id) {
        return tcgRarityService.getTcgRarityById(id);
    }

    @PostMapping
    public TcgRarityDto createTcgRarity(@RequestBody CreateTcgRarityDto dto) {
        return tcgRarityService.createTcgRarity(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTcgRarity(@PathVariable Integer id) {
        tcgRarityService.deleteTcgRarity(id);
    }
}
