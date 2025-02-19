package com.tcgmarketplace.expansion;

import com.tcgmarketplace.expansion.dto.CreateExpansionDto;
import com.tcgmarketplace.expansion.dto.UpdateExpansionDto;
import com.tcgmarketplace.expansion.dto.ExpansionDto;

import java.util.List;

public interface ExpansionService {
    List<ExpansionDto> getAllExpansions();
    ExpansionDto getExpansionById(Integer id);
    ExpansionDto findExpansionByName(String name);
    ExpansionDto createExpansion(CreateExpansionDto dto);
    ExpansionDto updateExpansion(Integer id, UpdateExpansionDto dto);
    void deleteExpansion(Integer id);
}
