package com.tcgmarketplace.expansion.dto;

import java.time.LocalDate;

public record UpdateExpansionDto(
        String name,
        String code,
        LocalDate releaseDate
) {}
