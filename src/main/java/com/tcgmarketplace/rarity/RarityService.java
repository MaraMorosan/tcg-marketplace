package com.tcgmarketplace.rarity;

import com.tcgmarketplace.rarity.dto.CreateRarityDto;
import com.tcgmarketplace.rarity.dto.RarityDto;
import com.tcgmarketplace.rarity.dto.UpdateRarityDto;

import java.util.List;

public interface RarityService {
    List<RarityDto> getAllRarities();
    RarityDto getRarityById(Integer id);
    RarityDto createRarity(CreateRarityDto dto);
    RarityDto updateRarity(Integer id, UpdateRarityDto dto);
    void deleteRarity(Integer id);
}
