package com.tcgmarketplace.expansion.dto;

import java.time.LocalDate;

public record ExpansionDto(
        Integer id,
        Integer tcgId,
        String name,
        String code,
        LocalDate releaseDate
) {}
