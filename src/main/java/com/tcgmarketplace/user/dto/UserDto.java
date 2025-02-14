package com.tcgmarketplace.user.dto;

import com.tcgmarketplace.user.Role;

import java.time.LocalDateTime;

public record UserDto(
        Integer id,
        String username,
        String email,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
