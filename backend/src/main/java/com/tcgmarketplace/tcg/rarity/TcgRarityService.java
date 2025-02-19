package com.tcgmarketplace.tcg.rarity;

import com.tcgmarketplace.tcg.rarity.dto.CreateTcgRarityDto;
import com.tcgmarketplace.tcg.rarity.dto.TcgRarityDto;
import java.util.List;

public interface TcgRarityService {
    List<TcgRarityDto> getAllTcgRarities();
    TcgRarityDto getTcgRarityById(Integer id);
    TcgRarityDto createTcgRarity(CreateTcgRarityDto dto);
    void deleteTcgRarity(Integer id);
}
