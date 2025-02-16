package com.tcgmarketplace.rarity;

import com.tcgmarketplace.rarity.dto.CreateRarityDto;
import com.tcgmarketplace.rarity.dto.RarityDto;
import com.tcgmarketplace.rarity.dto.UpdateRarityDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rarity")
public class RarityController {

    private final RarityService rarityService;

    public RarityController(RarityService rarityService) {
        this.rarityService = rarityService;
    }

    @GetMapping
    public List<RarityDto> getAllRarities() {
        return rarityService.getAllRarities();
    }

    @GetMapping("/{id}")
    public RarityDto getRarityById(@PathVariable Integer id) {
        return rarityService.getRarityById(id);
    }

    @PostMapping
    public RarityDto createRarity(@RequestBody CreateRarityDto dto) {
        return rarityService.createRarity(dto);
    }

    @PutMapping("/{id}")
    public RarityDto updateRarity(@PathVariable Integer id, @RequestBody UpdateRarityDto dto) {
        return rarityService.updateRarity(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteRarity(@PathVariable Integer id) {
        rarityService.deleteRarity(id);
    }
}
