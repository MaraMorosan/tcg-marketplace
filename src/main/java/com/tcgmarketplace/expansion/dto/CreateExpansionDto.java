package com.tcgmarketplace.expansion.dto;

import java.time.LocalDate;

public record CreateExpansionDto(
        Integer tcgId,
        String name,
        String code,
        LocalDate releaseDate
) {}
